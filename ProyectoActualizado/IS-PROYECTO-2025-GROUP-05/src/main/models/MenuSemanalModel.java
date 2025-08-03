package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MenuSemanalModel {

    private static final String FILE_PATH = "ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/MenuSemanal.txt";

    public boolean guardarOModificarMenu(String dia, String tipoComida, String plato, String c1, String c2, String bebida, String postre, double costo, boolean sobrescribir) {
        List<String> lineasActuales = new ArrayList<>();
        boolean encontrado = false;
        String nuevaLinea = dia + "," + tipoComida + "," + plato + "," + c1 + "," + c2 + "," + bebida + "," + postre + "," + costo;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String diaArchivo = partes[0].trim();
                    String tipoArchivo = partes[1].trim();

                    if (diaArchivo.equalsIgnoreCase(dia) && tipoArchivo.equalsIgnoreCase(tipoComida)) {
                        encontrado = true;
                        if (sobrescribir) {
                            lineasActuales.add(nuevaLinea);
                        } else {
                            lineasActuales.add(linea);
                        }
                    } else {
                        lineasActuales.add(linea);
                    }
                } else {
                    lineasActuales.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }

        if (!encontrado) {
            lineasActuales.add(nuevaLinea);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String l : lineasActuales) {
                bw.write(l);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error escribiendo el archivo: " + e.getMessage());
            return false;
        }
    }

    public boolean existeMenuParaDiaYTipo(String dia, String tipoComida) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String diaArchivo = partes[0].trim();
                    String tipoArchivo = partes[1].trim();
                    if (diaArchivo.equalsIgnoreCase(dia) && tipoArchivo.equalsIgnoreCase(tipoComida)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar existencia del menú: " + e.getMessage());
        }
        return false;
    }

    public Map<String, String> obtenerMenu(String dia, String tipoComida) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 7 && partes[0].equalsIgnoreCase(dia) && partes[1].equalsIgnoreCase(tipoComida)) {
                    Map<String, String> menu = new HashMap<>();
                    menu.put("plato", partes[2]);
                    menu.put("contorno1", partes[3]);
                    menu.put("contorno2", partes[4]);
                    menu.put("bebida", partes[5]);
                    menu.put("postre", partes[6]);
                    return menu;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}