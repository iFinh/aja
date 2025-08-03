package controllers.UserControllers;

import models.Usuario;
import models.Sesion;
import views.Usuario.DashboardUserView;

import javax.swing.*;
import java.time.LocalDateTime;

public class DashboardUserController {
    private DashboardUserView view;  // ✅ Eliminado "final" para evitar error
    private final Usuario usuario;

    public DashboardUserController() {
        this.usuario = Sesion.getUsuarioActual();
        
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "No hay una sesión activa para el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            new LoginController(); // Redirige a Login si no hay sesión
            return;
        }

        this.view = new DashboardUserView(usuario);
        inicializarVista();
        agregarListeners();
        view.setVisible(true);
    }

    private void inicializarVista() {
        view.updateDate(LocalDateTime.now()); 
    }

    private void agregarListeners() {
        view.getConsultarMenuBtn().addActionListener(_ -> consultarMenu());
        view.getRecargarSaldoBtn().addActionListener(_ -> recargarSaldo());
        view.getCerrarSesionBtn().addActionListener(_ -> cerrarSesion());
    }

    private void consultarMenu() {
        view.dispose();
        new MenuController();
    }

    private void recargarSaldo() {
        new RecargaSaldoController(view);
    }

    private void cerrarSesion() {
        Sesion.cerrarSesion();
        view.dispose();
        new LoginController(); 
    }
}
