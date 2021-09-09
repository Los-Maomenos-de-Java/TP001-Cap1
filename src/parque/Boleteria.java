package parque;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Boleteria {
    public static List<Oferta> ofertas = new ArrayList<>();
    private Queue<Oferta> ofertasParaUsuario = new ArrayDeque<>();
    private Vendedor vendedor = new Vendedor();

    public Boleteria() {
        ofertas = ManejadorDeArchivos.leerAtracciones();
        ofertas.addAll(ManejadorDeArchivos.leerPromociones());
    }

    public static Atraccion obtenerAtraccionPorNombre(String nombre) {
        return ofertas
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .filter(oferta -> oferta.getNombre().equals(nombre))
                .findFirst()
                .map(Oferta::getAtracciones)
                .filter(list -> !list.isEmpty())
                .map(atracciones -> atracciones.get(0))
                .orElse(null);
    }

    private Queue<Oferta> ofertasOrdenadasPara(Usuario usuario) {
        ofertasParaUsuario = ofertas
                .stream()
                .filter(Oferta::tieneCupo)
                .filter(usuario::puedeVisitar)
                .filter(oferta -> !usuario.comproLaAtraccion(oferta))
                .sorted(Comparator.comparing(usuario::esDelTipoQueLeGusta)
                        .thenComparing(Oferta::esPromocion)
                        .thenComparing(Oferta::getCosto)
                        .thenComparing(Oferta::getTiempo).reversed())
                .collect(Collectors.toCollection(ArrayDeque::new));

        return ofertasParaUsuario;
    }

    public void ofrecerA(Usuario usuario) throws IOException {
        ofertasOrdenadasPara(usuario);
        this.vendedor.iniciarVenta(usuario);

        while (!this.ofertasParaUsuario.isEmpty() && usuario.getPresupuesto() > 0 && usuario.getTiempoDisponible() > 0) {
            Oferta ofertaSugerida = this.ofertasParaUsuario.poll();
            if (this.vendedor.ofrecer(ofertaSugerida)) {
                //usuario.comprarAtraccion(ofertaSugerida);
                //ofertaSugerida.serComprada();
                vendedor.continuarVenta(usuario);
            }
            ofertasOrdenadasPara(usuario);
        }
        vendedor.mostrarItinerario();
        vendedor.generarTicket(usuario);
    }
}