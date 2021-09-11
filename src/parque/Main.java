package parque;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
    	String archivoUsuarios = "archivos/UsuariosSimpsons.txt";
    	List<Usuario> usuarios = ManejadorDeArchivos.leerUsuarios(archivoUsuarios);

        Boleteria boleteria = new Boleteria();

        for(Usuario u:usuarios) {
            boleteria.ofrecerA(u);
        }

        System.out.println(DibujadorDeHomero.saludo());
    }
}