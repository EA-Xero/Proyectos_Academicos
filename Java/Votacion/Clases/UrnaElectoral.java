import java.util.*;

public class UrnaElectoral {
    LinkedList<Candidato> listaCandidatos;
    Stack<Voto> historialVotos;
    Queue<Voto> votosReportados;
    int idCounter;

    public UrnaElectoral() {
        listaCandidatos = new LinkedList<>();
        historialVotos = new Stack<>();
        votosReportados = new LinkedList<>();
        idCounter = 1;
    }

    public void agregarCandidato(Candidato candidato) {
        listaCandidatos.add(candidato);
    }

    public boolean verificarVotante(Votante votante) {
        return votante.isYaVoto();
    }

    public boolean registrarVoto(Votante votante, int candidatoId) {
        if (verificarVotante(votante)) {
            System.out.println("El votante ya ha votado.");
            return false;
        }

        Voto voto = new Voto(idCounter++, votante.getId(), candidatoId);

        for (Candidato candidato : listaCandidatos) {
            if (candidato.getId() == candidatoId) {
                candidato.agregarVoto(voto);
                historialVotos.push(voto);
                votante.marcarVotado();
                return true;
            }
        }
        return false;
    }

    public boolean reportarVoto(Candidato candidato, int idVoto) {
        Queue<Voto> votos = candidato.getVotosRecibidos();
        for (Voto voto : votos) {
            if (voto.getId() == idVoto) {
                votos.remove(voto);
                votosReportados.add(voto);
                return true;
            }
        }
        System.out.println("No se encontró el voto para reportar.");
        return false;
    }

    public String obtenerResultados() {
        StringBuilder resultados = new StringBuilder();
        for (Candidato candidato : listaCandidatos) {
            resultados.append("Candidato: ").append(candidato.getNombre())
                      .append(", Votos: ").append(candidato.getVotosRecibidos().size()).append("\n");
        }
        return resultados.toString();
    }

    // Métodos nuevos
    public boolean existeCandidato(int id) {
        for (Candidato candidato : listaCandidatos) {
            if (candidato.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Candidato obtenerCandidato(int id) {
        for (Candidato candidato : listaCandidatos) {
            if (candidato.getId() == id) {
                return candidato;
            }
        }
        return null;
    }
}
