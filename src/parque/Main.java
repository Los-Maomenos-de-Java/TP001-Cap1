package parque;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Usuario> usuarios = ManejadorDeArchivos.leerUsuarios();

        Boleteria boleteria = new Boleteria();

        for (Usuario u : usuarios) {
            boleteria.ofrecerA(u);
        }
    }
}