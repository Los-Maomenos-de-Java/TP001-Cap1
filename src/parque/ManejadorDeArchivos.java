package parque;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejadorDeArchivos {

    public static List<Oferta> leerAtracciones() {
        File archivo;
        BufferedReader br;
        FileReader fr = null;

        List<Oferta> atracciones = new ArrayList<>();

        try {
            archivo = new File("Archivos/Atracciones.txt");
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
            archivo = new File("Archivos/Usuarios.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosUsuario = linea.split(",");

                String nombre = datosUsuario[0];
                double presupuesto = Integer.parseInt(datosUsuario[1]);
                double tiempoDisponible = Integer.parseInt(datosUsuario[2]);
                TipoDeAtraccion tipoDeAtraccionPreferida = TipoDeAtraccion.valueOf(datosUsuario[4]);

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
            archivo = new File("Archivos/Promociones.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea = br.readLine();
            while (linea != null) {
                String[] datosPromocion = linea.split(",");

                String nombre = datosPromocion[0];
                String tipoPromocion = datosPromocion[1];
                String[] atraccionesString = datosPromocion[2].split("-");
                Atraccion[] atracciones = new Atraccion[atraccionesString.length];
                double descuentoAbsoluto;
                String[] atraccionesGratisString;
                for (int i = 0; i < atraccionesString.length; i++) {
                    atracciones[i] = Boleteria.obtenerAtraccionPorNombre(atraccionesString[i]);
                }
                int descuentoPorcentual = 0;

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
}
