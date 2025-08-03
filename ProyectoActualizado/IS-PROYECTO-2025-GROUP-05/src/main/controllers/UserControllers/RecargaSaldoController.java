package controllers.UserControllers;

import models.RefBancariaModel;
import models.RefBancariaModel.RefBancaria;
import models.UserModel;
import models.Sesion;
import models.Usuario;
import views.Usuario.RecargaSaldoView;

import javax.swing.*;

public class RecargaSaldoController {
    private final RecargaSaldoView view;
    private final RefBancariaModel refModel;
    private final UserModel userModel;
    private final JFrame parent;

    public RecargaSaldoController(JFrame parent) {
        this.parent = parent;
        this.view = new RecargaSaldoView(parent);
        this.refModel = new RefBancariaModel();
        this.userModel = new UserModel();
        
        setupListeners();
        view.setVisible(true);
    }

    private void setupListeners() {
        view.addConfirmarListener(e -> confirmarRecarga());
        view.addCancelarListener(e -> cancelar());
    }

    private void confirmarRecarga() {
        String referencia = view.getReferencia();
        
        if (referencia.isEmpty()) {
            view.mostrarError("Debe ingresar la referencia bancaria.");
            return;
        }

        if (!refModel.esReferenciaValida(referencia)) {
            view.mostrarError("Formato de referencia inválido. Debe tener entre 8 y 12 dígitos.");
            return;
        }

        RefBancaria refBancaria = refModel.buscarReferencia(referencia);
        
        if (refBancaria == null) {
            view.mostrarError("Referencia bancaria no encontrada o ya procesada.");
            return;
        }

        if (refBancaria.getMonto() <= 0.009) {
            view.mostrarError("Error: El monto debe ser mayor a 0.00 Bs\n" +
                            "Monto recibido: " + String.format("%.2f", refBancaria.getMonto()) + " Bs");
            return;
        }

        String mensaje = String.format(
            "Se encontró la referencia:\n\n" +
            "Referencia: %s\n" +
            "Monto: %.2f Bs\n" +
            "Banco: %s\n\n" +
            "¿Confirma que desea recargar este monto a su saldo?",
            refBancaria.getReferencia(),
            refBancaria.getMonto(),
            refBancaria.getBanco()
        );

        int opcion = JOptionPane.showConfirmDialog(
            view,
            mensaje,
            "Confirmar Recarga",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            procesarRecarga(refBancaria);
        }
    }

    private void procesarRecarga(RefBancaria refBancaria) {
        if (refBancaria.getMonto() <= 0.009) {
            view.mostrarError("Error: El monto a recargar debe ser mayor a 0.00 Bs");
            return;
        }

        Usuario usuarioActual = Sesion.getUsuarioActual();
        
        if (usuarioActual == null) {
            view.mostrarError("Error: No hay sesión activa.");
            return;
        }

        double nuevoSaldo = usuarioActual.getSaldo() + refBancaria.getMonto();
        
        if (userModel.actualizarSaldo(usuarioActual.getCredencial(), nuevoSaldo)) {
            if (refModel.eliminarReferencia(refBancaria.getReferencia())) {
                usuarioActual.setSaldo(nuevoSaldo);
                
                String mensajeExito = String.format(
                    "¡Recarga exitosa!\n\n" +
                    "Referencia procesada: %s\n" +
                    "Monto recargado: %.2f Bs\n" +
                    "Saldo anterior: %.2f Bs\n" +
                    "Saldo actual: %.2f Bs",
                    refBancaria.getReferencia(),
                    refBancaria.getMonto(),
                    usuarioActual.getSaldo() - refBancaria.getMonto(),
                    usuarioActual.getSaldo()
                );
                
                view.mostrarExito(mensajeExito);
                view.limpiarCampos();
                view.dispose();
                actualizarVistaPadre();
            } else {
                view.mostrarError("Error al procesar la referencia. Contacte al administrador.");
            }
        } else {
            view.mostrarError("Error al actualizar el saldo. Intente nuevamente.");
        }
    }

    private void actualizarVistaPadre() {
        if (parent instanceof views.Usuario.DashboardUserView) {
            views.Usuario.DashboardUserView dashboard = (views.Usuario.DashboardUserView) parent;
            dashboard.actualizarSaldo(Sesion.getUsuarioActual().getSaldo());
        }
    }

    private void cancelar() {
        view.dispose();
    }
}