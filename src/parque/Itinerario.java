package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Itinerario {
    private List<Ofertable> atracciones = new ArrayList<>();

    public Itinerario(List<Ofertable> ofertasVendidas) {
        atracciones.addAll(ofertasVendidas);
    }

    public List<Atraccion> getAtracciones() {
        return this.atracciones
                .stream()
                .map(Ofertable::getAtracciones)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        System.out.println("\n\t\t\t\tATRACCIONES COMPRADAS: \n");

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n", "        Atracciones", "   Costo", "  Tiempo", " Tipo de Atracción");
        System.out.println("-----------------------------------------------------------------------------");

        atracciones
                .stream()
                .filter(Ofertable::esPromocion)
                .forEach(promocion -> {
                    System.out.printf("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n",
                            "- " + promocion.getNombre(),
                            "  $" + promocion.getCosto(),
                            "  ⏱ " + promocion.getTiempo(),
                            "     " + promocion.getTipo().toString().charAt(0)
                                    + promocion.getTipo().toString().substring(1).toLowerCase());
                    promocion.getAtracciones().forEach(atraccion -> System.out.printf("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-" + atraccion.getNombre()
                            , "  $" + atraccion.getCosto(), "", ""));
                    System.out.printf("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-Descuento",
                            "  $" + (promocion.getCosto() - promocion.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum()),
                            "", "");
                    System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                });

        atracciones
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .forEach(atraccion -> System.out.printf("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n",
                        "- " + atraccion.getNombre(),
                        "  $" + atraccion.getCosto(),
                        "  ⏱ " + atraccion.getTiempo(),
                        "     " + atraccion.getTipo().toString().charAt(0)
                                + atraccion.getTipo().toString().substring(1).toLowerCase()))
        ;
        System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");


        System.out.println("-----------------------------------------------------------------------------");

        var costoTotal = atracciones.stream().mapToDouble(Ofertable::getCosto).sum();
        var tiempoTotal = atracciones.stream().mapToDouble(Ofertable::getTiempo).sum();

        System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "  $" + costoTotal, "  ⏱ " + tiempoTotal);

        System.out.println("-----------------------------------------------------------------------------");

        return "";
    }
}