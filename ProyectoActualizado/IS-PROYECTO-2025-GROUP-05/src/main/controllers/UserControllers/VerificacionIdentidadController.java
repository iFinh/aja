package controllers.UserControllers;

import models.Usuario;
import views.Usuario.VerificacionIdentidadView;
// import models.Sesion;

import javax.swing.*;

import controllers.AdminControllers.DashboardAdminController;

import java.io.*;
import java.util.Locale;

public class VerificacionIdentidadController {
    private final VerificacionIdentidadView view;
    private final JFrame parentView; // Nueva referencia a la vista anterior (MenuView)
    private File archivoSeleccionado;

    public VerificacionIdentidadController(JFrame parent) {
        this.parentView = parent;
        this.view = new VerificacionIdentidadView(parent);

        view.addSeleccionarArchivoListener(_ -> seleccionarArchivo());
        view.addVerificarListener(_ -> verificar());

        view.setVisible(true);
    }

private void seleccionarArchivo() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Selecciona una imagen");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

    int result = fileChooser.showOpenDialog(view);
    if (result == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        try {
            // Intenta leer la imagen con ImageIO
            if (javax.imageio.ImageIO.read(archivo) == null) {
                view.mostrarError("El archivo seleccionado no es una imagen válida.");
                return;
            }
            archivoSeleccionado = archivo;
        } catch (IOException e) {
            view.mostrarError("Error al verificar la imagen.");
        }
    }
}


private void verificar() {
    if (archivoSeleccionado == null) {
        view.mostrarError("Debe seleccionar un archivo.");
        return;
    }

    String archivoSubido = archivoSeleccionado.getName();

    try (BufferedReader reader = new BufferedReader(new FileReader("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/usuarios.txt"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 6) {
                String cedula = partes[0].trim();
                double saldo = Double.parseDouble(partes[2].trim());
                String nombreApellido = partes[3].trim();
                String rol = partes[4].trim().toLowerCase();
                String archivoGuardado = partes[5].trim();
                boolean desayuno = Boolean.parseBoolean(partes[6].trim());
                boolean almuerzo = Boolean.parseBoolean(partes[7].trim());

                if (archivoSubido.equals(archivoGuardado)) {
                    double cbb = obtenerCBBDesdeArchivo();
                    double costo = switch (rol) {
                        case "estudiante" -> cbb * 0.25;
                        case "profesor" -> cbb * 0.80;
                        default -> cbb;
                    };
                    
                    Usuario usuario = new Usuario(cedula, saldo, nombreApellido, rol, archivoGuardado, desayuno, almuerzo);

                    if (saldo >= costo && usuario.tieneDesayuno()) {
                        double nuevoSaldo = saldo - costo;
                        actualizarSaldoEnArchivo(cedula, nuevoSaldo);
                        view.mostrarMensaje("Acceso exitoso. Se descontaron " + String.format(Locale.US, "%.2f", costo) + "Bs.");
                    } else {
                        view.mostrarError("Saldo insuficiente para completar la operación.");
                    }

                    return;
                }
            }
        }
        view.mostrarError("Usuario no encontrado en la base de datos.");
    } catch (Exception e) {
        e.printStackTrace();
        view.mostrarError("Error al verificar identidad.");
    }
}

    private double obtenerCBBDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/costos.txt"))) {
            String linea, ultimaLinea = "";
            while ((linea = br.readLine()) != null) {
                ultimaLinea = linea;
            }
            if (!ultimaLinea.isEmpty()) {
                String[] partes = ultimaLinea.split(",");
                return Double.parseDouble(partes[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

private void actualizarSaldoEnArchivo(String cedula, double nuevoSaldo) {
    File archivo = new File("ProyectoActualizado/IS-PROYECTO-2025-GROUP-05/src/main/data/usuarios.txt");
    StringBuilder contenidoActualizado = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes[0].equals(cedula)) {
                partes[2] = String.format(Locale.US, "%.2f", nuevoSaldo);
                linea = String.join(",", partes);
            }
            contenidoActualizado.append(linea).append(System.lineSeparator());
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Escribimos todo de nuevo sobre el mismo archivo
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
        bw.write(contenidoActualizado.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
