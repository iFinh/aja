package test.controller;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import controllers.UserControllers.RegistroController;
import models.Sesion;
import models.UserModel;
import models.Usuario;
import views.Usuario.RegistroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class RegistroControllerTest {
    private RegistroViewStub viewStub;
    private UserModelStub modelStub;
    RegistroController controller;

    // Stub para RegistroView
    private static class RegistroViewStub extends RegistroView {
        public String credencial;
        public String contrasena;
        public String confirmacion;
        public String mensajeError;
        public String mensajeExito;
        public boolean disposed;
        public ActionListener registroListener;
        public ActionListener volverLoginListener;
        public ActionListener volverMainListener;

        public RegistroViewStub() {
            super();
        }

        @Override
        public String getCredencial() {
            return credencial;
        }

        @Override
        public String getContrasena() {
            return contrasena;
        }

        @Override
        public String getConfirmacion() {
            return confirmacion;
        }

        @Override
        public void mostrarError(String mensaje) {
            this.mensajeError = mensaje;
        }

        @Override
        public void mostrarMensaje(String mensaje) {
            this.mensajeExito = mensaje;
        }

        @Override
        public void dispose() {
            this.disposed = true;
        }

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
    }

    // Stub para UserModel
    private static class UserModelStub extends UserModel {
        public boolean credencialValida = true;
        public boolean credencialExiste = true;
        public boolean usuarioYaRegistrado = false;
        public boolean claveValida = true;
        public boolean guardarExitoso = true;

        @Override
        public boolean esCredencialValida(String credencial) {
            return credencialValida;
        }

        @Override
        public boolean credencialExiste(String credencial) {
            return credencialExiste;
        }

        @Override
        public boolean usuarioYaRegistrado(String credencial) {
            return usuarioYaRegistrado;
        }

        @Override
        public boolean esClaveValida(String clave) {
            return claveValida;
        }

        @Override
        public boolean guardarUsuario(String credencial, String clave, double saldo) {
            if (guardarExitoso) {
                Sesion.iniciarSesion(new Usuario(credencial, saldo));
            }
            return guardarExitoso;
        }
    }

    @Before
    public void setUp() {
        viewStub = new RegistroViewStub();
        modelStub = new UserModelStub();
        controller = new RegistroController(viewStub, modelStub);
        Sesion.cerrarSesion();
    }

    @After
    public void tearDown() {
        Sesion.cerrarSesion();
    }

    @Test
    public void testRegistroExitoso() {
        // Configurar datos para prueba
        viewStub.credencial = "12345678";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.guardarExitoso = true;
        
        // Ejecutar
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        // Verificar
        assertNotNull("Debería haber iniciado sesión", Sesion.getUsuarioActual());
        assertEquals("12345678", Sesion.getUsuarioActual().getCredencial());
        assertEquals("¡Registro exitoso! Bienvenido: 12345678", viewStub.mensajeExito);
        assertTrue("Debería cerrar la vista", viewStub.disposed);
    }

    @Test
    public void testCamposVacios() {
        viewStub.credencial = "";
        viewStub.contrasena = "";
        viewStub.confirmacion = "";
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("Todos los campos son obligatorios", viewStub.mensajeError);
        assertNull("No debería haber sesión iniciada", Sesion.getUsuarioActual());
    }

    @Test
    public void testCredencialInvalida() {
        viewStub.credencial = "123";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.credencialValida = false;
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("Cédula inválida.", viewStub.mensajeError);
    }

    @Test
    public void testCredencialNoAutorizada() {
        viewStub.credencial = "87654321";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.credencialExiste = false;
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("La cédula no está autorizada.", viewStub.mensajeError);
    }

    @Test
    public void testUsuarioYaRegistrado() {
        viewStub.credencial = "11223344";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.usuarioYaRegistrado = true;
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("La cédula ya está registrada.", viewStub.mensajeError);
    }

    @Test
    public void testClaveInvalida() {
        viewStub.credencial = "12345678";
        viewStub.contrasena = "short";
        viewStub.confirmacion = "short";
        modelStub.claveValida = false;
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("Contraseña debe ser de al menos 6 caracteres.", viewStub.mensajeError);
    }

    @Test
    public void testContrasenasNoCoinciden() {
        viewStub.credencial = "12345678";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "different123";
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("Las contraseñas no coinciden.", viewStub.mensajeError);
    }

    @Test
    public void testGuardarUsuarioFalla() {
        viewStub.credencial = "12345678";
        viewStub.contrasena = "password123";
        viewStub.confirmacion = "password123";
        modelStub.guardarExitoso = false;
        
        viewStub.registroListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        
        assertEquals("Error al guardar el usuario.", viewStub.mensajeError);
        assertNull("No debería haber sesión iniciada", Sesion.getUsuarioActual());
    }

    @Test
    public void testVolverLogin() {
        viewStub.volverLoginListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        assertTrue("Debería cerrar la vista", viewStub.disposed);
    }

    @Test
    public void testVolverMain() {
        viewStub.volverMainListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        assertTrue("Debería cerrar la vista", viewStub.disposed);
    }
}