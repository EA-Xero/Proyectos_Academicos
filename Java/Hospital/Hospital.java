import java.util.*;

public class Hospital {
    private Map<String, Paciente> pacientesTotales;
    private PriorityQueue<Paciente> colaAtencion;
    private Map<String, AreaAtencion> areasAtencion;
    public List<Paciente> pacientesAtendidos;
    private final List<Paciente> pacientesCriticos = new ArrayList<>();
    public Hospital() {
        this.pacientesTotales = new HashMap<>();
        this.colaAtencion = new PriorityQueue<>(new Comparator<Paciente>() {
            @Override
            public int compare(Paciente p1, Paciente p2) {
                int tiempoActual = SimuladorUrgencia.minutoActualGlobal;

                int prioridad1 = (int) (((6 - p1.getCategoria()) * 1000) + (p1.tiempoEsperaActual()));
                int prioridad2 = (int) (((6 - p2.getCategoria()) * 1000) + (p1.tiempoEsperaActual()));

                return Integer.compare(prioridad2, prioridad1);

            }
        });
        this.areasAtencion = new HashMap<>();
        this.pacientesAtendidos = new ArrayList<>();

        areasAtencion.put("SAPU", new AreaAtencion("SAPU", 50));
        areasAtencion.put("urgencia_adulto", new AreaAtencion("urgencia_adulto", 50));
        areasAtencion.put("infantil", new AreaAtencion("infantil", 50));
    }

    public void registrarPaciente(Paciente p) {
        p.setTiempoLlegada(SimuladorUrgencia.minutoActualGlobal);
        pacientesTotales.put(p.getId(), p);
        colaAtencion.offer(p);
        reorganizarCola();
    }

    public void reasignarCategoria(String id, int nuevaCategoria) {
        Paciente p = pacientesTotales.get(id);
        if (p != null) {
            colaAtencion.remove(p);
            p.setCategoria(nuevaCategoria);
            p.registrarCambio("Categoría reasignada a C" + nuevaCategoria);
            colaAtencion.offer(p);
            reorganizarCola();
        }
    }

    public Paciente atenderSiguiente() {
        Paciente p;
        verificarPacientesCriticos();
        if (!pacientesCriticos.isEmpty()) {
            p = pacientesCriticos.remove(0);  // Atender primero a los críticos
        }
        else{
            p = colaAtencion.poll();
        }
        if (p != null) {
            p.setTiempoAtencion(SimuladorUrgencia.minutoActualGlobal);
            p.setEstado("atendido");
            AreaAtencion area = obtenerArea(p.getArea());
            if (area != null) {
                area.ingresarPaciente(p);
            }
            pacientesAtendidos.add(p);
        }
        reorganizarCola();
        return p;
    }
    public void actualizarPacientesExcedidos(List<Paciente> excedidos, int minutoActual) {
        for (Paciente paciente : colaAtencion) {
            int espera = minutoActual - (int) paciente.getTiempoLlegada();
            int maxPermitido = SimuladorUrgencia.tiempoMaximoPorCategoria(paciente.getCategoria());

            if (espera > maxPermitido && !excedidos.contains(paciente)) {
                excedidos.add(paciente);
            }
        }
    }

    public List<Paciente> obtenerPacientesPorCategoria(int categoria) {
        List<Paciente> lista = new ArrayList<>();
        for (Paciente p : colaAtencion) {
            if (p.getCategoria() == categoria) {
                lista.add(p);
            }
        }
        return lista;
    }

    public AreaAtencion obtenerArea(String nombre) {
        return areasAtencion.get(nombre);
    }

    public List<Paciente> getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public PriorityQueue<Paciente> getColaAtencion() {
        return colaAtencion;
    }
    public void reorganizarCola() {
        List<Paciente> temp = new ArrayList<>(colaAtencion);
        colaAtencion.clear();
        for (Paciente p : temp) {
            colaAtencion.offer(p);
        }
    }
    public void verificarPacientesCriticos() {
        List<Paciente> nuevosCriticos = new ArrayList<>();
        for (Paciente p : colaAtencion) {
            int espera = SimuladorUrgencia.minutoActualGlobal - (int) p.getTiempoLlegada();
            int maxEspera = SimuladorUrgencia.tiempoMaximoPorCategoria(p.getCategoria());
            if (espera >= maxEspera && !pacientesCriticos.contains(p)) {
                SimuladorUrgencia.excedentesPorCategoria.put(
                        p.getCategoria(),
                        SimuladorUrgencia.excedentesPorCategoria.get(p.getCategoria()) + 1
                );
                nuevosCriticos.add(p);
            }
        }

        for (Paciente p : nuevosCriticos) {
            colaAtencion.remove(p);
            pacientesCriticos.add(p);
        }
    }

}