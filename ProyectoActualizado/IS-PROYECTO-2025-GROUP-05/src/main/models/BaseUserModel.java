package models;

import java.io.*;

public abstract class BaseUserModel {

    public void ensureFileExists(File file) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("Archivo creado en: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    public boolean credencialExiste(String credencial, File archivoCredenciales) {
        if (!archivoCredenciales.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoCredenciales))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length > 0 && partes[0].trim().equals(credencial)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error verificando credencial: " + e.getMessage());
        }
        return false;
    }

    public boolean autenticar(String credencial, String contrasena, File archivo) {
        if (!archivo.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2 &&
                    partes[0].trim().equals(credencial) &&
                    partes[1].trim().equals(contrasena)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error autenticando: " + e.getMessage());
        }
        return false;
    }

    public boolean guardarCredencial(String credencial, String contrasena, File archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(credencial + "," + contrasena);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar credencial: " + e.getMessage());
            return false;
        }
    }

    public boolean esClaveValida(String clave) {
        if (clave == null) return false;
        int longitud = clave.length();
        return longitud >= 6;
    }
}