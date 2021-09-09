package parque;

import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.stream.Collectors;


public class Boleteria {
    private static List<Ofertable> ofertables = new ArrayList<>();
    private List<Ofertable> ofertasParaUsuario = new ArrayList<>();
    private Vendedor vendedor = new Vendedor();
    
    public Boleteria() {
    	ofertables = ManejadorDeArchivos.leerAtracciones();
    	ofertables.addAll(ManejadorDeArchivos.leerPromociones());
    }
        
    public static Atraccion obtenerAtraccionPorNombre(String nombre) {
    	for(Ofertable ofertable : ofertables) {
    		if(!ofertable.esPromocion()) {
    			if(ofertable.getNombre() == nombre) {
    				return ofertable.getAtracciones().get(0);
    			}
    		}
    	}
    	return null;
    }
    
    private boolean tieneAtraccionVendida(Ofertable ofertable) {
    	for(Atraccion atraccion: ofertable.getAtracciones()) {
    		if(vendedor.getAtraccionesVendidas().contains(atraccion)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private List<Ofertable> ofertasOrdenadasPara(Usuario usuario){
    	this.ofertasParaUsuario.addAll(ofertables);
    	this.ofertasParaUsuario.sort(new OrdenadorDeOfertas(usuario.getTipoDeAtraccionPreferida()) );
    	this.ofertasFiltradasPara(usuario);
    	return this.ofertasParaUsuario;
    }
    
    private List<Ofertable> ofertasFiltradasPara(Usuario usuario){
    	//remover oferta si alguna de las: oferta.Atracciones est� en atraccionesVendidas
    	this.ofertasParaUsuario.removeIf(oferta -> tieneAtraccionVendida(oferta));
    	
    	this.ofertasParaUsuario.removeIf(oferta -> (oferta.getCupo() ==0));
    	this.ofertasParaUsuario.removeIf(oferta -> (oferta.getCosto() > usuario.getPresupuesto()));
    	this.ofertasParaUsuario.removeIf(oferta -> (oferta.getTiempo() > usuario.getTiempoDisponible()));
    	return this.ofertasParaUsuario;
    }
    
    public void ofrecerA(Usuario usuario) {
    	this.ofertasOrdenadasPara(usuario);
    	this.vendedor.iniciarVenta(usuario);
    	while(!this.ofertasParaUsuario.isEmpty() && usuario.getPresupuesto() > 0 && usuario.getTiempoDisponible() > 0) {
    		Ofertable ofertableSugerida = this.ofertasParaUsuario.remove(0);
    		if(this.vendedor.ofrecer(ofertableSugerida)) {
    			usuario.comprarAtraccion(ofertableSugerida);
    			this.ofertasFiltradasPara(usuario);
    			vendedor.continuarVenta(usuario);
    		}
    	}
    	vendedor.mostrarItinerario();
    	vendedor.generarTicket(usuario);
    }
}