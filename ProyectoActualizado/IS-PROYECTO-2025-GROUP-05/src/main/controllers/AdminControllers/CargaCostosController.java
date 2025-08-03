package controllers.AdminControllers;

import models.AdminModel;
import views.Admin.CargaCostosView;

import javax.swing.*;

public class CargaCostosController {
    private final CargaCostosView view;
    private final AdminModel model;

    public CargaCostosController(JFrame parent) {
        this.model = new AdminModel();
        this.view = new CargaCostosView(parent);

        this.view.addGuardarListener(e -> guardarCostos());

        this.view.setVisible(true);
    }

    private void guardarCostos() {
        String periodo = view.getPeriodo();
        String cfStr = view.getCf();
        String cvStr = view.getCv();
        String nbStr = view.getNb();
        String mermaStr = view.getMerma();

        // Validación de campos vacíos
        if (periodo.isEmpty() || cfStr.isEmpty() || cvStr.isEmpty() || nbStr.isEmpty() || mermaStr.isEmpty()) {
            view.mostrarError("Todos los campos deben estar llenos.");
            return;
        }

        // Validación de formato de período (MM-YYYY)
        if (!periodo.matches("^(0[1-9]|1[0-2])-\\d{4}$")) {
            view.mostrarError("Formato de período inválido. Use MM-YYYY.");
            return;
        }

        // Validación de período actual o futuro
        if (!model.esPeriodoValido(periodo)) {
            view.mostrarError("No se pueden calcular costos para períodos anteriores al mes actual.\n" +
                    "Solo se permiten cálculos para el mes actual o meses futuros.");
            return;
        }

        // Validación si ya existe un cálculo previo
        if (model.existeCostoPeriodo(periodo)) {
            int opcion = JOptionPane.showConfirmDialog(
                    view,
                    "Ya existe un cálculo de costos para el período " + periodo + ".\n" +
                            "¿Desea sobrescribirlo con los nuevos valores?",
                    "Período Existente",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        double cf, cv, merma;
        int nb;

        // Validación de números
        try {
            cf = Double.parseDouble(cfStr);
            cv = Double.parseDouble(cvStr);
            merma = Double.parseDouble(mermaStr);
            nb = Integer.parseInt(nbStr);

            if (cf < 0 || cv < 0 || nb <= 0 || merma < 0 || merma > 100) {
                throw new IllegalArgumentException();
            }

        } catch (NumberFormatException ex) {
            view.mostrarError("Ingrese solo números válidos en los campos de costos, merma y bandejas.");
            return;
        } catch (IllegalArgumentException ex) {
            view.mostrarError("Verifique que:\n- Los costos no sean negativos\n- N° de bandejas sea mayor que cero\n- Merma esté entre 0% y 100%");
            return;
        }

        // Cálculo del CCB
        double ccb = model.calcularCCB(cf, cv, nb, merma);
        if (ccb <= 0) {
            view.mostrarError("Error en el cálculo del CCB. Verifique los valores ingresados.");
            return;
        }

        // Mostrar resumen y confirmar
        String resumen = String.format(
                "Resumen del Cálculo CCB:\n\n" +
                        "Período: %s\n" +
                        "Costos Fijos: %.2f Bs\n" +
                        "Costos Variables: %.2f Bs\n" +
                        "N° de Bandejas: %d\n" +
                        "Merma: %.2f%%\n\n" +
                        "CCB Calculado: %.5f Bs\n\n" +
                        "¿Confirma que desea guardar estos datos?",
                periodo, cf, cv, nb, merma, ccb
        );

        int confirmacion = JOptionPane.showConfirmDialog(
                view,
                resumen,
                "Confirmar Cálculo CCB",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        // Guardar costos
        if (model.guardarCostos(periodo, ccb, cf, cv, nb, merma)) {
            view.mostrarMensaje(String.format(
                    "Costos guardados exitosamente.\n\n" +
                            "Período: %s\n" +
                            "CCB: %.5f Bs\n" +
                            "Los datos completos han sido almacenados en el sistema.",
                    periodo, ccb
            ));
            view.dispose();
        } else {
            view.mostrarError("Error al guardar los costos. Intente nuevamente.");
        }
    }
}
