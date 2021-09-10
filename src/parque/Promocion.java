package parque;

import java.util.ArrayList;
import java.util.List;

public abstract class Promocion implements Ofertable {
    protected List<Atraccion> atracciones = new ArrayList<>();
    private String nombre;

    public Promocion(String nombre) {
        this.nombre = nombre;
    }

    protected void agregarAtraccion(Atraccion a) {
        atracciones.add(a);
    }

    protected double costoTotalAtracciones() {
        return atracciones
                .stream()
                .mapToDouble(Atraccion::getCosto)
                .sum();
    }

    @Override
    public void serComprada() {
        for (Atraccion atraccion : atracciones) {
            atraccion.serComprada();
        }
    }

    @Override
    public boolean tieneCupo() {
        return this.getCupo() > 0;
    }
    
    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public double getTiempo() {
        return atracciones
                .stream()
                .mapToDouble(Atraccion::getTiempo)
                .sum();
    }

    @Override
    public int getCupo() {
        return atracciones
                .stream()
                .mapToInt(Atraccion::getCupo)
                .min()
                .orElseThrow(() -> new RuntimeException("No hay atracciones en la promo"));
    }

    @Override
    public TipoDeAtraccion getTipo() {
        return atracciones
                .stream()
                .map(Ofertable::getTipo)
                .findAny()
                .orElseThrow(() -> new RuntimeException("La promoción no contiene atracciones"));
    }

    public List<Atraccion> getAtracciones() {
        return this.atracciones;
    }

    @Override
    public boolean esPromocion() {
        return true;
    }
}