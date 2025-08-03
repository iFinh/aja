package controllers.UserControllers;

import views.Usuario.RegistroView;
import models.UserModel;
import models.Usuario;
import controllers.AppController;
import models.Sesion;

public class RegistroController {
    private RegistroView view;
    private UserModel model;

    public RegistroController(RegistroView view, UserModel model) {
        this.view = view;
        this.model = model;

        this.view.setRegistroListener(_ -> onRegister());
        this.view.setVolverLoginListener(_ -> onVolverLogin());
        this.view.setVolverMainListener(_ -> onVolverMain());

        this.view.setVisible(true);
    }

    public RegistroController() {
        this(new RegistroView(), new UserModel());
    }

    public void onRegister() {
        String credencial = view.getCredencial();
        String pass = view.getContrasena();
        String confirm = view.getConfirmacion();

        if (credencial.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            view.mostrarError("Todos los campos son obligatorios");
            return;
        }

        if (!model.esCredencialValida(credencial)) {
            view.mostrarError("Cédula inválida.");
            return;
        }

        if (!model.credencialExiste(credencial)) {
            view.mostrarError("La cédula no está autorizada.");
            return;
        }
        

        if (model.usuarioYaRegistrado(credencial)) {
            view.mostrarError("La cédula ya está registrada.");
            return;
        }
        
        if (!model.esClaveValida(pass)) {
            view.mostrarError("Contraseña debe ser de al menos 6 caracteres.");
            return;
        }

        if (!pass.equals(confirm)) {
            view.mostrarError("Las contraseñas no coinciden.");
            return;
        }

        if (model.guardarUsuario(credencial, pass, 0.0)) {
            Usuario usuario = model.obtenerUsuario(credencial);
            Sesion.iniciarSesion(usuario);
            view.mostrarMensaje("¡Registro exitoso! Bienvenido: " + credencial);
            new DashboardUserController();
            view.dispose();
        } else {
            view.mostrarError("Error al guardar el usuario.");
        }
    }

    public void onVolverLogin() {
        view.dispose();
        new LoginController();
    }

    public void onVolverMain() {
        view.dispose();
        new AppController();
    }
}
