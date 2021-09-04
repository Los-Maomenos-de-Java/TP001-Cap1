package parque;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Boleteria boleteria = new Boleteria();
        Usuario u = new Usuario("Pepe" ,50, 4, TipoDeAtraccion.AVENTURA);

        Atraccion a = new Atraccion("1", 40, 2, TipoDeAtraccion.AVENTURA, 4);
        Atraccion b = new Atraccion("2", 15, 1, TipoDeAtraccion.PAISAJE, 10);
        Atraccion c = new Atraccion("3", 2, 1, TipoDeAtraccion.DEGUSTACION, 0);
        Atraccion d = new Atraccion("4", 2, 3, TipoDeAtraccion.DEGUSTACION, 2);
        Atraccion e = new Atraccion("5", 30, 4, TipoDeAtraccion.PAISAJE, 4);
        Atraccion f = new Atraccion("6", 4, 1, TipoDeAtraccion.AVENTURA, 20);
        Atraccion g = new Atraccion("7", 35, 15, TipoDeAtraccion.PAISAJE, 5);

        Promocion h = new PromocionPorcentual(50);

        h.agregarAtraccion(a);
        h.agregarAtraccion(f);

        boleteria.agregarOferta(a);
        boleteria.agregarOferta(b);
        boleteria.agregarOferta(c);
        boleteria.agregarOferta(d);
        boleteria.agregarOferta(e);
        boleteria.agregarOferta(f);
        boleteria.agregarOferta(g);
        boleteria.agregarOferta(h);

        var ofertaAPepe = boleteria.ofertasFiltradasYOrdenadas(u);

        ofertaAPepe.forEach(System.out::println);
    }
}