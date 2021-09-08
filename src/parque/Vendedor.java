package parque;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendedor {
    private List<Oferta> ofertasVendidas = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);

    public void iniciarVenta(Usuario usuario) {
        // saludar
        System.out.println("Bienvenido " + usuario.getNombre() + "!\n\nVeo que tienes: $" + usuario.getPresupuesto()
                + " y " + usuario.getTiempoDisponible() + " horas disponibles\n");
    }

    public void continuarVenta(Usuario usuario) {
        System.out.println("Ahora cuentas con: $" + usuario.getPresupuesto() + " y " + usuario.getTiempoDisponible()
                + " horas disponibles\n");
    }

    public boolean ofrecer(Oferta ofertaSugerida) {
        System.out.println(
                "-----------------------------------------------------------------------------------------------------");
        System.out.println("\nPuedo ofrecerte:\n");

        if (ofertaSugerida.esPromocion()) {
            System.out.println("PROMOCION: " + ofertaSugerida.getNombre());
            System.out.println("\tAtracciones incluidas:");
            String atracciones = "";
            for (Oferta atraccion : ofertaSugerida.getAtracciones()) {
                atracciones += "\t" + atraccion.getNombre() + "\n\t";
            }
            System.out.println(" \t" + atracciones);
        } else {
            System.out.println("ATRACCION: " + ofertaSugerida.getNombre());
        }

        System.out.println("\tCosto final: $" + ofertaSugerida.getCosto());
        System.out.println("\tTiempo total requerido: " + ofertaSugerida.getTiempo());

        System.out.println("¿Qué te parece?\n¿Quieres comprar esta oferta? ( s / n ): ");

        String respuesta = scan.nextLine().toLowerCase();

        while (!respuesta.equals("s") && !respuesta.equals("n")) {
            System.out.println("Respuesta inválida.\n¿Quieres comprar esta oferta? ( s / n )");
            respuesta = this.scan.nextLine().toLowerCase();
        }

        if (respuesta.equals("s")) {
            ofertasVendidas.add(ofertaSugerida);
            return true;
        }
        return false;
    }

    public List<Atraccion> getAtraccionesVendidas() {
        Itinerario vendido = new Itinerario(this.ofertasVendidas);
        return vendido.getAtracciones();
    }

    public void mostrarItinerario() {
        System.out.println(
                "-----------------------------------------------------------------------------------------------------");
        System.out.println("No tengo nada más para ofrecer.\n\nEste es tu itinerario:");
        Itinerario vendido = new Itinerario(this.ofertasVendidas);
        System.out.println(vendido);
        System.out.println(
                "-----------------------------------------------------------------------------------------------------");
        System.out.println("Presioná Enter para continuar");
        scan.nextLine();
        System.out.println(
                "-----------------------------------------------------------------------------------------------------");
    }

    // TODO Escritura itinerario
    public void generarTicket(Usuario usuario) {
        // usa el gestor de archivos para generar el Ticket
        this.ofertasVendidas.clear();
    }
}
