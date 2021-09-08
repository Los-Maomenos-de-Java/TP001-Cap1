package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Itinerario {
    private List<Atraccion> atracciones = new ArrayList<>();
    private double costoTotal;
    private double tiempoTotal;

    public Itinerario(List<Oferta> ofertasVendidas) {
        ofertasVendidas.forEach(this::cargarDatosDeOferta);
    }

    private void cargarDatosDeOferta(Oferta oferta) {
        atracciones.addAll(oferta.getAtracciones());
        this.costoTotal += oferta.getCosto();
        this.tiempoTotal += oferta.getTiempo();
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
                    promocion.getAtracciones().forEach(atraccion -> System.out.printf("%-25.25s %-10.10s %-10.10s%n",
                            "\t-" + atraccion.getNombre()
                            , "$" + atraccion.getCosto(), ""));
                    System.out.printf("%-25.25s %-10.10s %-10.10s%n",
                            "\t-Descuento",
                            "$" + (promocion.getCosto() - promocion.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum()),
                            "");
                    System.out.print("\n");
                });

        atracciones
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .map(atraccion -> System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n%n",
                        "- " + atraccion.getNombre(),
                        "$" + atraccion.getCosto(),
                        "⏱ " + atraccion.getTiempo()))
                .collect(Collectors.toList());

        System.out.println("---------------------------------------------------------------");

        var costoTotal = atracciones.stream().mapToDouble(Oferta::getCosto).sum();
        var tiempoTotal = atracciones.stream().mapToDouble(Oferta::getTiempo).sum();

        System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "$" + costoTotal, "⏱ " + tiempoTotal);

        System.out.println("---------------------------------------------------------------");

        return "";
    }
}