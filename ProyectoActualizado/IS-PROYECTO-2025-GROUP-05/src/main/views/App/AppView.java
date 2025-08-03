package views.App;
import views.Components.BotonAzul;
import views.Components.RoundedPanel;
import javax.swing.*;
import java.awt.*;

public class AppView extends JFrame {
    private RoundedPanel formPanel;
    private JLabel titleLabel;
    private BotonAzul btnAdmin, btnUser;

    public AppView() {
        configureWindow();
        initComponents();
        updateLayout();
    }

    private void configureWindow() {
        setTitle("Sistema de Acceso");
        setSize(850, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(36, 136, 242));
    }

    private void initComponents() {
        formPanel = new RoundedPanel(20);
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);
        getContentPane().add(formPanel);

        titleLabel = new JLabel("<html><div style='text-align: center;'>Sistema de Gestión del<br>Comedor Universitario SGCU</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        formPanel.add(titleLabel);

        btnAdmin = new BotonAzul("Administrador", new Dimension(180, 40));
        formPanel.add(btnAdmin);

        btnUser = new BotonAzul("Usuario", new Dimension(180, 40));
        formPanel.add(btnUser);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateLayout();
            }
        });
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

        int spacing = 30;
        int buttonWidth = 180;
        int buttonHeight = 40;

        int titleHeight = 60; 
        int totalHeight = titleHeight + spacing + buttonHeight + spacing + buttonHeight;
        int startY = (formHeight - totalHeight) / 2;

        titleLabel.setBounds((formWidth - 600) / 2, startY, 600, titleHeight);
        int y = startY + titleHeight + spacing;

        btnAdmin.setBounds((formWidth - buttonWidth) / 2, y, buttonWidth, buttonHeight);
        y += buttonHeight + spacing;

        btnUser.setBounds((formWidth - buttonWidth) / 2, y, buttonWidth, buttonHeight);
    }

    public BotonAzul getBtnAdmin() { return btnAdmin; }
    public BotonAzul getBtnUser() { return btnUser; }
    public JFrame getFrame() { return this; }
}
