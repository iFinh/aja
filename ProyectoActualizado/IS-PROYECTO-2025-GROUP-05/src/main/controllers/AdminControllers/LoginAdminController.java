package controllers.AdminControllers;

import controllers.AppController;
import models.AdminModel;
import models.Sesion;
import models.Usuario;
import views.Admin.LoginAdminView;

public class LoginAdminController {
    private final LoginAdminView view;
    private final AdminModel adminModel;

    public LoginAdminController() {
        this.view = new LoginAdminView();
        this.adminModel = new AdminModel();
        setupListeners();
    }

    public void setupListeners() {
        view.setLoginListener(_ -> intentarLogin());
        view.setVolverMainListener(_ -> volverAlMenu());
        view.setVolverRegistroListener(_ -> irARegistro());
    }

   public void intentarLogin() {
        String credencial = view.getCredencial();
        String contrasena = view.getPassword();

        if (credencial.isEmpty() || contrasena.isEmpty()) {
            view.mostrarError("Todos los campos son obligatorios");
            return;
        }

        if (!adminModel.credencialExiste(credencial)) {
            view.mostrarError("El usuario no está registrado.");
            return;
        }
        
        if (!adminModel.esClaveValida(contrasena)) {
            view.mostrarError("Contraseña debe ser de al menos 6 caracteres.");
            return;
        }

        if (!adminModel.verificarCredenciales(credencial, contrasena)) {
            view.mostrarError("Contraseña incorrecta.");
            return;
        }

        Sesion.iniciarSesion(new Usuario(credencial));

        view.mostrarMensaje("Inicio de sesión exitoso.");
        view.dispose();
        new DashboardAdminController(); 
    }

    public void volverAlMenu() {
        view.dispose();
        new AppController(); 
    }

    public void irARegistro() {
        view.dispose();
        new RegistroAdminController();
    }
}