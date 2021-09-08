package parque;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    public List<Atraccion> getAtracciones() {
        return this.atracciones;
    }

    @Override
    public String toString() {
        StringBuilder itinerario = new StringBuilder("ATRACCIONES COMPRADAS: " + "\n");

        var groupedByTipo = atracciones
                .stream()
                .collect(Collectors.groupingBy(Atraccion::getTipo, LinkedHashMap::new, Collectors.toList()));

        var atraccionesAgupadas =
                groupedByTipo
                        .entrySet()
                        .stream()
                        .map(entry -> "\n-" + entry.getKey() + ": " + "\n\t-" + entry.getValue().stream().map(Atraccion::getNombre)
                                .collect(Collectors.joining("\n\t-")))
                        .collect(Collectors.joining("\n"));

        itinerario.append(atraccionesAgupadas);

        itinerario.append("\n\n" + "COSTO TOTAL: $ ").append(this.costoTotal).append("\n\nTIEMPO REQUERIDO: ").append(this.tiempoTotal);
        return itinerario.toString();
    }
}