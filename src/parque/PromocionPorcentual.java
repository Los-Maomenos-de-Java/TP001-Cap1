package parque;

public class PromocionPorcentual extends Promocion {
    private double porcentajeDescuento;

    public PromocionPorcentual(String nombre, int porcentajeDescuento) {
    	super(nombre);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getCosto() {
        var porcentaje = 1 - porcentajeDescuento / 100;

        return costoTotalAtracciones() * porcentaje;
    }
}