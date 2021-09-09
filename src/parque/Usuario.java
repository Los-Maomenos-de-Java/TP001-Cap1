package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private String nombre;
    private double presupuestoActual;
    private double tiempoDisponible;
    private final double PRESUPUESTO_INICIAL;
    private final double TIEMPO_INICIAL;
    private TipoDeAtraccion tipoDeAtraccionPreferida;
    private List<Oferta> ofertasCompradas = new ArrayList<>();

    public Usuario(String nombre, double presupuesto, double tiempoDisponible, TipoDeAtraccion tipoDeAtraccionPreferida) {
        this.nombre = nombre;
        this.PRESUPUESTO_INICIAL = presupuesto;
        this.TIEMPO_INICIAL = tiempoDisponible;
        this.presupuestoActual = PRESUPUESTO_INICIAL;
        this.tiempoDisponible = TIEMPO_INICIAL;
        this.tipoDeAtraccionPreferida = tipoDeAtraccionPreferida;
    }

    public void comprarAtraccion(Oferta oferta) {
        presupuestoActual -= oferta.getCosto();
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

    public boolean puedeVisitar(Oferta oferta) {
        return this.tiempoDisponible >= oferta.getTiempo() && this.presupuestoActual >= oferta.getCosto();
    }

    public boolean esDelTipoQueLeGusta(Oferta oferta) {
        return this.tipoDeAtraccionPreferida.equals(oferta.getTipo());
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuestoActual() {
        return presupuestoActual;
    }

    public double getTiempoDisponible() {
        return tiempoDisponible;
    }

    public double getPresupuestoInicial() {
        return this.PRESUPUESTO_INICIAL;
    }

    public double getTiempoInicial() {
        return this.TIEMPO_INICIAL;
    }
}