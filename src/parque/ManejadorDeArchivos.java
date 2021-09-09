package parque;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ManejadorDeArchivos {

    public static List<Oferta> leerAtracciones() {
        File archivo;
        BufferedReader br;
        FileReader fr = null;

        List<Oferta> atracciones = new ArrayList<>();

        try {
            archivo = new File("/Volumes/HP P500/Programación/Java/ParqueDiversiones/TP001-Cap1/resources/atracciones.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosAtraccion = linea.split(",");

                String nombre = datosAtraccion[0];
                double costoVisita = Double.parseDouble(datosAtraccion[1]);
                double tiempoPromedio = Double.parseDouble(datosAtraccion[2]);
                int cupo = Integer.parseInt(datosAtraccion[3]);
                TipoDeAtraccion tipoAtraccion = TipoDeAtraccion.valueOf(datosAtraccion[4]);

                atracciones.add(new Atraccion(nombre, costoVisita, tiempoPromedio, tipoAtraccion, cupo));
                linea = br.readLine();
            }
            return atracciones;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return atracciones;
    }

    public static List<Usuario> leerUsuarios() {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            archivo = new File("/Volumes/HP P500/Programación/Java/ParqueDiversiones/TP001-Cap1/resources/usuarios.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosUsuario = linea.split(",");

                String nombre = datosUsuario[0];
                double presupuesto = Integer.parseInt(datosUsuario[1]);
                double tiempoDisponible = Integer.parseInt(datosUsuario[2]);
                TipoDeAtraccion tipoDeAtraccionPreferida = TipoDeAtraccion.valueOf(datosUsuario[3]);

                usuarios.add(new Usuario(nombre, presupuesto, tiempoDisponible, tipoDeAtraccionPreferida));
                linea = br.readLine();
            }
            return usuarios;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return usuarios;
    }

    public static List<Oferta> leerPromociones() {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        List<Oferta> promociones = new ArrayList<>();

        try {
            archivo = new File("/Volumes/HP P500/Programación/Java/ParqueDiversiones/TP001-Cap1/resources/promociones.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosPromocion = linea.split(",");

                String nombre = datosPromocion[0];
                String tipoPromocion = datosPromocion[1];
                String[] atraccionesString = datosPromocion[2].split("-");
                Atraccion[] atracciones = new Atraccion[atraccionesString.length];
                String[] atraccionesGratisString;
                for (int i = 0; i < atraccionesString.length; i++) {
                    atracciones[i] = Boleteria.obtenerAtraccionPorNombre(atraccionesString[i]);
                }
                double descuentoAbsoluto;
                int descuentoPorcentual;

                if (tipoPromocion.equals("PromocionAbsoluta")) {
                    descuentoAbsoluto = Integer.parseInt(datosPromocion[3]);
                    Promocion promocionAAgregar = new PromocionAbsoluta(nombre, descuentoAbsoluto);
                    for (Atraccion atraccion : atracciones) {
                        promocionAAgregar.agregarAtraccion(atraccion);
                    }
                    promociones.add(promocionAAgregar);
                }

                if (tipoPromocion.equals("PromocionAxB")) {
                    atraccionesGratisString = datosPromocion[3].split("-");
                    Promocion promocionAAgregar = new PromocionAxB(nombre, atraccionesGratisString);
                    for (Atraccion atraccione : atracciones) {
                        promocionAAgregar.agregarAtraccion(atraccione);
                    }
                    promociones.add(promocionAAgregar);
                }
                if (tipoPromocion.equals("PromocionPorcentual")) {
                    descuentoPorcentual = Integer.parseInt(datosPromocion[3]);
                    Promocion promocionAAgregar = new PromocionPorcentual(nombre, descuentoPorcentual);
                    for (Atraccion atraccione : atracciones) {
                        promocionAAgregar.agregarAtraccion(atraccione);
                    }
                    promociones.add(promocionAAgregar);
                }
                linea = br.readLine();
            }
            return promociones;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return promociones;
    }

    public static void generarTicket(Usuario usuario, List<Oferta> ofertasCompradas) throws IOException {
        File nuevoTicket = new File("/Volumes/HP P500/Programación/Java/ParqueDiversiones" +
                "/TP001-Cap1/resources/" + usuario.getNombre() + ".txt");

        PrintWriter salida = new PrintWriter(new FileWriter("/Volumes/HP P500/Programación/Java/ParqueDiversiones" +
                "/TP001-Cap1/resources/" + usuario.getNombre() + ".txt"));

        nuevoTicket.createNewFile();

        var fecha = new Date();
        var fechaFormateada = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss").format(fecha);

        salida.println("\t\t" + "\uD83D\uDCC5 " + fechaFormateada);
        salida.println("\n\n\t\tRESUMEN DE COMPRA DE " + usuario.getNombre().toUpperCase() + ":");

        salida.println("---------------------------------------------------------------");

        salida.printf("%-30.30s |%-10.10s |%-10.10s|%n", "Atracciones", "Costo", "Tiempo");
        salida.printf("\n");

        ofertasCompradas
                .stream()
                .filter(Oferta::esPromocion)
                .forEach(promocion -> {
                    salida.printf("%-30.30s |%-10.10s |%-10.10s|%n",
                            "- " + promocion.getNombre(),
                            "$" + promocion.getCosto(),
                            "⏱ " + promocion.getTiempo());
                    promocion.getAtracciones().forEach(atraccion -> salida.printf("%-25.25s %-10.10s %-10.10s%n",
                            "\t-" + atraccion.getNombre()
                            , "$" + atraccion.getCosto(), ""));
                    salida.printf("%-25.25s %-10.10s %-10.10s%n",
                            "\t-Descuento",
                            "$" + (promocion.getCosto() - promocion.getAtracciones().stream().mapToDouble(Atraccion::getCosto).sum()),
                            "");
                    salida.printf("\n");
                });

        ofertasCompradas
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .forEach(atraccion -> salida.printf("%-30.30s |%-10.10s |%-10.10s|%n%n",
                        "- " + atraccion.getNombre(),
                        "$" + atraccion.getCosto(),
                        "⏱ " + atraccion.getTiempo()));

        salida.println("---------------------------------------------------------------");

        var costoTotal = ofertasCompradas.stream().mapToDouble(Oferta::getCosto).sum();
        var tiempoTotal = ofertasCompradas.stream().mapToDouble(Oferta::getTiempo).sum();

        salida.printf("%-30.30s |%-10.10s |%-10.10s|%n", "- TOTAL", "$" + costoTotal, "⏱ " + tiempoTotal);

        salida.println("---------------------------------------------------------------");

        salida.close();
    }
}