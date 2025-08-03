package test.model;  

import models.Sesion;
import models.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SesionTest {
    
    private final Usuario usuarioPrueba = new Usuario("admin123", 0.0);

    @Before
    @After
    public void limpiarSesion() {
        Sesion.cerrarSesion();
    }

    @Test
    public void testIniciarYCerrarSesion() {
        Sesion.iniciarSesion(usuarioPrueba);
        assertNotNull(Sesion.getUsuarioActual());
        assertEquals("admin123", Sesion.getUsuarioActual().getCredencial());

        Sesion.cerrarSesion();
        assertNull(Sesion.getUsuarioActual());
    }

    @Test
    public void testSesionSinIniciar() {
        assertNull(Sesion.getUsuarioActual());
    }

    @Test
    public void testReemplazarUsuarioEnSesion() {
        Sesion.iniciarSesion(usuarioPrueba);
        Usuario nuevoUsuario = new Usuario("567890", 100.0);
        
        Sesion.iniciarSesion(nuevoUsuario);
        assertEquals("567890", Sesion.getUsuarioActual().getCredencial());
    }
}
