package parque;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {
	private List<Atraccion> atracciones = new ArrayList<>();
	private double costoTotal;
	private double tiempoTotal;
	
	public Itinerario(List<Oferta> ofertasVendidas) {
		ofertasVendidas.forEach(oferta -> cargarDatosDeOferta(oferta));
	}
	
	private void cargarDatosDeOferta(Oferta oferta) {
		oferta.getAtracciones().forEach(atraccion -> atracciones.add(atraccion));
		this.costoTotal += oferta.getCosto();
		this.tiempoTotal += oferta.getTiempo();
	}
	
	public List<Atraccion> getAtracciones(){
		return this.atracciones;
	}
	
	@Override
	public String toString() {
		String itinerario = "ATRACCIONES COMPRADAS:";
		for(Atraccion atraccion:this.atracciones) {
			itinerario += "\n\t-"+atraccion.getNombre()+": "+atraccion.getTipo();
		}
		itinerario += "\n\n"+"COSTO TOTAL: $ "+this.costoTotal+"\n\nTIEMPO REQUERIDO: "+this.tiempoTotal;
		return itinerario;
	}
}
