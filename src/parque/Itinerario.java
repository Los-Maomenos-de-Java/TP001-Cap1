package parque;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {
    private List<Oferta> atracciones = new ArrayList<>();

    public Itinerario(List<Oferta> ofertasVendidas) {
        atracciones.addAll(ofertasVendidas);
    }

    @Override
    public String toString() {
        System.out.println("\n\t\tATRACCIONES COMPRADAS: \n");

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n", "Atracciones", "Costo", "Tiempo");
        System.out.print("\n");

        atracciones
                .stream()
                .filter(Oferta::esPromocion)
                .forEach(promocion -> {
                    System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n",
                            "- " + promocion.getNombre(),
                            "$" + promocion.getCosto(),
                            "⏱ " + promocion.getTiempo());
                    promocion.getAtracciones().forEach(atraccion -> System.out.printf("%-27.27s |%-10.10s |%-10.10s%n",
                            "\t-" + atraccion.getNombre()
                            , "$" + atraccion.getCosto(), ""));
                    System.out.printf("%-27.27s |%-10.10s |%-10.10s%n",
                            "\t-Descuento",
                            "$" + (promocion.getCosto() - promocion.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum()),
                            "");
                    System.out.print("\n");
                });

        atracciones
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .forEach(atraccion -> System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n%n",
                        "- " + atraccion.getNombre(),
                        "$" + atraccion.getCosto(),
                        "⏱ " + atraccion.getTiempo()));

        System.out.println("---------------------------------------------------------------");

        var costoTotal = atracciones.stream().mapToDouble(Oferta::getCosto).sum();
        var tiempoTotal = atracciones.stream().mapToDouble(Oferta::getTiempo).sum();

        System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "$" + costoTotal, "⏱ " + tiempoTotal);

        System.out.println("---------------------------------------------------------------");

        return "";
    }
}