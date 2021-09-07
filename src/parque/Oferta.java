package parque;

import java.util.List;

public interface Oferta {
	String getNombre();
    double getCosto();
    double getTiempo();
    int getCupo();
    TipoDeAtraccion getTipo();
    List<Atraccion> getAtracciones();
    boolean contieneAtraccion(Atraccion o);
    boolean esPromocion();
}