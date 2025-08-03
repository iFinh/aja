package views.Admin;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CargaCostosView extends JDialog {
    private final JTextField periodoField, cfField, cvField, nbField, mermaField;
    private final BotonAzul guardarBtn;

    public CargaCostosView(JFrame parent) {
        super(parent, "Carga de Costos", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        RoundedPanel panel = new RoundedPanel(20);
        panel.setLayout(null);
        panel.setBounds(20, 20, 340, 300);
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel periodoLabel = new JLabel("Período (MM-YYYY):");
        periodoLabel.setBounds(20, 20, 150, 25);
        panel.add(periodoLabel);

        periodoField = new JTextField();
        periodoField.setBounds(170, 20, 140, 25);
        panel.add(periodoField);

        JLabel cfLabel = new JLabel("Costos Fijos:");
        cfLabel.setBounds(20, 60, 150, 25);
        panel.add(cfLabel);

        cfField = new JTextField();
        cfField.setBounds(170, 60, 140, 25);
        panel.add(cfField);

        JLabel cvLabel = new JLabel("Costos Variables:");
        cvLabel.setBounds(20, 100, 150, 25);
        panel.add(cvLabel);

        cvField = new JTextField();
        cvField.setBounds(170, 100, 140, 25);
        panel.add(cvField);

        JLabel nbLabel = new JLabel("N° Bandejas:");
        nbLabel.setBounds(20, 140, 150, 25);
        panel.add(nbLabel);

        nbField = new JTextField();
        nbField.setBounds(170, 140, 140, 25);
        panel.add(nbField);

        JLabel mermaLabel = new JLabel("Merma (%):");
        mermaLabel.setBounds(20, 180, 150, 25);
        panel.add(mermaLabel);

        mermaField = new JTextField();
        mermaField.setBounds(170, 180, 140, 25);
        panel.add(mermaField);

        guardarBtn = new BotonAzul("Guardar y Calcular");
        guardarBtn.setBounds(90, 230, 160, 35);
        panel.add(guardarBtn);
    }

    public String getPeriodo() {
        return periodoField.getText().trim();
    }
    public String getCf() {
        return cfField.getText().trim();
    }
    public String getCv() {
        return cvField.getText().trim();
    }
    public String getNb() {
        return nbField.getText().trim();
    }
    public String getMerma() {
        return mermaField.getText().trim();
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