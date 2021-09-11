package parque;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.stream.Collectors;


public class Boleteria {
    private static List<Ofertable> ofertables = new ArrayList<>();
    private List<Ofertable> ofertasParaUsuario = new ArrayList<>();
    private Vendedor vendedor = new Vendedor();
    
    public Boleteria() {
    	String archivoAtracciones = "archivos/AtraccionesSimpson.txt";
    	String archivoPromociones = "archivos/PromocionesSimpsons.txt";
    	ofertables = ManejadorDeArchivos.leerAtracciones(archivoAtracciones);
    	ofertables.addAll(ManejadorDeArchivos.leerPromociones(archivoPromociones));
    }
        
    public static Atraccion obtenerAtraccionPorNombre(String nombre) {
    	for(Ofertable ofertable : ofertables) {
    		if(!ofertable.esPromocion()) {
    			if(ofertable.getNombre().equals(nombre)) {
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
    	this.ofertasParaUsuario.removeIf(this::tieneAtraccionVendida);
    	this.ofertasParaUsuario.removeIf(ofertable -> !ofertable.tieneCupo());
    	this.ofertasParaUsuario.removeIf(ofertable -> !usuario.puedeVisitar(ofertable));

    	return this.ofertasParaUsuario;
    }
    
    public void ofrecerA(Usuario usuario) throws IOException {
    	this.ofertasOrdenadasPara(usuario);
    	this.vendedor.iniciarVenta(usuario);
    	while(!this.ofertasParaUsuario.isEmpty() && usuario.getPresupuestoActual() > 0 && usuario.getTiempoDisponible() > 0) {
    		Ofertable ofertableSugerida = this.ofertasParaUsuario.remove(0);
    		if(this.vendedor.ofrecer(ofertableSugerida)) {
    			usuario.comprarOferta(ofertableSugerida);
    			this.ofertasFiltradasPara(usuario);
    			vendedor.continuarVenta(usuario);
    		}
    	}
    	vendedor.mostrarItinerario();
    	vendedor.generarTicket(usuario);
    }
}