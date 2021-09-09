package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private String nombre;
    private double presupuesto;
    private double tiempoDisponible;
    private TipoDeAtraccion tipoDeAtraccionPreferida;
    private List<Ofertable> ofertasCompradas = new ArrayList<>();

    public Usuario(String nombre, double presupuesto, double tiempoDisponible, TipoDeAtraccion tipoDeAtraccionPreferida) {
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.tiempoDisponible = tiempoDisponible;
        this.tipoDeAtraccionPreferida = tipoDeAtraccionPreferida;
    }

    public void comprarAtraccion(Ofertable o) {
        presupuesto -= o.getCosto();
        tiempoDisponible -= o.getTiempo();
        ofertasCompradas.add(o);
    }

    public boolean esDelTipoQueLeGusta(Ofertable ofertable) {
        return ofertable.getTipo() == this.tipoDeAtraccionPreferida;
    }

    public boolean puedeVisitar(Ofertable ofertable) {
        return this.presupuesto >= ofertable.getCosto() && this.tiempoDisponible >= ofertable.getTiempo();
    }

    public boolean comproLaAtraccion(Ofertable ofertable) {
        return getAtraccionesCompradas()
                .stream()
                .anyMatch(ofertable::contieneAtraccion);
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

    public TipoDeAtraccion getTipoDeAtraccionPreferida() {
        return this.tipoDeAtraccionPreferida;
    }

    public List<Atraccion> getAtraccionesCompradas() {
        return this.ofertasCompradas
                .stream()
                .map(Ofertable::getAtracciones)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}