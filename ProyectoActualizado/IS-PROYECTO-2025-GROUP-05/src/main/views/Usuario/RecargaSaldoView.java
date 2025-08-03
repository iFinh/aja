package views.Usuario;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;
import views.Components.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RecargaSaldoView extends JDialog {
    private JTextField referenciaField;
    private BotonAzul confirmarBtn, cancelarBtn;
    private JLabel titleLabel, infoLabel, referenciaLabel;
    private JTextArea datosPagoArea;
    private RoundedPanel formPanel, datosPanel;

    public RecargaSaldoView(JFrame parent) {
        super(parent, "Recarga de Saldo", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(new Color(36, 136, 242));

        initComponents();
        setupLayout();
    }

    private void initComponents() {
        formPanel = new RoundedPanel(20);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        add(formPanel);

        titleLabel = new JLabel("Recarga de Saldo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        datosPanel = new RoundedPanel(15);
        datosPanel.setBackground(new Color(240, 248, 255));
        datosPanel.setLayout(null);
        formPanel.add(datosPanel);

        JLabel datosLabel = new JLabel("Datos para Pago Móvil:");
        datosLabel.setFont(new Font("Arial", Font.BOLD, 16));
        datosPanel.add(datosLabel);

        datosPagoArea = new JTextArea();
        datosPagoArea.setText(
            "BANCO: Banco de Venezuela\n" +
            "TELÉFONO: 0414-1234567\n" +
            "CÉDULA: V-12345678\n" +
            "TITULAR: Sistema SGCU\n\n" +
            "Montos disponibles (mínimo 0.01 Bs):\n" +
            "• Bs 5.00 - Bs 10.00 - Bs 15.00\n" +
            "• Bs 20.00 - Bs 25.00 - Bs 30.00\n\n" +
            "Instrucciones:\n" +
            "1. Realice el pago móvil\n" +
            "2. Copie la referencia bancaria\n" +
            "3. Péguela en el campo de abajo\n" +
            "4. Confirme la recarga"
        );
        datosPagoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        datosPagoArea.setEditable(false);
        datosPagoArea.setBackground(new Color(240, 248, 255));
        datosPagoArea.setForeground(Color.BLACK);
        datosPanel.add(datosPagoArea);

        infoLabel = new JLabel("<html><div style='text-align: center; color: red;'>" +
                             "<b>IMPORTANTE:</b> Solo confirme si ya realizó el pago móvil.<br>" +
                             "La referencia será validada automáticamente." +
                             "</div></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(infoLabel);

        referenciaLabel = new JLabel("Referencia Bancaria:");
        referenciaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(referenciaLabel);

        referenciaField = UIFactory.crearCampoTextoRedondeado(15);
        referenciaField.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(referenciaField);

        confirmarBtn = new BotonAzul("Confirmar Recarga", new Dimension(180, 40));
        cancelarBtn = new BotonAzul("Cancelar", new Dimension(120, 40));
        cancelarBtn.setBackground(new Color(108, 117, 125));
        
        formPanel.add(confirmarBtn);
        formPanel.add(cancelarBtn);
    }

    private void setupLayout() {
        formPanel.setBounds(20, 20, 440, 540);

        titleLabel.setBounds(0, 20, 440, 30);

        datosPanel.setBounds(20, 70, 400, 280);
        
        JLabel datosLabel = (JLabel) datosPanel.getComponent(0);
        datosLabel.setBounds(10, 10, 200, 20);
        
        datosPagoArea.setBounds(10, 35, 380, 235);

        infoLabel.setBounds(20, 370, 400, 40);

        referenciaLabel.setBounds(20, 430, 150, 25);
        referenciaField.setBounds(20, 460, 400, 35);

        confirmarBtn.setBounds(80, 510, 180, 40);
        cancelarBtn.setBounds(280, 510, 120, 40);
    }

    public String getReferencia() {
        return referenciaField.getText().trim();
    }

    public void limpiarCampos() {
        referenciaField.setText("");
    }

    public void addConfirmarListener(ActionListener listener) {
        confirmarBtn.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        cancelarBtn.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Recarga Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
}