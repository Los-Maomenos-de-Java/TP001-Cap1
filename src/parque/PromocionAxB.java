package parque;

import java.util.ArrayList;
import java.util.List;

public class PromocionAxB extends Promocion {
	private List<Atraccion> atraccionesGratis = new ArrayList<>();
	
	public PromocionAxB(String nombre,String [] atraccionesGratis) {
		super(nombre);
		for(String atraccion:atraccionesGratis) {
			this.agregarAtraccionGratis(atraccion);
		}
	}
	
    public void agregarAtraccionGratis(String nombreAtraccion) {
    	for(Atraccion atraccion:this.atracciones) {
    		if(atraccion.getNombre() == nombreAtraccion) {
    	    	this.atraccionesGratis.add(atraccion);
    		}
    	}
    }
    
    @Override
    public double getCosto() {
        var costoAtraccionesGratis = atraccionesGratis
                .stream()
                .mapToDouble(Atraccion::getCosto)
                .sum();

        return costoTotalAtracciones() - costoAtraccionesGratis;
    }
}