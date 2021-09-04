package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private String nombre;
    private double presupuesto;
    private double tiempoDisponible;
    private TipoDeAtraccion tipoDeAtraccionPreferida;
    private List<Oferta> ofertasCompradas = new ArrayList<>();

    public Usuario(String nombre, double presupuesto, double tiempoDisponible, TipoDeAtraccion tipoDeAtraccionPreferida) {
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.tiempoDisponible = tiempoDisponible;
        this.tipoDeAtraccionPreferida = tipoDeAtraccionPreferida;
    }

    public void comprarAtraccion(Oferta o) {
        presupuesto -= o.getCosto();
        tiempoDisponible -= o.getTiempo();
        o.serComprada();
        ofertasCompradas.add(o);
    }

    public boolean esDelTipoQueLeGusta(Oferta oferta) {
        return oferta.getTipo() == this.tipoDeAtraccionPreferida;
    }

    public boolean puedeVisitar(Oferta oferta) {
        return this.presupuesto >= oferta.getCosto() && this.tiempoDisponible >= oferta.getTiempo();
    }

    public boolean comproLaAtraccion(Oferta oferta) {
        return getAtraccionesCompradas()
                .stream()
                .anyMatch(oferta::contieneAtraccion);
    }

    public List<Atraccion> getAtraccionesCompradas() {
        return this.ofertasCompradas
                .stream()
                .map(Oferta::getAtracciones)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}