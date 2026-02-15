import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UrnaElectoral urna = new UrnaElectoral();
        Map<Integer, Votante> votantes = new HashMap<>();

        while (true) {
            System.out.println("\n----- Sistema de Votaciones Electo -----");
            System.out.println("1. Agregar Candidato");
            System.out.println("2. Registrar Votante");
            System.out.println("3. Votar");
            System.out.println("4. Reportar Voto");
            System.out.println("5. Ver Resultados");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID del Candidato: ");
                    int idCandidato = scanner.nextInt();
                    scanner.nextLine();
                    if (urna.existeCandidato(idCandidato)) {
                        System.out.println("Ya existe un candidato con ese ID.");
                        break;
                    }
                    System.out.print("Ingrese el nombre del Candidato: ");
                    String nombreCandidato = scanner.nextLine();
                    System.out.print("Ingrese el partido del Candidato: ");
                    String partido = scanner.nextLine();
                    Candidato candidato = new Candidato(idCandidato, nombreCandidato, partido);
                    urna.agregarCandidato(candidato);
                    System.out.println("Candidato agregado exitosamente.");
                    break;

                case 2:
                    System.out.print("Ingrese el ID del Votante: ");
                    int idVotante = scanner.nextInt();
                    scanner.nextLine();
                    if (votantes.containsKey(idVotante)) {
                        System.out.println("Ya existe un votante con ese ID.");
                        break;
                    }
                    System.out.print("Ingrese el nombre del Votante: ");
                    String nombreVotante = scanner.nextLine();
                    Votante votante = new Votante(idVotante, nombreVotante);
                    votantes.put(idVotante, votante);
                    System.out.println("Votante registrado exitosamente.");
                    break;

                case 3:
                    System.out.print("Ingrese el ID del Votante: ");
                    int idVotanteVotar = scanner.nextInt();
                    System.out.print("Ingrese el ID del Candidato: ");
                    int idCandidatoVotar = scanner.nextInt();
                    Votante votanteVotar = votantes.get(idVotanteVotar);
                    if (votanteVotar == null) {
                        System.out.println("Votante no encontrado.");
                        break;
                    }
                    if (!urna.existeCandidato(idCandidatoVotar)) {
                        System.out.println("Candidato no encontrado.");
                        break;
                    }
                    boolean exito = urna.registrarVoto(votanteVotar, idCandidatoVotar);
                    if (exito) {
                        System.out.println("Voto registrado correctamente.");
                    } else {
                        System.out.println("No se pudo registrar el voto (quizás ya votó).");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el ID del Candidato: ");
                    int idCandidatoReportar = scanner.nextInt();
                    System.out.print("Ingrese el ID del Voto a reportar: ");
                    int idVotoReportar = scanner.nextInt();
                    Candidato candidatoReportar = urna.obtenerCandidato(idCandidatoReportar);
                    if (candidatoReportar == null) {
                        System.out.println("Candidato no encontrado.");
                        break;
                    }
                    boolean reportado = urna.reportarVoto(candidatoReportar, idVotoReportar);
                    if (reportado) {
                        System.out.println("Voto reportado correctamente.");
                    } else {
                        System.out.println("No se pudo reportar el voto.");
                    }
                    break;

                case 5:
                    System.out.println("--- Resultados ---");
                    System.out.println(urna.obtenerResultados());
                    break;

                case 6:
                    System.out.println("Saliendo del sistema de votaciones.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}
