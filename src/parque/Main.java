package parque;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Usuario> usuarios = ManejadorDeArchivos.leerUsuarios();

        Boleteria boleteria = new Boleteria();

        for (Usuario u : usuarios) {
            boleteria.ofrecerA(u);
        }
    }
}