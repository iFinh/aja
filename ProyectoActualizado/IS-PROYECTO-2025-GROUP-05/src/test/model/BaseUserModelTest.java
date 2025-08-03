package test.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import models.BaseUserModel;
import java.io.*;

public class BaseUserModelTest {



    private static class TestableBaseUserModel extends BaseUserModel {

    }

    private TestableBaseUserModel model;
    private File testFile;

    @Before
    public void setUp() throws IOException {
        model = new TestableBaseUserModel();
        testFile = File.createTempFile("test", ".txt");

        // Limpiar el archivo 

        new FileWriter(testFile).close();

    }

    @After
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testEnsureFileExists() {
        File nonExistentFile = new File(testFile.getParent(), "nonexistent.txt");
        assertFalse("El archivo no debe existir inicialmente", nonExistentFile.exists());
        
        model.ensureFileExists(nonExistentFile);
        
        assertTrue("El archivo debe ser creado", nonExistentFile.exists());
        nonExistentFile.delete();
    }

    @Test
    public void testCredencialExiste() throws IOException {

        // Preparar datos de prueba

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("123456");
            writer.newLine();
            writer.write("789012");
        }
        
        assertTrue("Debe encontrar credencial existente", model.credencialExiste("123456", testFile));
        assertTrue("Debe encontrar credencial existente", model.credencialExiste("789012", testFile));
        assertFalse("No debe encontrar credencial inexistente", model.credencialExiste("000000", testFile));
    }

    @Test
    public void testCredencialExisteConArchivoInexistente() {
        File nonExistentFile = new File("nonexistent.txt");
        assertFalse("Debe retornar false para archivo que no existe", 
                   model.credencialExiste("123456", nonExistentFile));
    }

    @Test
    public void testAutenticar() throws IOException {
        // Preparar datos para prueba
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {
            writer.write("user1,pass1");
            writer.newLine();
            writer.write("user2,pass2");
        }
        
        assertTrue("Debe autenticar credenciales correctas", 
                  model.autenticar("user1", "pass1", testFile));
        assertTrue("Debe autenticar credenciales correctas", 
                  model.autenticar("user2", "pass2", testFile));
        assertFalse("No debe autenticar usuario incorrecto", 
                   model.autenticar("user3", "pass1", testFile));
        assertFalse("No debe autenticar contraseña incorrecta", 
                   model.autenticar("user1", "wrongpass", testFile));
    }

    @Test
    public void testAutenticarConArchivoInexistente() {
        File nonExistentFile = new File("nonexistent.txt");
        assertFalse("Debe retornar false para archivo que no existe", 
                   model.autenticar("user", "pass", nonExistentFile));
    }

    @Test
    public void testGuardarCredencial() throws IOException {
        assertTrue("Debe guardar credencial exitosamente", 
                 model.guardarCredencial("newuser", "newpass", testFile));
        
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            String line = reader.readLine();
            assertEquals("Debe contener la credencial guardada", 
                        "newuser,newpass", line);
        }
    }

    @Test
    public void testEsClaveValida() {
        assertTrue("Clave válida (6 caracteres)", model.esClaveValida("123456"));
        assertTrue("Clave válida (más de 6 caracteres)", model.esClaveValida("1234567"));
        assertFalse("Clave inválida (menos de 6 caracteres)", model.esClaveValida("12345"));
        assertFalse("Clave inválida (null)", model.esClaveValida(null));
    }

    @Test
    public void VerificarQueExistaYNoModifique() {
        long originalLength = testFile.length();
        model.ensureFileExists(testFile);
        assertEquals("No debe modificar archivo existente", 
                    originalLength, testFile.length());
    }
}
