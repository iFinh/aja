package views.Components;

import java.awt.*;
import javax.swing.*;

public class UIFactory {

    public static BotonAzul crearBotonAzul(String texto, Dimension size) {
        BotonAzul boton = new BotonAzul(texto, size);
        return boton;
    }

    public static JTextField crearCampoTextoRedondeado(int radio) {
        JTextField field = new JTextField();
        field.setBorder(new RoundedBorder(radio));
        field.setFont(new Font("Arial", Font.BOLD, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        return field;
    }

    public static JPasswordField crearCampoPasswordRedondeado(int radio) {
        JPasswordField field = new JPasswordField();
        field.setBorder(new RoundedBorder(radio));
        field.setFont(new Font("Arial", Font.BOLD, 14));
        field.setForeground(Color.BLACK);
        field.setBackground(Color.WHITE);
        return field;
    }
}