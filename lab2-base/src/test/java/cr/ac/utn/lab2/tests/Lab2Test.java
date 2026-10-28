package cr.ac.utn.lab2.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cr.ac.utn.lab2.base.BaseTest;
import cr.ac.utn.lab2.pages.AddRemovePage;
import cr.ac.utn.lab2.pages.CheckboxesPage;
import cr.ac.utn.lab2.pages.DropdownPage;
import cr.ac.utn.lab2.pages.LoginPage;
import cr.ac.utn.lab2.pages.SecureAreaPage;
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
        // Arrange
        LoginPage login = new LoginPage(driver).abrir();

        // Act
        SecureAreaPage zonaSegura = login.iniciarSesion(USUARIO_VALIDO, CLAVE_VALIDA);

        // Assert
        assertEquals("Secure Area", zonaSegura.titulo());
        String mensaje = zonaSegura.mensaje();
        assertTrue(mensaje.contains("You logged into a secure area!"),
                "Mensaje real: " + mensaje);
        String url = zonaSegura.urlActual();
        assertTrue(url.endsWith("/secure"), "URL real: " + url);
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        // Arrange
        LoginPage login = new LoginPage(driver).abrir();

        // Act
        login.iniciarSesionFallido("usuarioInexistente", CLAVE_VALIDA);

        // Assert
        String mensaje = login.mensaje();
        assertTrue(mensaje.contains("Your username is invalid!"), "Mensaje real: " + mensaje);
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        // Arrange
        LoginPage login = new LoginPage(driver).abrir();

        // Act
        login.iniciarSesionFallido(USUARIO_VALIDO, "claveIncorrecta");

        // Assert
        String mensaje = login.mensaje();
        assertTrue(mensaje.contains("Your password is invalid!"), "Mensaje real: " + mensaje);
    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        // Arrange
        SecureAreaPage zonaSegura = new LoginPage(driver).abrir()
                .iniciarSesion(USUARIO_VALIDO, CLAVE_VALIDA);

        // Act
        LoginPage login = zonaSegura.cerrarSesion();

        // Assert
        String mensaje = login.mensaje();
        assertTrue(mensaje.contains("You logged out of the secure area!"),
                "Mensaje real: " + mensaje);
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // Arrange
        CheckboxesPage casillas = new CheckboxesPage(driver).abrir();
        assertEquals(2, casillas.cantidadDeCasillas());
        assertFalse(casillas.estaMarcada(0), "La 1.a casilla debe iniciar sin marcar");
        assertTrue(casillas.estaMarcada(1), "La 2.a casilla debe iniciar marcada");

        // Act
        casillas.alternar(0);

        // Assert
        assertTrue(casillas.estaMarcada(0), "La 1.a casilla debe quedar marcada");
        assertTrue(casillas.estaMarcada(1), "La 2.a casilla debe seguir marcada");
    }

    @Test
    void t6_listaDesplegable() {
        // Arrange
        DropdownPage lista = new DropdownPage(driver).abrir();

        // Act
        lista.seleccionar("Option 2");

        // Assert
        assertEquals("Option 2", lista.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // Arrange
        AddRemovePage pagina = new AddRemovePage(driver).abrir();

        // Act
        pagina.agregar().agregar().agregar();
        assertEquals(3, pagina.cantidadDeBotonesEliminar(), "Deben existir 3 botones Delete");
        pagina.eliminarPrimero();

        // Assert
        assertEquals(2, pagina.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // En TablePage ordene por "Last Name" (una vez, ascendente) y compruebe que
        // el apellido de la primera fila es "Bach" y el de la ultima es "Smith".
        fail("Pendiente: T8");
    }

    // ---------------- Bono ----------------

    @Test
    void t9_bono_correoDeUnaPersona() {
        // BONO: agregue a TablePage un metodo nuevo, por ejemplo correoPorApellido(String apellido),
        // que recorra las filas y devuelva el correo. Compruebe que el correo de "Doe" es jdoe@hotmail.com.
        // La prueba NO debe contener localizadores.
        fail("Pendiente: T9 (bono)");
    }
}
