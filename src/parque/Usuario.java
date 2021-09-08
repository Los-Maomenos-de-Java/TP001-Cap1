package parque;

import java.util.ArrayList;
import java.util.List;

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
        ofertasCompradas.add(o);
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
}