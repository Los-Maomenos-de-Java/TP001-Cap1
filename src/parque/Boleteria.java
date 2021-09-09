package parque;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Boleteria {
    public static List<Oferta> ofertas = new ArrayList<>();
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

    private Queue<Oferta> ofertasOrdenadasPara(Usuario usuario, Stream<Oferta> ofertasParaUsuario) {
        return ofertasParaUsuario
                .filter(Oferta::tieneCupo)
                .filter(usuario::puedeVisitar)
                .filter(oferta -> !usuario.comproLaOferta(oferta))
                .sorted(Comparator.comparing(usuario::esDelTipoQueLeGusta)
                        .thenComparing(Oferta::esPromocion)
                        .thenComparing(Oferta::getCosto)
                        .thenComparing(Oferta::getTiempo).reversed())
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public void ofrecerA(Usuario usuario) throws IOException {
        var ofertasParaUsuario = ofertasOrdenadasPara(usuario, ofertas.stream());
        this.vendedor.iniciarVenta(usuario);

        while (!ofertasParaUsuario.isEmpty() && usuario.getPresupuestoActual() > 0 && usuario.getTiempoDisponible() > 0) {
            Oferta ofertaSugerida = ofertasParaUsuario.poll();
            if (this.vendedor.ofrecer(ofertaSugerida)) {
                usuario.comprarAtraccion(ofertaSugerida);
                ofertaSugerida.serComprada();
                vendedor.continuarVenta(usuario);
            }
            ofertasParaUsuario = ofertasOrdenadasPara(usuario, ofertasParaUsuario.stream());
        }
        vendedor.mostrarItinerario();
        vendedor.generarTicket(usuario);
    }
}