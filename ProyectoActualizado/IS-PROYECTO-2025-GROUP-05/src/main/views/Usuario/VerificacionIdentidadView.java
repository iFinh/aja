package views.Usuario;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VerificacionIdentidadView extends JDialog {
    private final JButton seleccionarArchivoBtn;
    private final BotonAzul verificarBtn;

    public VerificacionIdentidadView(JFrame parent) {
        super(parent, "Verificación de Identidad", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(null);
        panel.setBounds(20, 20, 340, 200);
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel titulo = new JLabel("Por favor verifica tu identidad", SwingConstants.CENTER);
        titulo.setBounds(20, 20, 300, 25);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo);

        seleccionarArchivoBtn = new JButton("Seleccionar archivo (JPG/PNG)");
        seleccionarArchivoBtn.setBounds(60, 70, 220, 30);
        panel.add(seleccionarArchivoBtn);

        verificarBtn = new BotonAzul("Verificar", new Dimension(160, 35));
        verificarBtn.setBounds(90, 150, 160, 35);
        panel.add(verificarBtn);
    }

    public void addSeleccionarArchivoListener(ActionListener listener) {
        seleccionarArchivoBtn.addActionListener(listener);
    }

    public void addVerificarListener(ActionListener listener) {
        verificarBtn.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
