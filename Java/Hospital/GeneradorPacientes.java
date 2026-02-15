import java.io.*;
import java.util.*;

public class GeneradorPacientes {
    private static final Random random = new Random();
    private static final String[] nombres = {"Juan", "Maria", "Pedro", "Ana", "Luis", "Lucia"};
    private static final String[] apellidos = {"Gomez", "Perez", "Lopez", "Diaz", "Torres"};

    public static List<Paciente> generarPacientes(int cantidad, long timestampInicio) {
        List<Paciente> lista = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            String nombre = nombres[random.nextInt(nombres.length)];
            String apellido = apellidos[random.nextInt(apellidos.length)];
            String id = "P" + (1000 + i);
            int categoria = generarCategoria();
            long tiempoLlegada = timestampInicio + (i * 10);
            String area = asignarArea();
            Paciente p = new Paciente(nombre, apellido, id, categoria,"en_espera", area);
            lista.add(p);
        }
        return lista;
    }

    private static int generarCategoria() {
        int r = random.nextInt(100);
        if (r < 10) return 1;
        if (r < 25) return 2;
        if (r < 43) return 3;
        if (r < 70) return 4;
        return 5;
    }

    private static String asignarArea() {
        int r = random.nextInt(3);
        return r == 0 ? "SAPU" : (r == 1 ? "urgencia_adulto" : "infantil");
    }

    public static void guardarEnArchivo(List<Paciente> pacientes, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            for (Paciente p : pacientes) {
                writer.println(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}