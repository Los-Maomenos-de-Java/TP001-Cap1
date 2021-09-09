package parque;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendedor {
    private List<Ofertable> ofertasVendidas = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);

    public void iniciarVenta(Usuario usuario) {
        System.out.println("\nBienvenido " + usuario.getNombre() + "!\n\nVeo que tienes: $" + usuario.getPresupuestoActual()
                + " y " + usuario.getTiempoDisponible() + " horas disponibles\n");
    }

    public void continuarVenta(Usuario usuario) {
        System.out.println("Ahora cuentas con: $" + usuario.getPresupuestoActual() + " y " + usuario.getTiempoDisponible()
                + " horas disponibles\n");
    }

    public boolean ofrecer(Ofertable ofertableSugerida) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("\nPuedo ofrecerte:\n");

        if (ofertableSugerida.esPromocion()) {
            System.out.println("PROMOCION: " + ofertableSugerida.getNombre());
            System.out.println("\tAtracciones incluidas:");
            String atracciones = "";
            for (Ofertable atraccion : ofertableSugerida.getAtracciones()) {
                atracciones += "\t" + atraccion.getNombre() + "\n\t";
            }
            System.out.println(" \t" + atracciones);
        } else {
            System.out.println("ATRACCION: " + ofertableSugerida.getNombre());
        }

        System.out.println("\tCosto final: $" + ofertableSugerida.getCosto());
        System.out.println("\tTiempo total requerido: " + ofertableSugerida.getTiempo());

        System.out.println("¿Qué te parece?\n¿Quieres comprar esta oferta? ( s / n ): ");

        String respuesta = scan.nextLine().toLowerCase();

        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.println("Respuesta inválida.\n¿Quieres comprar esta oferta? ( s / n )");
            respuesta = this.scan.nextLine().toLowerCase();
        }

        if (respuesta.equals("s")) {
            ofertasVendidas.add(ofertableSugerida);
            return true;
        }
        return false;
    }

    public void mostrarItinerario() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("No tengo nada más para ofrecer.\n\nEste es tu itinerario:");
        Itinerario vendido = new Itinerario(this.ofertasVendidas);
        System.out.println(vendido);
        System.out.println("Presioná Enter para continuar");
        scan.nextLine();
        System.out.println("-----------------------------------------------------------------------------");
    }

    public void generarTicket(Usuario usuario) throws IOException {
        ManejadorDeArchivos.generarTicket(usuario, ofertasVendidas);
        this.ofertasVendidas.clear();
    }
}
