package parque;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Boleteria {
    public static List<Ofertable> ofertables = new ArrayList<>();
    private Vendedor vendedor = new Vendedor();

    public Boleteria() {
        ofertables = ManejadorDeArchivos.leerAtracciones();
        ofertables.addAll(ManejadorDeArchivos.leerPromociones());
    }

    public static Atraccion obtenerAtraccionPorNombre(String nombre) {
        return ofertables
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .filter(oferta -> oferta.getNombre().equals(nombre))
                .findFirst()
                .map(Ofertable::getAtracciones)
                .filter(list -> !list.isEmpty())
                .map(atracciones -> atracciones.get(0))
                .orElse(null);
    }

    private Queue<Ofertable> ofertasOrdenadasPara(Usuario usuario, Stream<Ofertable> ofertasParaUsuario) {
        return ofertasParaUsuario
                .filter(Ofertable::tieneCupo)
                .filter(usuario::puedeVisitar)
                .filter(oferta -> !usuario.comproLaOferta(oferta))
                .sorted(Comparator.comparing(usuario::esDelTipoQueLeGusta)
                        .thenComparing(Ofertable::esPromocion)
                        .thenComparing(Ofertable::getCosto)
                        .thenComparing(Ofertable::getTiempo).reversed())
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public void ofrecerA(Usuario usuario) throws IOException {
        var ofertasParaUsuario = ofertasOrdenadasPara(usuario, ofertables.stream());
        this.vendedor.iniciarVenta(usuario);

        while (!ofertasParaUsuario.isEmpty() && usuario.getPresupuestoActual() > 0 && usuario.getTiempoDisponible() > 0) {
            Ofertable ofertableSugerida = ofertasParaUsuario.poll();
            if (this.vendedor.ofrecer(ofertableSugerida)) {
                usuario.comprarAtraccion(ofertableSugerida);
                ofertableSugerida.serComprada();
                vendedor.continuarVenta(usuario);
            }
            ofertasParaUsuario = ofertasOrdenadasPara(usuario, ofertasParaUsuario.stream());
        }
        vendedor.mostrarItinerario();
        vendedor.generarTicket(usuario);
    }
}