package parque;

public class PromocionPorcentual extends Promocion {
    private double porcentajeDescuento;

    public PromocionPorcentual(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double getCosto() {
        var porcentaje = 1 - porcentajeDescuento / 100;

        return costoTotalAtracciones() * porcentaje;
    }
}