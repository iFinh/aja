package test.model;  

import models.Usuario;  
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioTest {
    
    @Test
    public void testCrearUsuarioConSaldoInicial() {
        Usuario usuario = new Usuario("123456", 100.0);
        assertEquals("123456", usuario.getCredencial());
        assertEquals(100.0, usuario.getSaldo(), 0.001);
    }

    @Test
    public void testRecargarSaldo() {
        Usuario usuario = new Usuario("123456", 50.0);
        usuario.recargarSaldo(30.5);
        assertEquals(80.5, usuario.getSaldo(), 0.001);
    }

    @Test
    public void testDescontarSaldoExitoso() {
        Usuario usuario = new Usuario("123456", 100.0);
        assertTrue(usuario.descontarSaldo(40.0));
        assertEquals(60.0, usuario.getSaldo(), 0.001);
    }

    @Test
    public void testDescontarSaldoFallido() {
        Usuario usuario = new Usuario("123456", 30.0);
        assertFalse(usuario.descontarSaldo(50.0));
        assertEquals(30.0, usuario.getSaldo(), 0.001);
    }
}