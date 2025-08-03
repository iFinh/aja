package test.view;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import views.Usuario.RegistroView;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.ActionEvent;

public class RegistroViewTest {
    private RegistroView view;

    @Before
    public void setUp() {
        view = new RegistroView();

        view.setVisible(false);
    }

    @After
    public void tearDown() {
        view.dispose();
    }

    @Test
    public void testComponentesInicializados() {
        assertNotNull("Campo de credencial debe estar inicializado", view.getCredencial());
        assertNotNull("Campo de contraseña debe estar inicializado", view.getContrasena());
        assertNotNull("Campo de confirmación debe estar inicializado", view.getConfirmacion());
    }

    @Test
    public void testGettersCamposVacios() {
        assertEquals("Credencial debe estar vacío al inicio", "", view.getCredencial());
        assertEquals("Contraseña debe estar vacío al inicio", "", view.getContrasena());
        assertEquals("Confirmación debe estar vacío al inicio", "", view.getConfirmacion());
    }

    @Test
    public void testRegistroListener() {
        final boolean[] listenerCalled = {false};
        
        view.setRegistroListener(_ -> listenerCalled[0] = true);
        
        // Simular click en el botón de registro
        for (ActionListener listener : view.registerButton.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, ""));
        }
        
        assertTrue("El listener de registro debe ser llamado", listenerCalled[0]);
    }

    @Test
    public void testVolverLoginListener() {
        final boolean[] listenerCalled = {false};
        
        view.setVolverLoginListener(_ -> listenerCalled[0] = true);
        
        // Simular click en el botón de volver a login
        for (ActionListener listener : view.volverLoginButton.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, ""));
        }
        
        assertTrue("El listener de volver a login debe ser llamado", listenerCalled[0]);
    }

    @Test
    public void testVolverMainListener() {
        final boolean[] listenerCalled = {false};
        
        view.setVolverMainListener(_ -> listenerCalled[0] = true);
        
        // Simular click en el botón de volver al main
        for (ActionListener listener : view.volverMainButton.getActionListeners()) {
            listener.actionPerformed(new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, ""));
        }
        
        assertTrue("El listener de volver al main debe ser llamado", listenerCalled[0]);
    }

    @Test
    public void testLayoutAfterResize() {

        int originalWidth = view.getWidth();
        int originalHeight = view.getHeight();
        
        view.setSize(800, 500);
        view.updateLayout();
        
        assertNotEquals("El layout debe cambiar después de resize", 
                       originalWidth, view.getWidth());
        assertNotEquals("El layout debe cambiar después de resize", 
                       originalHeight, view.getHeight());
    }
}