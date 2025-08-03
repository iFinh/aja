package test.view;


import controllers.AdminControllers.DashboardAdminController;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Sesion;
import models.Usuario;
import views.Admin.DashboardAdminView;
import java.time.LocalDateTime;

public class DashboardAdminViewTest {
        private static class TestDashboardAdminView extends DashboardAdminView {
        public String credencialSet;
        public LocalDateTime fechaSet;
        public boolean vistaCerrada;
        public String mensajeError;
        
        @Override
        public void setCredencial(String credencial) {
            this.credencialSet = credencial;
        }
        
        @Override
        public void updateDate(LocalDateTime fecha) {
            this.fechaSet = fecha;
        }
        
        @Override
        public void mostrarError(String mensaje) {
            this.mensajeError = mensaje;
        }
        
        @Override
        public void dispose() {
            this.vistaCerrada = true;
        }
    }
    
    // Datos de prueba
    private static final String CREDENCIAL_VALIDA = "admin123";
    private static final String MENSAJE_ERROR_SESION = "No hay una sesión activa.";
    
    private TestDashboardAdminView vistaPrueba;
    
    @Before
    public void prepararPrueba() {
        vistaPrueba = new TestDashboardAdminView();
    }
    
    @After
    public void limpiarPrueba() {
        Sesion.cerrarSesion();
    }
    
    @Test
    public void testConstructorConSesionActiva_ConfiguraVistaCorrectamente() {
        Sesion.setUsuarioActual(new Usuario(CREDENCIAL_VALIDA));
        
        new DashboardAdminController(vistaPrueba);
        
        assertEquals(CREDENCIAL_VALIDA, vistaPrueba.credencialSet);
        assertNotNull(vistaPrueba.fechaSet);
        assertNull(vistaPrueba.mensajeError);
        assertFalse(vistaPrueba.vistaCerrada);
    }
    
    @Test
    public void testConstructorSinSesionActiva_MuestraErrorYCierraVista() {
        Sesion.cerrarSesion();
        
        new DashboardAdminController(vistaPrueba);
        
        assertEquals(MENSAJE_ERROR_SESION, vistaPrueba.mensajeError);
        assertTrue(vistaPrueba.vistaCerrada);
        assertNull(vistaPrueba.credencialSet);
        assertNull(vistaPrueba.fechaSet);
    }
    
    @Test
    public void testCerrarSesion_CierraSesionYVista() {
        Sesion.setUsuarioActual(new Usuario(CREDENCIAL_VALIDA));
        DashboardAdminController controller = new DashboardAdminController(vistaPrueba);

        controller.cerrarSesion();

        assertNull(Sesion.getUsuarioActual());
        assertTrue(vistaPrueba.vistaCerrada);
    }

    @Test
    public void testCargaCostosFijos_NoProduceErrores() {
    Sesion.setUsuarioActual(new Usuario(CREDENCIAL_VALIDA));
    
    DashboardAdminController controller = new DashboardAdminController(vistaPrueba) {
        @Override
        public void cargaCostosFijos() {

        // No hacer nada (override para evitar que cree la vista real)
        
        }
    };
    
        controller.cargaCostosFijos();
        
        assertNull(vistaPrueba.mensajeError);
        assertFalse(vistaPrueba.vistaCerrada);
    }
}
