package parque;

import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.stream.Collectors;

public class Boleteria {
    private static List<Oferta> ofertas = new ArrayList<>();
    private List<Oferta> ofertasParaUsuario = new ArrayList<>();
    private Vendedor vendedor = new Vendedor();
    
    public Boleteria() {
    	ofertas = ManejadorDeArchivos.leerAtracciones();
    	ofertas.addAll(ManejadorDeArchivos.leerPromociones());
    }
        
    public static Atraccion obtenerAtraccionPorNombre(String nombre) {
    	for(Oferta oferta:ofertas) {
    		if(!oferta.esPromocion()) {
    			if(oferta.getNombre() == nombre) {
    				return oferta.getAtracciones().get(0);
    			}
    		}
    	}
    	return null;
    }
    
    private boolean tieneAtraccionVendida(Oferta oferta) {
    	for(Atraccion atraccion:oferta.getAtracciones()) {
    		if(vendedor.getAtraccionesVendidas().contains(atraccion)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private List<Oferta> ofertasOrdenadasPara(Usuario usuario){
    	this.ofertasParaUsuario.addAll(ofertas);
    	this.ofertasParaUsuario.sort(new OrdenadorDeOfertas(usuario.getTipoDeAtraccionPreferida()) );
    	this.ofertasFiltradasPara(usuario);
    	return this.ofertasParaUsuario;
    }
    
    private List<Oferta> ofertasFiltradasPara(Usuario usuario){    	
    	//remover oferta si alguna de las: oferta.Atracciones está en atraccionesVendidas
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
    		Oferta ofertaSugerida = this.ofertasParaUsuario.remove(0);
    		if(this.vendedor.ofrecer(ofertaSugerida)) {
    			usuario.comprarAtraccion(ofertaSugerida);
    			this.ofertasFiltradasPara(usuario);
    			vendedor.continuarVenta(usuario);
    		}
    	}
    	vendedor.mostrarItinerario();
    	vendedor.generarTicket(usuario);
    }
}