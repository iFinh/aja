package views.Admin;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionarMenuView extends JDialog {

    private final JComboBox<String> diaCombo;
    private final JComboBox<String> tipoComidaCombo;
    private final JTextField platoField, contorno1Field, contorno2Field, bebidaField, postreField, costoField;
    private final BotonAzul guardarBtn;

    public GestionarMenuView(JFrame parent) {
        super(parent, "Gestión de Menú Semanal", true);
        setSize(400, 540);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(null);
        panel.setBounds(20, 20, 340, 460);
        panel.setBackground(Color.WHITE);
        add(panel);

        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        String[] tiposComida = {"Desayuno", "Almuerzo"};

        JLabel diaLabel = new JLabel("Día de la Semana:");
        diaLabel.setBounds(20, 20, 150, 25);
        panel.add(diaLabel);

        diaCombo = new JComboBox<>(dias);
        diaCombo.setBounds(170, 20, 140, 25);
        panel.add(diaCombo);

        JLabel tipoComidaLabel = new JLabel("Tipo de Comida:");
        tipoComidaLabel.setBounds(20, 60, 150, 25);
        panel.add(tipoComidaLabel);

        tipoComidaCombo = new JComboBox<>(tiposComida);
        tipoComidaCombo.setBounds(170, 60, 140, 25);
        panel.add(tipoComidaCombo);

        JLabel platoLabel = new JLabel("Plato Principal:");
        platoLabel.setBounds(20, 100, 150, 25);
        panel.add(platoLabel);

        platoField = new JTextField();
        platoField.setBounds(170, 100, 140, 25);
        panel.add(platoField);

        JLabel contorno1Label = new JLabel("Contorno 1:");
        contorno1Label.setBounds(20, 140, 150, 25);
        panel.add(contorno1Label);

        contorno1Field = new JTextField();
        contorno1Field.setBounds(170, 140, 140, 25);
        panel.add(contorno1Field);

        JLabel contorno2Label = new JLabel("Contorno 2:");
        contorno2Label.setBounds(20, 180, 150, 25);
        panel.add(contorno2Label);

        contorno2Field = new JTextField();
        contorno2Field.setBounds(170, 180, 140, 25);
        panel.add(contorno2Field);

        JLabel bebidaLabel = new JLabel("Bebida:");
        bebidaLabel.setBounds(20, 220, 150, 25);
        panel.add(bebidaLabel);

        bebidaField = new JTextField();
        bebidaField.setBounds(170, 220, 140, 25);
        panel.add(bebidaField);

        JLabel postreLabel = new JLabel("Postre:");
        postreLabel.setBounds(20, 260, 150, 25);
        panel.add(postreLabel);

        postreField = new JTextField();
        postreField.setBounds(170, 260, 140, 25);
        panel.add(postreField);

        JLabel costoLabel = new JLabel("Costo (Bs):");
        costoLabel.setBounds(20, 300, 150, 25);
        panel.add(costoLabel);

        costoField = new JTextField();
        costoField.setBounds(170, 300, 140, 25);
        panel.add(costoField);

        guardarBtn = new BotonAzul("Guardar Menú");
        guardarBtn.setBounds(90, 360, 160, 35);
        panel.add(guardarBtn);
    }

    public String getDia() {
        return (String) diaCombo.getSelectedItem();
    }

    public String getTipoComida() {
        return (String) tipoComidaCombo.getSelectedItem();
    }

    public String getPlatoPrincipal() {
        return platoField.getText().trim();
    }

    public String getContorno1() {
        return contorno1Field.getText().trim();
    }

    public String getContorno2() {
        return contorno2Field.getText().trim();
    }

    public String getBebida() {
        return bebidaField.getText().trim();
    }

    public String getPostre() {
        return postreField.getText().trim();
    }

    public String getCosto() {
        return costoField.getText().trim();
    }

    public void addGuardarListener(ActionListener listener) {
        guardarBtn.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}