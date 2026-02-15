import java.util.*;

public class Main {
    public static void main(String[] args) {
        SimuladorUrgencia simulador = new SimuladorUrgencia();
        System.out.println("------------Prueba: Seguimiento Individual ------------");
        simulador.pruebaSeguimientoIndividual();
        System.out.println("------------Prueba: Promedios por categoria ------------");
        simulador.pruebaPromediosPorCategoria();
        System.out.println("------------Prueba: Saturacion Sistema ------------");
        simulador.pruebaSaturacionSistema();
        System.out.println("------------Prueba: Cambio Categoria ------------");
        simulador.pruebaCambioCategoria();
    }
}