package parque;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Boleteria {
    private List<Oferta> ofertas = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    public void agregarOferta(Oferta oferta) {
        this.ofertas.add(oferta);
    }

    public List<Oferta> ofertasFiltradasYOrdenadas(Usuario usuario) {
        return ofertas
                .stream()
                .filter(Oferta::tieneCupo)
                .filter(usuario::puedeVisitar)
                .filter(oferta -> !usuario.comproLaAtraccion(oferta))
                .sorted(Comparator.comparing(usuario::esDelTipoQueLeGusta)
                        .thenComparing(Oferta::esPromocion)
                        .thenComparing(Oferta::getCosto)
                        .thenComparing(Oferta::getTiempo).reversed())
                .collect(Collectors.toList());
    }
}