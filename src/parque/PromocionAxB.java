package parque;

import java.util.ArrayList;
import java.util.List;

public class PromocionAxB extends Promocion {
    private List<Atraccion> atraccionesGratis = new ArrayList<>();

    @Override
    public double getCosto() {
        var costoAtraccionesGratis = atraccionesGratis
                .stream()
                .mapToDouble(Atraccion::getCosto)
                .sum();

        return costoTotalAtracciones() - costoAtraccionesGratis;
    }
}