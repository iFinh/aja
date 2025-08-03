package views.Admin;

import views.Components.BotonAzul;
import views.Components.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardAdminView extends JFrame {
    private RoundedPanel formPanel;
    private JLabel dateLabel;
    private JLabel profileIcon;
    private JLabel credencialLabel;
    private JLabel rolLabel; 

    private BotonAzul verificarAlmuerzoBtn;
    private BotonAzul gestionarMenuBtn;
    private BotonAzul verificarDesayunoBtn;
    private BotonAzul cargaCostosFijosBtn;
    private BotonAzul cerrarSesionBtn;

    public DashboardAdminView() {
        setTitle("Panel de Administración");
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

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dateLabel.setForeground(Color.BLACK);
        formPanel.add(dateLabel);

        profileIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(169, 169, 169));
                g2.fillOval(0, 0, 100, 100);

                g2.setColor(Color.WHITE);
                g2.fillOval(30, 20, 40, 40);

                g2.fillOval(15, 60, 70, 50);

                g2.dispose();
            }
        };
        formPanel.add(profileIcon);

        credencialLabel = new JLabel();
        credencialLabel.setFont(new Font("Arial", Font.BOLD, 18));
        credencialLabel.setForeground(Color.BLACK);
        credencialLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(credencialLabel);

        rolLabel = new JLabel("Administrador");
        rolLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        rolLabel.setForeground(new Color(102, 102, 102));
        rolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(rolLabel);

        verificarAlmuerzoBtn = new BotonAzul("Control de Acceso Almuerzo");
        gestionarMenuBtn = new BotonAzul("Gestionar Menú");
        verificarDesayunoBtn = new BotonAzul("Control de Acceso Desayuno");
        cargaCostosFijosBtn = new BotonAzul("Carga de Costos Fijos");

        formPanel.add(verificarAlmuerzoBtn);
        formPanel.add(gestionarMenuBtn);
        formPanel.add(verificarDesayunoBtn);
        formPanel.add(cargaCostosFijosBtn);

        cerrarSesionBtn = new BotonAzul("Cerrar Sesión");
        formPanel.add(cerrarSesionBtn);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateLayout();
            }
        });

        SwingUtilities.invokeLater(() -> {
            updateLayout();
            updateDate(LocalDateTime.now());
        });

        setVisible(true);
    }

    public void updateDate(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateLabel.setText(now.format(formatter));
    }

    public void setCredencial(String credencial) {
        this.credencialLabel.setText("Credencial: " + credencial);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void updateLayout() {
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

        dateLabel.setBounds(20, 20, 150, 20);

        int profileX = (formWidth / 2 - 100) / 2;
        int profileY = (formHeight - 100) / 2 - 30;
        profileIcon.setBounds(profileX, profileY, 100, 100);

        int infoY = profileY + 110;
        credencialLabel.setBounds(profileX - 50, infoY, 200, 25);
        rolLabel.setBounds(profileX - 25, infoY + 25, 150, 20);


        int buttonWidth = 220;
        int buttonHeight = 40;
        int buttonSpacing = 20;
        int buttonsStartX = formWidth / 2 + 50;
        int buttonsStartY = profileY - 20;


        gestionarMenuBtn.setBounds(buttonsStartX, buttonsStartY, buttonWidth, buttonHeight);
        verificarAlmuerzoBtn.setBounds(buttonsStartX, buttonsStartY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight);
        verificarDesayunoBtn.setBounds(buttonsStartX, buttonsStartY + 2 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
        cargaCostosFijosBtn.setBounds(buttonsStartX, buttonsStartY + 3 * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);

        cerrarSesionBtn.setBounds(20, formHeight - 50, 150, 35);
    }

    public JButton getVerificarAlmuerzoBtn() {
        return verificarAlmuerzoBtn;
    }

    public JButton getGestionarMenuBtn() {
        return gestionarMenuBtn;
    }

    public JButton getVerificarDesayunoBtn() {
        return verificarDesayunoBtn;
    }

    public JButton getCargaCostosFijosBtn() {
        return cargaCostosFijosBtn;
    }

    public JButton getCerrarSesionBtn() {
        return cerrarSesionBtn;
    }
}