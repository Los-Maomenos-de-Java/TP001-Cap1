package parque;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromocionAxB extends Promocion {
    private List<String> atraccionesGratis;

    public PromocionAxB(String nombre, String[] atraccionesGratis) {
        super(nombre);
        this.atraccionesGratis = Arrays.stream(atraccionesGratis)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    @Override
    public double getCosto() {
        return atracciones
                .stream()
                .filter(atraccion -> !atraccionesGratis.contains(atraccion.getNombre().toLowerCase()))
                .mapToDouble(Atraccion::getCosto).sum();
    }
}