import java.util.*;

public class SimuladorUrgencia {
    Random random = new Random();
    private Hospital hospital;
    private GeneradorPacientes generador;
    public static int minutoActualGlobal = 0;
    public SimuladorUrgencia() {
        hospital = new Hospital();
        generador = new GeneradorPacientes();
    }
    static Map<Integer, Integer> excedentesPorCategoria = new HashMap<>(){};

    public void simular(int pacientesPorDia) {
        for(int z = 1; z <= 5; z++) {
            excedentesPorCategoria.put(z, 0);
        }
        List<Paciente> pacientes = generador.generarPacientes(pacientesPorDia,0);
        int[] categorias = new int[5];
        int nuevosPacientes = 0;
        minutoActualGlobal = 0;
        System.out.println("Cantidad de pacientes: " + pacientesPorDia);
        //GeneradorPacientes.guardarEnArchivo(pacientes,"Prueba_"+pacientesPorDia+"_Pacientes.txt");
        while (!pacientes.isEmpty() && minutoActualGlobal <= 1440) {
            hospital.verificarPacientesCriticos();
            if (minutoActualGlobal % 10 == 0) {
                Paciente nuevo = pacientes.remove(0);
                categorias[nuevo.getCategoria()-1] += 1;
                hospital.registrarPaciente(nuevo);
                nuevosPacientes++;

                if (nuevosPacientes % 3 == 0) {
                    for (int i=0; i<2; i++) {
                        Paciente atendido = hospital.atenderSiguiente();
                        if (atendido != null) {
                            int tiempoAtencion = tiempoAtencionPorCategoria(atendido.getCategoria());
                            minutoActualGlobal += tiempoAtencion;
                        }
                    }
                    continue;
                }
            }

            if (minutoActualGlobal % 15 == 0) {
                Paciente atendido = hospital.atenderSiguiente();
                if (atendido != null) {
                    int tiempoAtencion = tiempoAtencionPorCategoria(atendido.getCategoria());
                    minutoActualGlobal += tiempoAtencion;
                    continue;
                }
            }
            minutoActualGlobal++;
        }
        for(int i=0;i<5;i++) {
            System.out.println("Pacientes Categoria: C"+ (i+1) +": "+categorias[i]);
        }
        System.out.println("Pacientes atendidos: " + (nuevosPacientes));
        if(!pacientes.isEmpty()){
            System.out.println("Pacientes no atendidos: " + (pacientesPorDia - nuevosPacientes));
            for(Paciente p : pacientes) {
                excedentesPorCategoria.put(
                        p.getCategoria(),
                        excedentesPorCategoria.get(p.getCategoria()) + 1);
            }
        }
        System.out.println("Pacientes que excedieron el tiempo máximo por categoría:");
        for (int categoria = 1; categoria <= 5; categoria++) {
            System.out.println("C" + categoria + ": " + excedentesPorCategoria.get(categoria) + " pacientes");
        }
        excedentesPorCategoria.clear();
        Historial();
    }

    // Prueba: seguimiento individual
    public void pruebaSeguimientoIndividual() {
        hospital = new Hospital();
        minutoActualGlobal = 0;
        List<Paciente> pacientes = generador.generarPacientes(100, 0); // 100 pacientes, llegada aleatoria
        Paciente objetivo = null,atendido;
        int nuevosPacientes = 0;
        while (!pacientes.isEmpty()  && minutoActualGlobal <= 1440) {
            if (minutoActualGlobal % 10 == 0) {
                Paciente nuevo = pacientes.remove(0);
                if (objetivo == null && nuevo.getCategoria() == 4) {
                    objetivo = nuevo;
                }
                hospital.registrarPaciente(nuevo);
                nuevosPacientes++;

                if (nuevosPacientes % 3 == 0) {
                    atendido = hospital.atenderSiguiente();
                    if(status(atendido,objetivo)){
                        return;
                    };
                    status(atendido,objetivo);
                    atendido = hospital.atenderSiguiente();
                    if(status(atendido,objetivo)){
                        return;
                    };
                    status(atendido,objetivo);
                }
            }

            if (minutoActualGlobal % 15 == 0) {
                atendido = hospital.atenderSiguiente();
                if(status(atendido,objetivo)){
                    return;
                }
            }
            minutoActualGlobal++;
        }
        System.out.println("El paciente no fue atendido durante la jornada.");
    }

    public boolean status (Paciente atendido,Paciente objetivo){
        if (objetivo != null && atendido.getId().equals(objetivo.getId())) {
            int espera = (int) objetivo.tiempoEsperaActual();
            System.out.println("Paciente C"+objetivo.getCategoria() + " (ID: " + objetivo.getId() + ") atendido en " + espera +
                    " minutos (llegó en minuto " + objetivo.getTiempoLlegada() + ", atendido en minuto " + minutoActualGlobal + ")");
            return true;
        }
        return false;
    }


    // Prueba: promedio por categoría
    public void pruebaPromediosPorCategoria() {
        Map<Integer, List<Long>> tiemposPorCategoria = new HashMap<>();
        for (int i = 0; i < 15; i++) {
            int cant = random.nextInt(150);
            hospital = new Hospital();
            System.out.println("\n \n Prueba Numero: "+ (i+1));
            simular(cant);
            for (Paciente p : hospital.getPacientesAtendidos()) {
                tiemposPorCategoria
                        .computeIfAbsent(p.getCategoria(), k -> new ArrayList<>())
                        .add(p.tiempoEsperaActual());
            }
        }
        for (int cat = 1; cat <= 5; cat++) {
            List<Long> tiempos = tiemposPorCategoria.getOrDefault(cat, new ArrayList<>());
            double promedio = tiempos.stream().mapToLong(Long::longValue).average().orElse(0);
            System.out.println("Promedio de Categoria C" + cat + ": " + promedio + " minutos");
        }
    }

    // Prueba: saturación
    public void pruebaSaturacionSistema() {
        hospital = new Hospital();
        int cant = random.nextInt(151,400);
        simular(cant);
        Map<Integer, LongSummaryStatistics> stats = new HashMap<>();
        for (Paciente p : hospital.getPacientesAtendidos()) {
            stats.computeIfAbsent(p.getCategoria(), k -> new LongSummaryStatistics())
                    .accept(p.tiempoEsperaActual());
        }
        for (int cat = 1; cat <= 5; cat++) {
            LongSummaryStatistics stat = stats.getOrDefault(cat, new LongSummaryStatistics());
            System.out.println("Categoria C" + cat + ": promedio= " + stat.getAverage() + " minutos , max= " + stat.getMax() + ", min= " + stat.getMin());
        }
    }

    public void pruebaCambioCategoria() {;
        Paciente p = new Paciente("Ana", "Perez", "ID123", 3, "en_espera", "SAPU");
        hospital.registrarPaciente(p);
        hospital.reasignarCategoria("ID123", 1);
        System.out.println("Historial de cambios: " + p.getHistorialCambios());
    }
    public Hospital getHospital() {
        return hospital;
    }
    public  void Historial() {
        System.out.println("------------Pacientes en orden de atención------------");
        for (Paciente p : hospital.getPacientesAtendidos()) {
            System.out.printf("ID: %s, C%d, Esperó: %d min\n",
                    p.getId(), p.getCategoria(), p.tiempoEsperaActual());
        }

    }
    public static int  tiempoMaximoPorCategoria(int categoria) {
        return switch (categoria) {
            case 1 -> 15;
            case 2 -> 45;
            case 3 -> 120;
            case 4 -> 300;
            case 5 -> 360;
            default -> Integer.MAX_VALUE;
        };
    }
    public static int tiempoAtencionPorCategoria(int categoria) {
        return switch (categoria) {
            case 1 -> 30;
            case 2 -> 25;
            case 3 -> 20;
            case 4 -> 15;
            case 5 -> 10;
            default -> 0;
        };
    }

}
