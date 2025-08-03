package views.Usuario;

import javax.swing.*;
import views.Components.BotonAzul;
import views.Components.RoundedPanel;
import views.Components.UIFactory;

import java.awt.*;
import java.awt.event.*;

public class RegistroView extends JFrame {
    private JTextField credencialField;
    private JPasswordField passwordField;
    private JPasswordField confirmarPasswordField;
    public JButton registerButton;
    public JButton volverLoginButton;
    private JLabel titleLabel, credencialLabel, passwordLabel, confirmarLabel, volverLoginLabel;
    private RoundedPanel formPanel;
    public BotonAzul volverMainButton;

    public RegistroView() {
        setTitle("Registro de cuenta");
        setSize(850, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(36, 136, 242));
        getContentPane().setLayout(null);

        formPanel = new RoundedPanel(20);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        getContentPane().add(formPanel);

        titleLabel = new JLabel("Registro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        formPanel.add(titleLabel);

        credencialLabel = new JLabel("Credencial:");
        credencialLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(credencialLabel);

        credencialField = UIFactory.crearCampoTextoRedondeado(15);
        formPanel.add(credencialField);

        passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(passwordLabel);

        passwordField = UIFactory.crearCampoPasswordRedondeado(15);
        formPanel.add(passwordField);

        confirmarLabel = new JLabel("Confirmar contraseña:");
        confirmarLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(confirmarLabel);

        confirmarPasswordField = UIFactory.crearCampoPasswordRedondeado(15);
        formPanel.add(confirmarPasswordField);

        registerButton = UIFactory.crearBotonAzul("Registrarse", new Dimension(180, 35));
        formPanel.add(registerButton);

        volverLoginLabel = new JLabel("¿Ya tienes cuenta?, ");
        volverLoginLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(volverLoginLabel);

        volverLoginButton = new JButton("Inicia sesión");
        volverLoginButton.setFont(new Font("Arial", Font.BOLD, 12));
        volverLoginButton.setForeground(new Color(51, 204, 255));
        volverLoginButton.setBorderPainted(false);
        volverLoginButton.setFocusPainted(false);
        volverLoginButton.setContentAreaFilled(false);
        formPanel.add(volverLoginButton);

        volverMainButton = UIFactory.crearBotonAzul("Volver al Menú Principal", new Dimension(200, 35));
        formPanel.add(volverMainButton);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateLayout();
            }
        });

        setVisible(true);
    }

    public void updateLayout() {
        int topMargin = 40;
        int bottomMargin = 60;
        int sideMargin = 40;

        formPanel.setBounds(
            sideMargin,
            topMargin,
            getWidth() - 2 * sideMargin,
            getHeight() - topMargin - bottomMargin
        );

        int formWidth = formPanel.getWidth();
        int formHeight = formPanel.getHeight();

        int labelWidth = 100;
        int fieldWidth = 180;
        int fieldHeight = 30;
        int spacing = 20;

        int totalFormHeight = 30 + spacing + 3 * (fieldHeight + spacing) + 35 + spacing + 25;
        int startY = (formHeight - totalFormHeight) / 2;

        int centerXLabel = (formWidth / 2) - fieldWidth / 2 - labelWidth;
        int centerXField = (formWidth / 2) - fieldWidth / 2;

        titleLabel.setBounds((formWidth - 100) / 2, startY, 100, 30);
        int y = startY + 30 + spacing;

        credencialLabel.setBounds(centerXLabel, y, labelWidth, fieldHeight);
        credencialField.setBounds(centerXField, y, fieldWidth, fieldHeight);
        y += fieldHeight + spacing;

        passwordLabel.setBounds(centerXLabel, y, labelWidth, fieldHeight);
        passwordField.setBounds(centerXField, y, fieldWidth, fieldHeight);
        y += fieldHeight + spacing;

        confirmarLabel.setBounds(centerXLabel - 70, y, labelWidth + 100, fieldHeight);
        confirmarPasswordField.setBounds(centerXField, y, fieldWidth, fieldHeight);
        y += fieldHeight + spacing;

        registerButton.setBounds(centerXField, y, fieldWidth, 35);
        y += 35 + spacing;

        volverLoginLabel.setBounds(centerXLabel + 90, y, 140, 25);
        volverLoginButton.setBounds(centerXField + 70, y, 160, 25);

        int bottomY = formHeight - 50;
        int leftX = 20;

        volverMainButton.setBounds(leftX, bottomY, 200, 35);
    }

    public String getCredencial() {
        return credencialField.getText().trim();
    }

    public String getContrasena() {
        return new String(passwordField.getPassword()).trim();
    }

    public String getConfirmacion() {
        return new String(confirmarPasswordField.getPassword()).trim();
    }

    public void setRegistroListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void setVolverLoginListener(ActionListener listener) {
        volverLoginButton.addActionListener(listener);
    }

    public void setVolverMainListener(ActionListener listener) {
        volverMainButton.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}