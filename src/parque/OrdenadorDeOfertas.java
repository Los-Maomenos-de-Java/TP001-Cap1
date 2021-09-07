package parque;

import java.util.Comparator;

public class OrdenadorDeOfertas implements Comparator<Oferta> {
	private TipoDeAtraccion tipoFavorito;

	public OrdenadorDeOfertas(TipoDeAtraccion tipoAtraccionFavorito) {
		this.tipoFavorito = tipoAtraccionFavorito;
	}

	@Override
	public int compare(Oferta o1, Oferta o2) {
		// Prioridad atraccion favorita
		if(o1.getTipo() == tipoFavorito && o2.getTipo() != tipoFavorito) {
			return -1;
		}else if(o1.getTipo() != tipoFavorito && o2.getTipo() == tipoFavorito) {
			return 1;
		}
		
		//Prioridad promocion
		if(o1.esPromocion() && !o2.esPromocion()) {
			return -1;
		}else if(!o1.esPromocion() && o2.esPromocion()) {
			return 1;
		}
		
		//Priodidad por oro
		if(o1.getCosto() > o2.getCosto()) {
			return -1;
		}else if(o1.getCosto() < o2.getCosto()) {
			return 1;
		}
		
		//Prioridad por tiempo
		return (int) -(o1.getTiempo() - o2.getTiempo());
	}
}
