package test.model; 

import models.UserModel;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.*;
import java.nio.file.*;

public class UserModelTest {
    private UserModel model;
    private final String TEST_USERS_FILE = "src/main/data/usuarios.txt";
    private final String TEST_CREDENTIALS_FILE = "src/main/data/credenciales.txt";
    private String originalUsersContent;
    private String originalCredentialsContent;

    @Before
    public void setUp() throws Exception {
        model = new UserModel();
        
        //Guardar contenido original si los archivos existen
        originalUsersContent = readFileIfExists(TEST_USERS_FILE);
        originalCredentialsContent = readFileIfExists(TEST_CREDENTIALS_FILE);
        
        //Crear estructura de directorios si no existe
        Files.createDirectories(Paths.get("src/main/data"));
        
        //Crear archivos de prueba
        writeFile(TEST_USERS_FILE, "623456,password123,100.0\n789012,clave456,50.5\n");
        writeFile(TEST_CREDENTIALS_FILE, "623456\n789012\n");
    }

    private String readFileIfExists(String filePath) throws IOException {
        if (Files.exists(Paths.get(filePath))) {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }
        return null;
    }

    private void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath),
                  content.getBytes(),
                  StandardOpenOption.CREATE,
                  StandardOpenOption.TRUNCATE_EXISTING,
                  StandardOpenOption.WRITE);
    }

    @Test
    public void testUsuarioYaRegistrado() {
        assertTrue(model.usuarioYaRegistrado("789012"));
        assertFalse(model.usuarioYaRegistrado("000000"));
    }

    @Test
    public void testCredencialExiste() {
        assertTrue(model.credencialExiste("623456"));
        assertFalse(model.credencialExiste("000000"));
    }

    @Test
    public void testAutenticarUsuario() {
        assertTrue(model.autenticarUsuario("623456", "password123"));
        assertFalse(model.autenticarUsuario("623456", "wrongpass"));
        assertFalse(model.autenticarUsuario("000000", "password123"));
    }

    @Test
    public void testEsCredencialValida() {
        assertTrue(model.esCredencialValida("500000"));
        assertTrue(model.esCredencialValida("32000000"));
        assertFalse(model.esCredencialValida("499999"));
        assertFalse(model.esCredencialValida("32000001"));
        assertFalse(model.esCredencialValida("no_numero"));
    }

    @Test
    public void testGuardarUsuario() {
        assertTrue(model.guardarUsuario("123456", "nuevacontra", 200.0));
        assertTrue(model.usuarioYaRegistrado("123456"));
        assertTrue(model.autenticarUsuario("123456", "nuevacontra"));
    }

    @Test
    public void testObtenerSaldo() {
        assertEquals(100.0, model.obtenerSaldo("623456"), 0.001);
        assertEquals(50.5, model.obtenerSaldo("789012"), 0.001);
        assertEquals(0.0, model.obtenerSaldo("000000"), 0.001);
    }

    @After
    public void tearDown() throws Exception {

        // Restaurar contenido original y/o eliminar archivos de prueba

        restoreOriginalFile(TEST_USERS_FILE, originalUsersContent);
        restoreOriginalFile(TEST_CREDENTIALS_FILE, originalCredentialsContent);
    }

    private void restoreOriginalFile(String filePath, String originalContent) throws IOException {
        if (originalContent != null) {
            writeFile(filePath, originalContent);
        } else {
            Files.deleteIfExists(Paths.get(filePath));
        }
    }
}