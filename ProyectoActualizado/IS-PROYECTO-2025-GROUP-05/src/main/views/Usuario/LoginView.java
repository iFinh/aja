package views.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import views.Components.BotonAzul;
import views.Components.RoundedPanel;
import views.Components.UIFactory;

public class LoginView extends JFrame {
    private JTextField credencialField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton volverRegistroButton;
    private JLabel titleLabel, credencialLabel, passwordLabel, volverRegistroLabel;
    private RoundedPanel formPanel;
    private BotonAzul volverMainButton;

    public LoginView() {
        setTitle("Iniciar sesión");
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

        titleLabel = new JLabel("Login");
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

        loginButton = UIFactory.crearBotonAzul("Iniciar sesión", new Dimension(180, 35));
        formPanel.add(loginButton);

        volverRegistroLabel = new JLabel("¿No tienes cuenta?,");
        volverRegistroLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(volverRegistroLabel);

        volverRegistroButton = new JButton("Regístrate");
        volverRegistroButton.setFont(new Font("Arial", Font.BOLD, 12));
        volverRegistroButton.setForeground(new Color(51, 204, 255));
        volverRegistroButton.setBorderPainted(false);
        volverRegistroButton.setFocusPainted(false);
        volverRegistroButton.setContentAreaFilled(false);
        formPanel.add(volverRegistroButton);

        volverMainButton = UIFactory.crearBotonAzul("Volver al Menú Principal", new Dimension(200, 35));
        formPanel.add(volverMainButton);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateLayout();
            }
        });

        setVisible(true);
    }

    private void updateLayout() {
        int topMargin = 40;
        int bottomMargin = 60;
        int sideMargin = 40;

        formPanel.setBounds(sideMargin, topMargin, getWidth() - 2 * sideMargin, getHeight() - topMargin - bottomMargin);

        int formWidth = formPanel.getWidth();
        int formHeight = formPanel.getHeight();

        int labelWidth = 100;
        int fieldWidth = 180;
        int fieldHeight = 30;
        int spacing = 20;

        int totalFormHeight = 30 + spacing + 2 * (fieldHeight + spacing) + 35 + spacing + 25;
        int startY = (formHeight - totalFormHeight) / 2;

        int centerXLabel = (formWidth / 2) - fieldWidth / 2 - labelWidth;
        int centerXField = (formWidth / 2) - fieldWidth / 2;

        titleLabel.setBounds((formWidth - 80) / 2, startY, 150, 30);
        int y = startY + 30 + spacing;

        credencialLabel.setBounds(centerXLabel, y, labelWidth, fieldHeight);
        credencialField.setBounds(centerXField, y, fieldWidth, fieldHeight);
        y += fieldHeight + spacing;

        passwordLabel.setBounds(centerXLabel, y, labelWidth, fieldHeight);
        passwordField.setBounds(centerXField, y, fieldWidth, fieldHeight);
        y += fieldHeight + spacing;

        loginButton.setBounds(centerXField, y, fieldWidth, 35);
        y += 35 + spacing;

        volverRegistroLabel.setBounds(centerXLabel + 90, y, 140, 25);
        volverRegistroButton.setBounds(centerXField + 70, y, 160, 25);

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

    public void setLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void setVolverRegistroListener(ActionListener listener) {
        volverRegistroButton.addActionListener(listener);
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