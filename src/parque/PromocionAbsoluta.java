package parque;

public class PromocionAbsoluta extends Promocion {
    private double descuentoAbsoluto;

    public PromocionAbsoluta(double descuentoAbsoluto) {
        this.descuentoAbsoluto = descuentoAbsoluto;
    }

    @Override
    public double getCosto() {
        return costoTotalAtracciones() - descuentoAbsoluto;
    }
}