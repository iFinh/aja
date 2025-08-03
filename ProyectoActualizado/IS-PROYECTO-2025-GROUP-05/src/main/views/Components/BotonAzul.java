package views.Components;

import javax.swing.*;
import java.awt.*;

public class BotonAzul extends JButton {

    public BotonAzul(String text) {
        this(text, new Dimension(150, 40)); 
    }

    public BotonAzul(String text, Dimension size) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 16));
        setPreferredSize(size);
        setMaximumSize(size); 
        setForeground(Color.WHITE);
        setBackground(new Color(0, 153, 255));
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}