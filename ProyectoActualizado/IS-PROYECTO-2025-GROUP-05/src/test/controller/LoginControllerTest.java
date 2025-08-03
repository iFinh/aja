package test.controller;

import controllers.UserControllers.LoginController;
import models.UserModel;
import models.Sesion;
import models.Usuario;
import views.Usuario.LoginView;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

public class LoginControllerTest {
    private LoginController controller;
    private UserModelStub userModelStub;
    private LoginViewStub viewStub;

    private static class UserModelStub extends UserModel {
        private boolean credencialValida = true;
        private boolean usuarioRegistrado = true;
        private boolean autenticacionExitosa = true;
        private double saldo = 100.0;

        @Override
        public boolean esCredencialValida(String credencial) {
            return credencialValida;
        }

        @Override
        public boolean usuarioYaRegistrado(String credencial) {
            return usuarioRegistrado;
        }

        @Override
        public boolean autenticarUsuario(String credencial, String contrasena) {
            return autenticacionExitosa;
        }

        @Override
        public double obtenerSaldo(String credencial) {
            return saldo;
        }
    }

    private static class LoginViewStub extends LoginView {
        private String errorMessage;
        private String successMessage;
        private boolean disposed = false;

        @Override
        public void mostrarError(String mensaje) {
            this.errorMessage = mensaje;
        }

        @Override
        public void mostrarMensaje(String mensaje) {
            this.successMessage = mensaje;
        }

        @Override
        public void dispose() {
            this.disposed = true;
        }

        @Override
        public String getCredencial() {
            return "623456";
        }

        @Override
        public String getContrasena() {
            return "password123";
        }
    }

    @Before
    public void setUp() {
        userModelStub = new UserModelStub();
        viewStub = new LoginViewStub();
        controller = new LoginController(userModelStub, viewStub);
        Sesion.cerrarSesion(); // Limpiar sesión antes de cada test
    }

    @Test
    public void testCredencialInvalida() {
        userModelStub.credencialValida = false;
        controller.intentarLogin();
        
        assertEquals("La cédula no es válida.", viewStub.errorMessage);
        assertNull(viewStub.successMessage);
        assertFalse(viewStub.disposed);
        assertNull(Sesion.getUsuarioActual()); // Verificar que no hay usuario en sesión
    }

    @Test
    public void testUsuarioNoRegistrado() {
        userModelStub.usuarioRegistrado = false;
        controller.intentarLogin();
        
        assertEquals("El usuario no está registrado.", viewStub.errorMessage);
        assertNull(viewStub.successMessage);
        assertFalse(viewStub.disposed);
        assertNull(Sesion.getUsuarioActual());
    }

    @Test
    public void testContrasenaIncorrecta() {
        userModelStub.autenticacionExitosa = false;
        controller.intentarLogin();
        
        assertEquals("Contraseña incorrecta.", viewStub.errorMessage);
        assertNull(viewStub.successMessage);
        assertFalse(viewStub.disposed);
        assertNull(Sesion.getUsuarioActual());
    }

    @Test
    public void testLoginExitoso() {
        controller.intentarLogin();
        
        assertNull(viewStub.errorMessage);
        assertEquals("Inicio de sesión exitoso.", viewStub.successMessage);
        assertTrue(viewStub.disposed);
        
        Usuario usuario = Sesion.getUsuarioActual();
        assertNotNull(usuario);
        assertEquals("623456", usuario.getCredencial());
        assertEquals(100.0, usuario.getSaldo(), 0.001);
    }

    @Test
    public void testVolverAlMenuPrincipal() {
        controller.volverAlMenuPrincipal();
        assertTrue(viewStub.disposed);
    }

    @Test
    public void testIrARegistro() {
        controller.irARegistro();
        assertTrue(viewStub.disposed);
    }

    @After
    public void tearDown() {
        Sesion.cerrarSesion();
    }
}