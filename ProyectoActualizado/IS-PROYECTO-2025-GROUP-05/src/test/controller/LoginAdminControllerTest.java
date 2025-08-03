package test.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import controllers.AdminControllers.LoginAdminController;
import models.AdminModel;
import models.Sesion;
import views.Admin.LoginAdminView;

public class LoginAdminControllerTest {

    private static class TestLoginAdminView extends LoginAdminView {
        public String errorMessage;
        public String successMessage;
        public boolean disposed = false;
        public String credencial = "";
        public String password = "";
        
        private ActionListener loginListener;
        private ActionListener volverMainListener;
        private ActionListener volverRegistroListener;
        
        public TestLoginAdminView() {

        }
        
        @Override public String getCredencial() { return credencial; }
        @Override public String getPassword() { return password; }
        @Override public void mostrarError(String mensaje) { this.errorMessage = mensaje; }
        @Override public void mostrarMensaje(String mensaje) { this.successMessage = mensaje; }
        @Override public void dispose() { this.disposed = true; }
        @Override public void setVisible(boolean visible) { /* No hacer nada */ }
        
        @Override
        public void setLoginListener(ActionListener listener) {
            this.loginListener = listener;
        }
        
        @Override
        public void setVolverMainListener(ActionListener listener) {
            this.volverMainListener = listener;
        }
        
        @Override
        public void setVolverRegistroListener(ActionListener listener) {
            this.volverRegistroListener = listener;
        }
        
        public void simularLogin() {
            if (loginListener != null) {
                loginListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "login"));
            }
        }
        
        public void simularVolverMain() {
            if (volverMainListener != null) {
                volverMainListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "volverMain"));
            }
        }
        
        public void simularIrARegistro() {
            if (volverRegistroListener != null) {
                volverRegistroListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "volverRegistro"));
            }
        }
    }

    private static class TestAdminModel extends AdminModel {
        public boolean usuarioRegistradoResult = false;
        public boolean claveValidaResult = false;
        public boolean credencialesValidasResult = false;
        
        @Override
        public boolean credencialExiste(String credencial) {
            return usuarioRegistradoResult;
        }
        
        @Override
        public boolean esClaveValida(String contrasena) {
            return claveValidaResult;
        }
        
        @Override
        public boolean verificarCredenciales(String credencial, String contrasena) {
            return credencialesValidasResult;
        }
    }

    private LoginAdminController controller;
    private TestLoginAdminView viewStub;
    private TestAdminModel modelStub;

    @Before
    public void setUp() throws Exception {
        // Limpiar la sesión
        Sesion.cerrarSesion();
        
        viewStub = new TestLoginAdminView();
        modelStub = new TestAdminModel();
        
        // Crear controlador real
        controller = new LoginAdminController();
        
        // Inyectar stubs después de la creación
        Field viewField = LoginAdminController.class.getDeclaredField("view");
        viewField.setAccessible(true);
        viewField.set(controller, viewStub);
        
        Field modelField = LoginAdminController.class.getDeclaredField("model");
        modelField.setAccessible(true);
        modelField.set(controller, modelStub);
        
        controller.getClass().getDeclaredMethod("setupListeners").invoke(controller);
    }

    @Test
    public void testCamposVacios() {
        // Configuración
        viewStub.credencial = "";
        viewStub.password = "";
        
        // Ejecución
        viewStub.simularLogin();
        
        // Verificación
        assertEquals("Todos los campos son obligatorios", viewStub.errorMessage);
    }

    @Test
    public void testUsuarioNoRegistrado() {
        // Configuración
        viewStub.credencial = "usuario";
        viewStub.password = "password";
        modelStub.usuarioRegistradoResult = false;

        viewStub.simularLogin();

        assertEquals("El usuario no está registrado.", viewStub.errorMessage);
    }

    @Test
    public void testLoginExitoso() {

        viewStub.credencial = "admin";
        viewStub.password = "password123";
        modelStub.usuarioRegistradoResult = true;
        modelStub.claveValidaResult = true;
        modelStub.credencialesValidasResult = true;

        viewStub.simularLogin();

        assertEquals("Inicio de sesión exitoso.", viewStub.successMessage);
        assertTrue(viewStub.disposed);
        assertNotNull(Sesion.getUsuarioActual());
        assertEquals("admin", Sesion.getUsuarioActual().getCredencial());
        assertEquals(1000.0, Sesion.getUsuarioActual().getSaldo(), 0.001);
    }

    @Test
    public void testVolverAlMenu() {

        viewStub.simularVolverMain();

        assertTrue(viewStub.disposed);
    }

    @Test
    public void testIrARegistro() {

        viewStub.simularIrARegistro();

        assertTrue(viewStub.disposed);
    }
}