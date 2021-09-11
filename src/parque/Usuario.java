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
    private List<Ofertable> ofertasCompradas = new ArrayList<>();

    public Usuario(String nombre, double presupuestoActual, double tiempoDisponible, TipoDeAtraccion tipoDeAtraccionPreferida) {
        this.nombre = nombre;
        this.PRESUPUESTO_INICIAL = presupuestoActual;
        this.TIEMPO_INICIAL = tiempoDisponible;
        this.presupuestoActual = PRESUPUESTO_INICIAL;
        this.tiempoDisponible = TIEMPO_INICIAL;
        this.tipoDeAtraccionPreferida = tipoDeAtraccionPreferida;
    }

    public boolean comprarOferta(Ofertable ofertable) {
        if (ofertable.tieneCupo()) {
            presupuestoActual -= ofertable.getCosto();
            tiempoDisponible -= ofertable.getTiempo();
            ofertasCompradas.add(ofertable);
            return true;
        }
        return false;
    }

    public boolean puedeVisitar(Ofertable ofertable) {
        return this.tiempoDisponible >= ofertable.getTiempo() && this.presupuestoActual >= ofertable.getCosto();
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

    public TipoDeAtraccion getTipoDeAtraccionPreferida() {
        return this.tipoDeAtraccionPreferida;
    }
    
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario that = (Usuario) o;
		return nombre.equals(that.nombre) 
				&& Double.compare(presupuestoActual,that.presupuestoActual) == 0
				&& Double.compare(tiempoDisponible,that.tiempoDisponible) == 0
				&& tipoDeAtraccionPreferida.equals(that.tipoDeAtraccionPreferida);
	}
}