package test.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import controllers.AdminControllers.RegistroAdminController;
import models.AdminModel;
import views.Admin.RegistroAdminView;

public class RegistroAdminControllerTest {

    private static class TestRegistroAdminView extends RegistroAdminView {
        public String errorMessage;
        public String successMessage;
        public boolean disposed = false;
        public String credencial = "";
        public String contrasena = "";
        public String confirmacion = "";
        
        private ActionListener registroListener;
        private ActionListener volverLoginListener;
        private ActionListener volverMainListener;
        
        public TestRegistroAdminView() {
            // Constructor sin inicialización
        }
        
        @Override public String getCredencial() { return credencial; }
        @Override public String getContrasena() { return contrasena; }
        @Override public String getConfirmacion() { return confirmacion; }
        @Override public void mostrarError(String mensaje) { this.errorMessage = mensaje; }
        @Override public void mostrarMensaje(String mensaje) { this.successMessage = mensaje; }
        @Override public void dispose() { this.disposed = true; }
        @Override public void setVisible(boolean visible) { /* No hacer nada */ }
        
        @Override
        public void setRegistroListener(ActionListener listener) {
            this.registroListener = listener;
        }
        
        @Override
        public void setVolverLoginListener(ActionListener listener) {
            this.volverLoginListener = listener;
        }
        
        @Override
        public void setVolverMainListener(ActionListener listener) {
            this.volverMainListener = listener;
        }
        
        public void simularRegistro() {
            if (registroListener != null) {
                registroListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "registro"));
            }
        }
        
        public void simularVolverLogin() {
            if (volverLoginListener != null) {
                volverLoginListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "volverLogin"));
            }
        }
        
        public void simularVolverMain() {
            if (volverMainListener != null) {
                volverMainListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "volverMain"));
            }
        }
    }

    private static class TestAdminModel extends AdminModel {
        public boolean claveValidaResult = false;
        public boolean existeCredencialResult = false;
        public boolean guardarAdminResult = false;
        
        @Override
        public boolean esClaveValida(String contrasena) {
            return claveValidaResult;
        }
        
        @Override
        public boolean credencialExiste(String credencial) {
            return existeCredencialResult;
        }
        
        @Override
        public boolean guardarAdmin(String credencial, String contrasena) {
            return guardarAdminResult;
        }
    }

    private RegistroAdminController controller;
    private TestRegistroAdminView viewStub;
    private TestAdminModel modelStub;

    @Before
    public void setUp() throws Exception {
        // Crear stubs
        viewStub = new TestRegistroAdminView();
        modelStub = new TestAdminModel();
        
        // Crear controlador real
        controller = new RegistroAdminController();
       
        injectPrivateField(controller, "view", viewStub);
        injectPrivateField(controller, "model", modelStub);
        
        controller.getClass().getDeclaredMethod("setupListeners").invoke(controller);
    }

    private void injectPrivateField(Object target, String fieldName, Object value) 
            throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testCamposVacios() {
 
        viewStub.credencial = "";
        viewStub.contrasena = "";
        viewStub.confirmacion = "";
        
        viewStub.simularRegistro();
  
        assertEquals("Todos los campos son obligatorios.", viewStub.errorMessage);
    }

    @Test
    public void testClaveInvalida() {
        // Configuración
        viewStub.credencial = "admin";
        viewStub.contrasena = "12345"; // Menos de 6 caracteres
        viewStub.confirmacion = "12345";
        modelStub.claveValidaResult = false;

        viewStub.simularRegistro();
 
        assertEquals("Contraseña debe ser de al menos 6 caracteres.", viewStub.errorMessage);
    }

    @Test
    public void testContrasenasNoCoinciden() {

        viewStub.credencial = "admin";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password456";
        modelStub.claveValidaResult = true;
  
        viewStub.simularRegistro();

        assertEquals("Las contraseñas no coinciden.", viewStub.errorMessage);
    }

    @Test
    public void testCredencialExistente() {

        viewStub.credencial = "admin";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.claveValidaResult = true;
        modelStub.existeCredencialResult = true;

        viewStub.simularRegistro();
   
        assertEquals("La credencial ya está registrada.", viewStub.errorMessage);
    }

    @Test
    public void testRegistroExitoso() {

        viewStub.credencial = "admin";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.claveValidaResult = true;
        modelStub.existeCredencialResult = false;
        modelStub.guardarAdminResult = true;
        
        viewStub.simularRegistro();

        assertEquals("Administrador registrado con éxito.", viewStub.successMessage);
        assertTrue(viewStub.disposed);
    }

    @Test
    public void testErrorAlGuardar() {
  
        viewStub.credencial = "admin";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.claveValidaResult = true;
        modelStub.existeCredencialResult = false;
        modelStub.guardarAdminResult = false;

        viewStub.simularRegistro();

        assertEquals("Error al guardar el administrador.", viewStub.errorMessage);
    }

    @Test
    public void testVolverLogin() {

        viewStub.simularVolverLogin();

        assertTrue(viewStub.disposed);
    }

    @Test
    public void testVolverMain() {

        viewStub.simularVolverMain();

        assertTrue(viewStub.disposed);
    }
}
