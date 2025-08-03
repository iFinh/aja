package test.controller;

import java.awt.event.ActionListener;


import javax.swing.JButton;

import controllers.AdminControllers.DashboardAdminController;
import static org.junit.Assert.*;
import org.junit.Test;
import models.Sesion;
import models.Usuario;
import views.Admin.DashboardAdminView;
import java.time.LocalDateTime;


public class DashboardAdminControllerTest {
    
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

    @Test
    public void testBotones_ListenersConfigurados() {

        class ButtonTestView extends TestDashboardAdminView {
            public int consultarInsumosListeners = 0;
            public int gestionarMenuListeners = 0;
            public int generarReporteListeners = 0;
            public int cargaCostosListeners = 0;
            public int cerrarSesionListeners = 0;

            @Override
            public JButton getConsultarInsumosBtn() {
                return new JButton() {
                    @Override
                    public void addActionListener(ActionListener l) {
                        consultarInsumosListeners++;
                    }
                };
            }

            @Override
            public JButton getGestionarMenuBtn() {
                return new JButton() {
                    @Override
                    public void addActionListener(ActionListener l) {
                        gestionarMenuListeners++;
                    }
                };
            }

            @Override
            public JButton getGenerarReporteBtn() {
                return new JButton() {
                    @Override
                    public void addActionListener(ActionListener l) {
                        generarReporteListeners++;
                    }
                };
            }

            @Override
            public JButton getCargaCostosFijosBtn() {
                return new JButton() {
                    @Override
                    public void addActionListener(ActionListener l) {
                        cargaCostosListeners++;
                    }
                };
            }

            @Override
            public JButton getCerrarSesionBtn() {
                return new JButton() {
                    @Override
                    public void addActionListener(ActionListener l) {
                        cerrarSesionListeners++;
                    }
                };
            }
        }

        ButtonTestView buttonView = new ButtonTestView();
        new DashboardAdminController(buttonView);

        assertEquals(1, buttonView.consultarInsumosListeners);
        assertEquals(1, buttonView.gestionarMenuListeners);
        assertEquals(1, buttonView.generarReporteListeners);
        assertEquals(1, buttonView.cargaCostosListeners);
        assertEquals(1, buttonView.cerrarSesionListeners);
    }

@Test
public void testConstructor_VistaNull() {
    Sesion.setUsuarioActual(new Usuario(CREDENCIAL_VALIDA));
    
    try {
        new DashboardAdminController(null);
        fail("Debería haber lanzado NullPointerException");
    } catch (NullPointerException e) {
        assertTrue(e.getMessage().contains("view"));
    }
}

@Test
public void testConstructor_SesionConUsuarioInvalido() {

    final String credencialInvalida = "";
    Sesion.setUsuarioActual(new Usuario(credencialInvalida));
    
    class VistaMock extends TestDashboardAdminView {
        public boolean listenersAdded = false;
        
        @Override
        public JButton getCerrarSesionBtn() {
            JButton btn = new JButton();
            btn.addActionListener(_ -> {});
            listenersAdded = true;
            return btn;
        }
    }
    
    VistaMock vistaMock = new VistaMock();
    
    // Ejecución
    new DashboardAdminController(vistaMock);
    
    // Verificaciones
    assertEquals(credencialInvalida, vistaMock.credencialSet);
    assertNull(vistaMock.mensajeError);
    assertNotNull(vistaMock.fechaSet);
    assertFalse(vistaMock.vistaCerrada);
    assertTrue(vistaMock.listenersAdded);
}
    
}