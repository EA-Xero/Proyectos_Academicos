import java.util.Stack;

public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String apellido;
    private String id;
    private int categoria;
    private long tiempoLlegada;
    private String estado;
    private String area;
    private Stack<String> historialCambios;
    private int tiempoAtencion;

    public Paciente(String nombre, String apellido, String id, int categoria,String estado,String area) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.categoria = categoria;
        this.estado = estado;
        setTiempoLlegada(SimuladorUrgencia.minutoActualGlobal);
        this.area = area;
        this.historialCambios = new Stack<>();
    }

    public long tiempoEsperaActual() {
        return Math.max(0, tiempoAtencion - tiempoLlegada);
    }


    public void registrarCambio(String descripcion) {
        historialCambios.push(descripcion);
    }

    public String obtenerUltimoCambio() {
        return historialCambios.isEmpty() ? "Sin cambios registrados" : historialCambios.pop();
    }

    public int getCategoria() {
        return categoria;
    }

    public long getTiempoLlegada() {
        return  tiempoLlegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    public void setTiempoLlegada(int minuto) {
        this.tiempoLlegada = minuto;
    }
    public void setTiempoAtencion(int tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    @Override
    public int compareTo(Paciente otro) {
        if (this.categoria != otro.categoria) {
            return Integer.compare(this.categoria, otro.categoria);
        }
        return Long.compare(this.tiempoLlegada, otro.tiempoLlegada);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " | ID: " + id + " | C" + categoria + " | Estado: " + estado + " | Área: " + area;
    }
    public Stack<String> getHistorialCambios() {
        return historialCambios;
    }
}