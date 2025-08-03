package controllers.AdminControllers;

import models.MenuSemanalModel;
import views.Admin.GestionarMenuView;

import javax.swing.*;

public class GestionarMenuController {

    private final MenuSemanalModel model;
    private final GestionarMenuView view;

    public GestionarMenuController(JFrame parent) {
        this.model = new MenuSemanalModel();
        this.view = new GestionarMenuView(parent);

        this.view.addGuardarListener(_ -> guardarMenu());

        this.view.setVisible(true);
    }

    private boolean contieneSoloLetras(String texto) {
        return texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    private void guardarMenu() {
        String dia = view.getDia();
        String tipoComida = view.getTipoComida();
        String plato = view.getPlatoPrincipal();
        String c1 = view.getContorno1();
        String c2 = view.getContorno2();
        String bebida = view.getBebida();
        String postre = view.getPostre();
        String costoStr = view.getCosto();

        if (tipoComida == null || tipoComida.isEmpty()) {
            view.mostrarError("Seleccione un tipo de comida.");
            return;
        }

        if (plato.isEmpty() || c1.isEmpty() || c2.isEmpty() || bebida.isEmpty() || postre.isEmpty() || costoStr.isEmpty()) {
            view.mostrarError("Todos los campos deben estar llenos.");
            return;
        }

        if (!contieneSoloLetras(plato) || !contieneSoloLetras(c1) || !contieneSoloLetras(c2)
            || !contieneSoloLetras(bebida) || !contieneSoloLetras(postre)) {
            view.mostrarError("Los campos referidos a la comida no pueden contener números ni caracteres especiales.");
            return;
        }

        double costo;
        try {
            costo = Double.parseDouble(costoStr);
            if (costo < 0) throw new IllegalArgumentException();
        } catch (NumberFormatException ex) {
            view.mostrarError("Ingrese un número válido para el costo.");
            return;
        } catch (IllegalArgumentException ex) {
            view.mostrarError("El costo debe ser mayor o igual a 0.");
            return;
        }

        boolean Existe = model.existeMenuParaDiaYTipo(dia, tipoComida);

        boolean sobrescribir = true;

        if (Existe) {
            String[] opciones = {"Sí", "No"};

            int opcion = JOptionPane.showOptionDialog(
                null,
                "El menú del día " + dia + " para " + tipoComida + " ya ha sido creado.\n¿Desea modificarlo?",
                "Confirmar modificación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            sobrescribir = (opcion == 0);
        }

        if (!Existe || sobrescribir) {
            if (model.guardarOModificarMenu(dia, tipoComida, plato, c1, c2, bebida, postre, costo, sobrescribir)) {
                view.mostrarMensaje("Menú guardado exitosamente para " + dia + " (" + tipoComida + ")");
                view.dispose();
            } else {
                view.mostrarError("Error al guardar el menú.");
            }
        } else {
            view.mostrarMensaje("El menú no fue modificado.");
        }
    }
}