package controllers;
import controllers.AdminControllers.LoginAdminController;
import controllers.UserControllers.LoginController;
import models.AdminModel;
import views.App.AppView;
import javax.swing.*;

public class AppController {

    private AppView view;
    private AdminModel model;

    public AppController() {
        this.view = new AppView();
        this.model = new AdminModel();
        setupControllers();
        view.setVisible(true);
    }

    private void setupControllers() {
        view.getBtnAdmin().addActionListener(_ -> handleAdminAccess());
        view.getBtnUser().addActionListener(_ -> handleUserAccess());
    }

    private void handleAdminAccess() {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
            view.getFrame(),
            passwordField,
            "Ingrese la contraseña de administrador",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            if (model.validateAdminPassword(new String(passwordField.getPassword()))) {
                view.getFrame().dispose();
                new LoginAdminController(); 
            } else {
                JOptionPane.showMessageDialog(
                    view.getFrame(),
                    "Contraseña incorrecta",
                    "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void handleUserAccess() {
        view.getFrame().dispose();
        new LoginController(); 
    }
}

