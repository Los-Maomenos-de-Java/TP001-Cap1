package parque;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromocionAxB extends Promocion {
    private List<Atraccion> atraccionesGratis = new ArrayList<>();

    public PromocionAxB(String nombre, String[] atraccionesGratis) {
        super(nombre);
        for (String atraccionString : atraccionesGratis) {
            this.atraccionesGratis.add(Boleteria.obtenerAtraccionPorNombre(atraccionString));
        }
    }

    @Override
    public double getCosto() {
        return atracciones
                .stream()
                .filter(atraccion -> !atraccionesGratis.contains(atraccion.getNombre().toLowerCase()))
                .mapToDouble(Atraccion::getCosto).sum();
    }
}