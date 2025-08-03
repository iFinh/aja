package controllers.UserControllers;

import models.Usuario;
import views.Usuario.VerificacionIdentidadLoginView;

import javax.swing.*;
import java.io.File;

public class VerificacionIdentidadLoginController {
    private final VerificacionIdentidadLoginView view;
    private final Usuario usuario;
    private final Runnable onVerificacionExitosa;

    public VerificacionIdentidadLoginController(Usuario usuario, Runnable onVerificacionExitosa) {
        this.usuario = usuario;
        this.view = new VerificacionIdentidadLoginView();
        this.onVerificacionExitosa = onVerificacionExitosa;
        setupListener();
    }

    private void setupListener() {
        view.addVerificarListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona tu imagen de identidad");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

            int result = fileChooser.showOpenDialog(view);
            if (result != JFileChooser.APPROVE_OPTION) {
                view.mostrarError("Verificación cancelada.");
                return;
            }

            File archivoSeleccionado = fileChooser.getSelectedFile();
            String nombreSeleccionado = archivoSeleccionado.getName().trim();
            String nombreUsuario = usuario.getFoto() != null ? usuario.getFoto().trim() : "";

            if (!nombreSeleccionado.equals(nombreUsuario)) {
                view.mostrarError("Imagen no coincide con la registrada.");
                return;
            }

            view.mostrarMensaje("Verificación exitosa.");
            view.dispose();
            onVerificacionExitosa.run(); // ← Aquí se lanza el Dashboard
        });
    }
}

