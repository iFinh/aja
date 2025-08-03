package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserModel extends BaseUserModel {

    private final File usuariosFile;
    private final File credencialesFile;


    public UserModel() {
        usuariosFile = new File("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/usuarios.txt");
        credencialesFile = new File("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/credenciales.txt");

        ensureFileExists(usuariosFile);
        ensureFileExists(credencialesFile);
    }

    public boolean esCredencialValida(String credencialStr) {
        try {
            int credencial = Integer.parseInt(credencialStr);
            return credencial >= 500_000 && credencial <= 32_000_000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean credencialExiste(String credencial) {
        return super.credencialExiste(credencial, credencialesFile);
    }

    public boolean usuarioYaRegistrado(String credencial) {
        if (!usuariosFile.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 1 && partes[0].trim().equals(credencial)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean guardarUsuario(String credencial, String contrasena, double saldoInicial) {
        String[] datos = obtenerDatosCredencial(credencial);
        if (datos == null) return false;

        String nombreApellido = datos[0];
        String rol = datos[1];
        String foto = datos[2];

        // Por defecto, ambos booleanos en false
        boolean desayuno = false;
        boolean almuerzo = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usuariosFile, true))) {
            writer.write(String.join(",", credencial, contrasena, String.valueOf(saldoInicial), nombreApellido, rol, foto,
                    String.valueOf(desayuno), String.valueOf(almuerzo)));
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarSaldo(String cedula, double nuevoSaldo) {
        List<String> lineasActualizadas = new ArrayList<>();
        boolean actualizado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosFile))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 6); // Protege el nombre completo con split limitado

                if (partes.length < 6) {
                    lineasActualizadas.add(linea); // Línea mal formada, conservar igual
                    continue;
                }

            if (partes[0].trim().equals(cedula.trim()) && partes.length >= 8) {
                partes[2] = String.format(Locale.US, "%.2f", nuevoSaldo);
                actualizado = true;
                lineasActualizadas.add(String.join(",", partes));
            } else {
                    lineasActualizadas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Reescribir el archivo con las líneas actualizadas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usuariosFile, false))) {
            for (String lineaActualizada : lineasActualizadas) {
                writer.write(lineaActualizada);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return actualizado;
    }

    public String[] obtenerDatosCredencial(String credencial) {
            try (BufferedReader reader = new BufferedReader(new FileReader(credencialesFile))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 4 && partes[0].trim().equals(credencial)) {
                        return new String[]{partes[1].trim(), partes[2].trim(), partes[3].trim()}; // nombreApellido, rol, foto
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    public boolean autenticarUsuario(String credencial, String contrasena) {
            return super.autenticar(credencial, contrasena, usuariosFile);
    }

    public double obtenerSaldo(String credencial) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3 && partes[0].trim().equals(credencial)) {
                    return Double.parseDouble(partes[2].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Usuario obtenerUsuario(String credencial) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 8 && partes[0].trim().equals(credencial)) {
                    return new Usuario(
                        partes[0].trim(),
                        Double.parseDouble(partes[2].trim()),
                        partes[3].trim(),
                        partes[4].trim(),
                        partes[5].trim(),
                        Boolean.parseBoolean(partes[6].trim()),
                        Boolean.parseBoolean(partes[7].trim())
                    );
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}