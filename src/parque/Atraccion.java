package parque;

import java.util.List;
import java.util.Objects;

public class Atraccion implements Ofertable {
    private String nombre;
    private double costoVisita;
    private double tiempoPromedio;
    private TipoDeAtraccion tipoDeAtraccion;
    private int cupo;

    public Atraccion(String nombre, double costoVisita, double tiempoPromedio, TipoDeAtraccion tipoDeAtraccion, int cupo) {
        this.nombre = nombre;
        this.costoVisita = costoVisita;
        this.tiempoPromedio = tiempoPromedio;
        this.tipoDeAtraccion = tipoDeAtraccion;
        this.cupo = cupo;
    }

    public void serComprada() {
        this.cupo--;
    }

    @Override
    public boolean tieneCupo() {
        return this.cupo > 0;
    }

    @Override
    public String getNombre() {
    	return this.nombre;
    }

    @Override
    public double getCosto() {
        return this.costoVisita;
    }

    @Override
    public double getTiempo() {
        return this.tiempoPromedio;
    }

    @Override
    public int getCupo() {
        return this.cupo;
    }

    @Override
    public TipoDeAtraccion getTipo() {
        return this.tipoDeAtraccion;
    }

    @Override
    public List<Atraccion> getAtracciones() {
        return List.of(this);
    }

    @Override
    public boolean esPromocion() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atraccion atraccion = (Atraccion) o;
        return Double.compare(atraccion.costoVisita, costoVisita) == 0 && Double.compare(atraccion.tiempoPromedio, tiempoPromedio) == 0 && nombre.equals(atraccion.nombre) && tipoDeAtraccion == atraccion.tipoDeAtraccion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, costoVisita, tiempoPromedio, tipoDeAtraccion);
    }

    @Override
    public String toString() {
        return "Atraccion {" +
                "Nombre: " + nombre +
                ", Costo Visita: " + costoVisita +
                ", Tiempo Promedio: " + tiempoPromedio +
                ", Tipo de Atraccion=" + tipoDeAtraccion +
                " }";
    }
}
