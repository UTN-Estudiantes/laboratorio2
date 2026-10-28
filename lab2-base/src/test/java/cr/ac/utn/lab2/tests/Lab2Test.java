package cr.ac.utn.lab2.tests;

import cr.ac.utn.lab2.base.BaseTest;
import cr.ac.utn.lab2.pages.AddRemovePage;
import cr.ac.utn.lab2.pages.CheckboxesPage;
import cr.ac.utn.lab2.pages.DropdownPage;
import cr.ac.utn.lab2.pages.LoginPage;
import cr.ac.utn.lab2.pages.SecureAreaPage;
import cr.ac.utn.lab2.pages.TablePage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * LABORATORIO 2 - PRUEBAS AUTOMATIZADAS CON POM
 *
 * Las paginas (paquete cr.ac.utn.lab2.pages) ya estan hechas con Page Factory.
 * Su trabajo: construir las pruebas usando ESAS paginas, siguiendo el patron POM.
 *
 * Reglas:
 *  - Ninguna prueba contiene By ni @FindBy: solo llama metodos de las paginas.
 *  - Las aserciones (assertEquals, assertTrue...) van en la prueba, no en la pagina.
 *  - Cada prueba es independiente (BaseTest abre un Chrome limpio para cada una).
 *  - Si necesita un metodo que la pagina no tiene, agreguelo a la pagina (solo en el bono es obligatorio).
 *
 * Datos del sitio https://the-internet.herokuapp.com :
 *   usuario valido:  tomsmith      clave valida: SuperSecretPassword!
 *
 * Ejecutar:  mvn test        (al inicio todo falla: es lo esperado)
 */
class Lab2Test extends BaseTest {
    private static final String USUARIO_VALIDO = "tomsmith";
    private static final String CLAVE_VALIDA = "SuperSecretPassword!";

    // ---------------- Login (LoginPage, SecureAreaPage) ----------------

    @Test
    void t1_loginValidoLlevaALaZonaSegura() {
        // Inicie sesion con el usuario y la clave validos.
        // Compruebe: titulo "Secure Area", mensaje que contiene "You logged into a secure area!"
        // y que la URL actual termina en /secure.
        LoginPage login = new LoginPage(driver).abrir();

        SecureAreaPage zonaSegura = login.iniciarSesion(USUARIO_VALIDO, CLAVE_VALIDA);
        assertEquals("Secure Area", zonaSegura.titulo());
        assertTrue(zonaSegura.mensaje().contains("You logged into a secure area!"),     
        "Mensaje real: " + zonaSegura.mensaje());
        assertTrue(zonaSegura.urlActual().endsWith("/secure"),
        "URL real: " + zonaSegura.urlActual());
    }

    

    @Test
    void t2_usuarioInvalidoMuestraError() {
        // Use un usuario que no existe. Compruebe que el mensaje contiene "Your username is invalid!".
        LoginPage login = new LoginPage(driver).abrir();
        login.iniciarSesionFallido("usuarioinexistente", CLAVE_VALIDA);
        assertTrue(login.mensaje().contains("Your username is invalid!"),
        "Mensaje real: " + login.mensaje());
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        // Use el usuario valido con una clave incorrecta. Mensaje: "Your password is invalid!".
        LoginPage login = new LoginPage(driver).abrir();
        login.iniciarSesionFallido(USUARIO_VALIDO, "ClaveIncorrecta123");
        assertTrue(login.mensaje().contains("Your password is invalid!"), "Mensaje real: " + login.mensaje());

    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        // Inicie sesion, cierre sesion y compruebe que el mensaje contiene "You logged out of the secure area!".
        
        // Arrange
        SecureAreaPage zonaSegura = new LoginPage(driver).abrir().iniciarSesion(USUARIO_VALIDO, CLAVE_VALIDA);
        // Act
        LoginPage login = zonaSegura.cerrarSesion();
        // Assert
        assertTrue(login.mensaje().contains("You logged out of the secure area!"), "Mensaje real: " + login.mensaje());
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // En CheckboxesPage hay 2 casillas. Al inicio la primera NO esta marcada y la segunda SI.
        // Marque la primera y compruebe que ahora ambas estan marcadas.

        // Arrange (y verificación del estado inicial que pide el enunciado)
        CheckboxesPage casillas = new CheckboxesPage(driver).abrir();
        assertEquals(2, casillas.cantidadDeCasillas());
        assertFalse(casillas.estaMarcada(0), "Al inicio la primera casilla NO debe estar marcada");
        assertTrue(casillas.estaMarcada(1), "Al inicio la segunda casilla SI debe estar marcada");

        // Act
        casillas.alternar(0);

        // Assert
        assertTrue(casillas.estaMarcada(0), "La primera casilla debe quedar marcada");
        assertTrue(casillas.estaMarcada(1), "La segunda casilla debe seguir marcada");
    }

    @Test
    void t6_listaDesplegable() {
        // En DropdownPage seleccione "Option 2" y compruebe que esa es la opcion seleccionada.
        
        // Arrange
        DropdownPage lista = new DropdownPage(driver).abrir();
        // Act
        lista.seleccionar("Option 2");
        // Assert
        assertEquals("Option 2", lista.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // En AddRemovePage agregue 3 elementos, elimine 1 y compruebe que quedan 2 botones Delete.
        
        // Arrange
        AddRemovePage pagina = new AddRemovePage(driver).abrir();
        // Act
        pagina.agregar().agregar().agregar();
        pagina.eliminarPrimero();
        // Assert
        assertEquals(2, pagina.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // En TablePage ordene por "Last Name" (una vez, ascendente) y compruebe que
        // el apellido de la primera fila es "Bach" y el de la ultima es "Smith".

        // Arrange
        TablePage tabla = new TablePage(driver).abrir();
        // Act
        tabla.ordenarPor("Last Name");
        // Assert (columna 0 = Last Name)
        int ultimaFila = tabla.cantidadDeFilas() - 1;
        assertEquals("Bach", tabla.celda(0, 0));
        assertEquals("Smith", tabla.celda(ultimaFila, 0));
    }

    // ---------------- Bono ----------------

    @Test
    void t9_bono_correoDeUnaPersona() {
        // BONO: agregue a TablePage un metodo nuevo, por ejemplo correoPorApellido(String apellido),
        // que recorra las filas y devuelva el correo. Compruebe que el correo de "Doe" es jdoe@hotmail.com.
        // La prueba NO debe contener localizadores.
        TablePage tabla = new TablePage(driver).abrir();
        String correo = tabla.correoPorApellido("Doe");
        assertEquals("jdoe@hotmail.com", correo);
    
    }
}
