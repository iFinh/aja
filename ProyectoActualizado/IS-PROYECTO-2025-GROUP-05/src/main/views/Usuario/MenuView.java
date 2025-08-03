package views.Usuario;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Locale;
import controllers.UserControllers.MenuController;
import models.Usuario;
import models.Sesion;

public class MenuView extends JFrame {
    private JLabel dateLabel, titleLabel, credencialInfoLabel, precioLabel, saldoLabel;
    private BotonAzul desayunoBtn, almuerzoBtn, volverBtn;
    private RoundedPanel daysPanel, formPanel, buttonPanel;
    private String currentMealType = "Desayuno";
    private MenuController controller;

    public MenuView() {
        initComponents();
        setupWindow();
    }

    private void initComponents() {
        Usuario usuario = Sesion.getUsuarioActual();
        setTitle("Sistema de Menú Diario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(36, 136, 242));
        getContentPane().setLayout(null);

        formPanel = new RoundedPanel(20);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        getContentPane().add(formPanel);

        dateLabel = new JLabel(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(dateLabel);

        credencialInfoLabel = new JLabel("Credencial: " + usuario.getCredencial());
        credencialInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        credencialInfoLabel.setForeground(Color.BLACK);
        formPanel.add(credencialInfoLabel);

        saldoLabel = new JLabel();
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        saldoLabel.setForeground(new Color(0, 153, 255));
        formPanel.add(saldoLabel);

        precioLabel = new JLabel();
        precioLabel.setFont(new Font("Arial", Font.BOLD, 17));
        precioLabel.setForeground(new Color(0, 102, 102));
        formPanel.add(precioLabel);

        titleLabel = new JLabel("Menú del Día");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(titleLabel);

        buttonPanel = new RoundedPanel(15);
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        formPanel.add(buttonPanel);

        desayunoBtn = new BotonAzul("Desayuno", new Dimension(140, 35));
        almuerzoBtn = new BotonAzul("Almuerzo", new Dimension(140, 35));
        buttonPanel.add(desayunoBtn);
        buttonPanel.add(almuerzoBtn);

        daysPanel = new RoundedPanel(15);
        daysPanel.setLayout(new BoxLayout(daysPanel, BoxLayout.Y_AXIS));
        daysPanel.setOpaque(false);
        daysPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(daysPanel);

        volverBtn = new BotonAzul("Volver al Dashboard", new Dimension(180, 35));
        formPanel.add(volverBtn);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateLayout();
            }
        });
    }

    private void setupWindow() {
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateLayout() {
        int width = getWidth() - 100;
        int height = getHeight() - 100;

        formPanel.setBounds(50, 50, width, height);
        credencialInfoLabel.setBounds(width - 220, 20, 200, 20);
        saldoLabel.setBounds(width - 220, 45, 200, 20);
        precioLabel.setBounds(55, formPanel.getHeight() - 40, 300, 25);
        titleLabel.setBounds(0, 20, width, 30);
        buttonPanel.setBounds((width - 350) / 2, 80, 350, 50);
        daysPanel.setBounds((width - 500) / 2, 150, 500, 280);
        volverBtn.setBounds(width - 200, height - 50, 180, 35);
        dateLabel.setBounds(55, 45, 100, 20);
    }

    public void showMealMenu(String tipo, Map<String, Map<String, String>> menusPorDia) {
        currentMealType = tipo;
        daysPanel.removeAll();

        Usuario usuario = Sesion.getUsuarioActual();

        for (Map.Entry<String, Map<String, String>> entry : menusPorDia.entrySet()) {
            String dia = entry.getKey();
            Map<String, String> menu = entry.getValue();

            RoundedPanel card = new RoundedPanel(15);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(new Color(240, 240, 240));
            card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel titulo = new JLabel("MENÚ DE " + dia.toUpperCase(), SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 16));
            titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(titulo);
            card.add(Box.createRigidArea(new Dimension(0, 10)));

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);

            for (Map.Entry<String, String> plato : menu.entrySet()) {
                if (!plato.getKey().equals("habilitado")) {
                    JLabel item = new JLabel("• " + plato.getKey() + ": " + plato.getValue());
                    item.setFont(new Font("Arial", Font.PLAIN, 14));
                    item.setAlignmentX(Component.CENTER_ALIGNMENT);
                    contentPanel.add(item);
                }
            }

            card.add(contentPanel);
            card.add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel statusLabel = new JLabel();
            statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
            statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            boolean habilitado = Boolean.parseBoolean(menu.getOrDefault("habilitado", "true"));

            boolean yaReservado = (tipo.equalsIgnoreCase("Desayuno") && usuario.tieneDesayuno()) ||
                                  (tipo.equalsIgnoreCase("Almuerzo") && usuario.tieneAlmuerzo());

            JButton reserveButton = new BotonAzul("Reservar", new Dimension(120, 30));
            reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            if (!habilitado) {
                reserveButton.setText("Terminado");
                reserveButton.setEnabled(false);
                statusLabel.setText("Estado: Terminado");
                statusLabel.setForeground(Color.GRAY);
            } else if (yaReservado) {
                reserveButton.setText("Reservado");
                reserveButton.setEnabled(false);
                statusLabel.setText("Estado: Reservado");
                statusLabel.setForeground(new Color(0, 153, 0));
            } else {
                statusLabel.setText("Estado: Disponible");
                statusLabel.setForeground(new Color(0, 102, 204));
                reserveButton.addActionListener(e -> {
                    if (controller != null) {
                        controller.reservarComida(currentMealType);
                    }
                });
            }

            card.add(statusLabel);
            card.add(Box.createRigidArea(new Dimension(0, 15)));
            card.add(reserveButton);

            daysPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            daysPanel.add(card);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    // Listeners setters
    public void setDesayunoListener(java.awt.event.ActionListener listener) {
        desayunoBtn.addActionListener(listener);
    }

    public void setAlmuerzoListener(java.awt.event.ActionListener listener) {
        almuerzoBtn.addActionListener(listener);
    }

    public void setVolverListener(java.awt.event.ActionListener listener) {
        volverBtn.addActionListener(listener);
    }

    // Setters for saldo and precio
    public void setPrecioBandeja(double precio) {
        precioLabel.setText(String.format("Precio por bandeja: %.2fBs", precio));
    }

    public void setSaldo(double saldo) {
        saldoLabel.setText(String.format("Saldo: %.2fBs", saldo));
    }

    // Controller setter
    public void setController(MenuController controller) {
        this.controller = controller;
    }
}
