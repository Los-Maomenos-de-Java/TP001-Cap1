package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Itinerario {
    private List<Ofertable> ofertas = new ArrayList<>();

    public Itinerario(List<Ofertable> ofertasVendidas) {
        ofertas.addAll(ofertasVendidas);
    }

    public List<Atraccion> getOfertas() {
        return this.ofertas
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

        ofertas
                .stream()
                .filter(Ofertable::esPromocion)
                .forEach(promocion -> {
                    System.out.printf("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n",
                            "- " + promocion.getNombre(),
                            "  $" + String.format("%.2f", promocion.getCosto()),
                            "  ⏱ " + String.format("%.2f", promocion.getTiempo()),
                            "     " + promocion.getTipo().toString().charAt(0)
                                    + promocion.getTipo().toString().substring(1).toLowerCase());
                    promocion.getAtracciones().forEach(atraccion -> System.out.printf("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-" + atraccion.getNombre()
                            , "  $" + String.format("%.2f",atraccion.getCosto()), "", ""));
                    System.out.printf("|%-27.27s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-Descuento",
                            "  $" + String.format("%.2f",(promocion.getCosto() - promocion.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum())),
                            "", "");
                    System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                });

        ofertas
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .forEach(atraccion -> {
                    System.out.printf("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n",
                            "- " + atraccion.getNombre(),
                            "  $" + String.format("%.2f",atraccion.getCosto()),
                            "  ⏱ " + String.format("%.2f",atraccion.getTiempo()),
                            "     " + atraccion.getTipo().toString().charAt(0)
                                    + atraccion.getTipo().toString().substring(1).toLowerCase());
                    System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                });

        var costoTotal = ofertas.stream().mapToDouble(Ofertable::getCosto).sum();
        var tiempoTotal = ofertas.stream().mapToDouble(Ofertable::getTiempo).sum();

        System.out.printf("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "  $" + String.format("%.2f",costoTotal), "  ⏱ " + String.format("%.2f",tiempoTotal));

        System.out.println("-----------------------------------------------------------------------------");

        return "";
    }
}