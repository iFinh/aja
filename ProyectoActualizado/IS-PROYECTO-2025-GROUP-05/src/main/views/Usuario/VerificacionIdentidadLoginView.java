package views.Usuario;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VerificacionIdentidadLoginView extends JFrame {
    private final JLabel mensajeLabel;
    private final BotonAzul verificarBtn;
    private final RoundedPanel contentPanel;

    public VerificacionIdentidadLoginView() {
        setTitle("Verificación de Identidad");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(36, 136, 242));
        setLayout(null);

        contentPanel = new RoundedPanel(20);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(20, 20, 380, 170);
        add(contentPanel);

        mensajeLabel = new JLabel("Verifica tu identidad para acceder", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mensajeLabel.setBounds(30, 30, 320, 30);
        contentPanel.add(mensajeLabel);

        verificarBtn = new BotonAzul("Verificar Identidad");
        verificarBtn.setBounds(90, 90, 200, 40);
        contentPanel.add(verificarBtn);
    }

    public void addVerificarListener(ActionListener listener) {
        verificarBtn.addActionListener(listener);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
