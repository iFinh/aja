package test.controller;

import controllers.UserControllers.MenuController;
import models.Sesion;
import models.Usuario;
import views.Usuario.MenuView;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MenuControllerTest {
    MenuController controller;
    private MenuViewStub viewStub;
    private Usuario usuarioTest;

    // Stub para MenuView
    private static class MenuViewStub extends MenuView {
        private String menuMostrado;
        private boolean disposed = false;
        private double saldoMostrado;
        private ActionListener desayunoListener;
        private ActionListener almuerzoListener;
        private ActionListener volverListener;

        @Override
        public void showMealMenu(String mealType) {
            this.menuMostrado = mealType;
        }

        @Override
        public void setSaldo(double saldo) {
            this.saldoMostrado = saldo;
        }

        @Override
        public void dispose() {
            this.disposed = true;
        }

        @Override
        public void setDesayunoListener(ActionListener listener) {
            this.desayunoListener = listener;
        }

        @Override
        public void setAlmuerzoListener(ActionListener listener) {
            this.almuerzoListener = listener;
        }

        @Override
        public void setVolverListener(ActionListener listener) {
            this.volverListener = listener;
        }
    }

    @Before
    public void setUp() {
    usuarioTest = new Usuario("623456", 150.0);
    Sesion.iniciarSesion(usuarioTest);
    viewStub = new MenuViewStub();
    controller = new MenuController(viewStub);  // Simple y limpio
    }

    @Test
    public void testConstructor_InicializacionCorrecta() {
        assertEquals(150.0, viewStub.saldoMostrado, 0.001);
        assertEquals("Desayuno", viewStub.menuMostrado);
        assertNotNull(viewStub.desayunoListener);
        assertNotNull(viewStub.almuerzoListener);
        assertNotNull(viewStub.volverListener);
    }

    @Test
    public void testDesayunoListener() {
        viewStub.desayunoListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        assertEquals("Desayuno", viewStub.menuMostrado);
    }

    @Test
    public void testAlmuerzoListener() {
        viewStub.almuerzoListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        assertEquals("Almuerzo", viewStub.menuMostrado);
    }

    @Test
    public void testVolverListener() {
        viewStub.volverListener.actionPerformed(new ActionEvent(new JButton(), 0, ""));
        assertTrue(viewStub.disposed);
        // No podemos verificar la creación del DashboardController sin modificar el código
    }

    @After
    public void tearDown() {
        Sesion.cerrarSesion();
    }
}
