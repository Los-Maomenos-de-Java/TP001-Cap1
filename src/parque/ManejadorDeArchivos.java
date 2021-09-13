package parque;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManejadorDeArchivos {

    public static List<Ofertable> leerAtracciones(String nombreArchivo) {
        File archivo;
        BufferedReader br;
        FileReader fr = null;

        List<Ofertable> atracciones = new ArrayList<>();

        try {
            archivo = new File(nombreArchivo);
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

    public static List<Usuario> leerUsuarios(String nombreArchivo) {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            archivo = new File(nombreArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosUsuario = linea.split(",");

                String nombre = datosUsuario[0];
                double presupuesto = Double.parseDouble(datosUsuario[1]);
                double tiempoDisponible = Double.parseDouble(datosUsuario[2]);
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

    public static List<Ofertable> leerPromociones(String nombreArchivo) {
        File archivo;
        FileReader fr = null;
        BufferedReader br;

        List<Ofertable> promociones = new ArrayList<>();

        try {
            archivo = new File(nombreArchivo);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosPromocion = linea.split(",");

                String nombre = datosPromocion[0];
                String tipoPromocion = datosPromocion[1];
                String[] atraccionesString = datosPromocion[2].split("-");
                Atraccion[] atracciones = new Atraccion[atraccionesString.length];

                for (int i = 0; i < atraccionesString.length; i++) {
                    atracciones[i] = Boleteria.obtenerAtraccionPorNombre(atraccionesString[i]);
                }

                double descuentoAbsoluto;
                double descuentoPorcentual;
                String[] atraccionesGratisString;

                if (tipoPromocion.equals("PromocionAbsoluta")) {
                    descuentoAbsoluto = Double.parseDouble(datosPromocion[3]);
                    Promocion promocionAAgregar = new PromocionAbsoluta(nombre, descuentoAbsoluto);

                    for (Atraccion atraccion : atracciones) {
                        promocionAAgregar.agregarAtraccion(atraccion);
                    }
                    promociones.add(promocionAAgregar);
                }

                if (tipoPromocion.equals("PromocionAxB")) {
                    atraccionesGratisString = datosPromocion[3].split("-");
                    Promocion promocionAAgregar = new PromocionAxB(nombre, atraccionesGratisString);
                    for (Atraccion atraccion : atracciones) {
                        promocionAAgregar.agregarAtraccion(atraccion);
                    }
                    promociones.add(promocionAAgregar);
                }
                if (tipoPromocion.equals("PromocionPorcentual")) {
                    descuentoPorcentual = Double.parseDouble(datosPromocion[3]);
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

    public static void generarTicket(Usuario usuario, List<Ofertable> ofertasCompradas) throws IOException {
        File nuevoTicket = new File("archivos/salida/" + usuario.getNombre() + ".txt");

        PrintWriter salida = new PrintWriter(new FileWriter("archivos/salida/" + usuario.getNombre() + ".txt"));

        nuevoTicket.createNewFile();

        var fecha = new Date();
        var fechaFormateada = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss").format(fecha);

        salida.println("\t\t\t" + "\uD83D\uDCC5 " + fechaFormateada);

        salida.println("\n Datos del usuario: ");
        salida.println("- Nombre: " + usuario.getNombre());
        salida.println("- Presupuesto inicial: " + String.format("%.2f", usuario.getPresupuestoInicial()));
        salida.println("- Tiempo disponible: " + String.format("%.2f", usuario.getTiempoInicial()) + "\n");

        salida.println("------------------------------------------------------------------------------");
        salida.println("\t\t\tRESUMEN DE COMPRA DE " + usuario.getNombre().toUpperCase() + ":");
        salida.println("------------------------------------------------------------------------------");

        salida.printf("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n", "        Atracciones", "   Costo", "  Tiempo", " Tipo de Atracción");
        salida.printf("------------------------------------------------------------------------------\n");

        ofertasCompradas
                .stream()
                .filter(Ofertable::esPromocion)
                .forEach(promocion -> {
                    salida.printf("|%-29.29s |%-10.10s |%-9.9s |%-20.20s|%n",
                            "- " + promocion.getNombre(),
                            "  $" + String.format("%.2f", promocion.getCosto()),
                            "  ⏱ " + String.format("%.2f", promocion.getTiempo()),
                            "     " + promocion.getTipo().toString().charAt(0)
                                    + promocion.getTipo().toString().substring(1).toLowerCase());
                    promocion.getAtracciones().forEach(atraccion -> salida.printf("%-23.23s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-" + atraccion.getNombre()
                            , "  $" + String.format("%.2f", atraccion.getCosto()), "", ""));
                    salida.printf("%-23.23s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "\t-Descuento",
                            "  $" + String.format("%.2f", (promocion.getCosto() - promocion
                                    .getAtracciones()
                                    .stream()
                                    .mapToDouble(Atraccion::getCosto)
                                    .sum())),
                            "", "");
                    salida.printf("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                });

        ofertasCompradas
                .stream()
                .filter(oferta -> !oferta.esPromocion())
                .forEach(atraccion -> {
                    salida.printf("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n",
                            "- " + atraccion.getNombre(),
                            "  $" + String.format("%.2f", atraccion.getCosto()),
                            "  ⏱ " + String.format("%.2f", atraccion.getTiempo()),
                            "     " + atraccion.getTipo().toString().charAt(0)
                                    + atraccion.getTipo().toString().substring(1).toLowerCase());
                    salida.printf("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
                });

        var costoTotal = ofertasCompradas.stream().mapToDouble(Ofertable::getCosto).sum();
        var tiempoTotal = ofertasCompradas.stream().mapToDouble(Ofertable::getTiempo).sum();

        salida.printf("|%-29.29s |%-10.10s |%-10.10s |%-20.20s|%n", "- TOTAL", "  $" + String.format("%.2f", costoTotal), "  ⏱ " + String.format("%.2f", tiempoTotal), "");

        salida.println("------------------------------------------------------------------------------");

        salida.println("\n-Presupuesto final: " + String.format("%.2f", usuario.getPresupuestoActual()));
        salida.println("-Tiempo restante: " + String.format("%.2f", usuario.getTiempoDisponible()));

        salida.close();
    }
}