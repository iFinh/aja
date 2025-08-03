package controllers.UserControllers;

import models.UserModel;
import controllers.AppController;
import models.Sesion;
import models.Usuario;
import views.Usuario.LoginView;
import views.Usuario.VerificacionIdentidadLoginView;

import javax.swing.*;
import java.io.File;

public class LoginController {
    private final UserModel userModel;
    private final LoginView view;

    public LoginController(UserModel userModel, LoginView view) {
        this.userModel = userModel;
        this.view = view;
        setupListeners();
    }

    public LoginController() {
        this(new UserModel(), new LoginView());
    }

    private void setupListeners() {
        view.setLoginListener(_ -> intentarLogin());
        view.setVolverMainListener(_ -> volverAlMenuPrincipal());
        view.setVolverRegistroListener(_ -> irARegistro());
    }

    public void intentarLogin() {
        String credencial = view.getCredencial();
        String contrasena = view.getContrasena();

        if (!userModel.esCredencialValida(credencial)) {
            view.mostrarError("La cédula no es válida.");
            return;
        }

        if (!userModel.usuarioYaRegistrado(credencial)) {
            view.mostrarError("El usuario no está registrado.");
            return;
        }

        if (!userModel.esClaveValida(contrasena)) {
            view.mostrarError("Contraseña debe ser de al menos 6 caracteres.");
            return;
        }

        if (!userModel.autenticarUsuario(credencial, contrasena)) {
            view.mostrarError("Contraseña incorrecta.");
            return;
        }

        // Usuario autenticado correctamente
        Usuario usuario = userModel.obtenerUsuario(credencial);
        Sesion.iniciarSesion(usuario);

        // Abrir ventana de verificación de identidad
        VerificacionIdentidadLoginView verificacionView = new VerificacionIdentidadLoginView();
        verificacionView.setVisible(true);

        verificacionView.addVerificarListener(e -> {
            if (verificarIdentidadConArchivo(usuario, verificacionView)) {
                verificacionView.dispose();
                view.dispose(); // Se cierra login solo si verificación es exitosa
                view.mostrarMensaje("Inicio de sesión exitoso.");
                new DashboardUserController();
            }
        });
    }

    private boolean verificarIdentidadConArchivo(Usuario usuario, JFrame parentView) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona tu imagen de identidad");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(parentView);
        if (result != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(parentView, "Verificación cancelada.", "Verificación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        File archivoSeleccionado = fileChooser.getSelectedFile();
        String nombreArchivoSeleccionado = archivoSeleccionado.getName().trim();
        String nombreArchivoUsuario = usuario.getFoto() != null ? usuario.getFoto().trim() : "";

        if (!nombreArchivoSeleccionado.equals(nombreArchivoUsuario)) {
            JOptionPane.showMessageDialog(parentView, "Verificación fallida. Imagen no coincide con la registrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void irARegistro() {
        view.dispose();
        new RegistroController();
    }

    public void volverAlMenuPrincipal() {
        view.dispose();
        new AppController();
    }
}
