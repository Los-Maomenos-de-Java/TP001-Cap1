package parque;

public class PromocionAbsoluta extends Promocion {
    private double descuentoAbsoluto;

    public PromocionAbsoluta(String nombre,double descuentoAbsoluto) {
    	super(nombre);
        this.descuentoAbsoluto = descuentoAbsoluto;
    }

    @Override
    public double getCosto() {
        return costoTotalAtracciones() - descuentoAbsoluto;
    }
}