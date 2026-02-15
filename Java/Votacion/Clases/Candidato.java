import java.util.LinkedList;
import java.util.Queue;

public class Candidato {
    private int id;
    private String nombre;
    private String partido;
    private Queue<Voto> votosRecibidos;

    public Candidato(int id, String nombre, String partido) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.votosRecibidos = new LinkedList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPartido() { return partido; }
    public Queue<Voto> getVotosRecibidos() { return votosRecibidos; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPartido(String partido) { this.partido = partido; }

    public void agregarVoto(Voto v) {
        votosRecibidos.add(v);
    }
}
