package parque;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromocionAxB extends Promocion {
    private List<Atraccion> atraccionesGratis;

    public PromocionAxB(String nombre, String[] atraccionesGratis) {
        super(nombre);
        var listaAtraccionesGratis = Arrays.asList(atraccionesGratis);
        this.atraccionesGratis = this.atracciones
                .stream()
                .filter(atraccion -> listaAtraccionesGratis.contains(atraccion.getNombre()))
                .collect(Collectors.toList());
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