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

    public void comprarAtraccion(Oferta oferta) {
        presupuesto -= oferta.getCosto();
        tiempoDisponible -= oferta.getTiempo();
        ofertasCompradas.add(oferta);
    }

    public boolean comproLaOferta(Oferta oferta) {
        return getAtraccionesCompradas().containsAll(oferta.getAtracciones());
    }

    public List<Atraccion> getAtraccionesCompradas() {
        return this.ofertasCompradas
                .stream()
                .map(Oferta::getAtracciones)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public double getTiempoDisponible() {
        return tiempoDisponible;
    }

    public boolean puedeVisitar(Oferta oferta) {
        return this.tiempoDisponible >= oferta.getTiempo() && this.presupuesto >= oferta.getCosto();
    }

    public boolean esDelTipoQueLeGusta(Oferta oferta) {
        return this.tipoDeAtraccionPreferida.equals(oferta.getTipo());
    }
}