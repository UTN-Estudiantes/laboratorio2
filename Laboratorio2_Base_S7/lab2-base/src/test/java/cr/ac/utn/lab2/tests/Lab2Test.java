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
import cr.ac.utn.lab2.pages.TablePage;
import org.junit.jupiter.api.Test;

/**
 * LABORATORIO 2 - PRUEBAS AUTOMATIZADAS CON POM
 *
 * Las paginas (paquete cr.ac.utn.lab2.pages) ya estan hechas con Page Factory.
 * Su trabajo: construir las pruebas usando ESAS paginas, siguiendo el patron
 * POM.
 *
 * Reglas:
 * - Ninguna prueba contiene By ni @FindBy: solo llama metodos de las paginas.
 * - Las aserciones (assertEquals, assertTrue...) van en la prueba, no en la
 * pagina.
 * - Cada prueba es independiente (BaseTest abre un Chrome limpio para cada
 * una).
 * - Si necesita un metodo que la pagina no tiene, agreguelo a la pagina (solo
 * en el bono es obligatorio).
 *
 * Datos del sitio https://the-internet.herokuapp.com :
 * usuario valido: tomsmith clave valida: SuperSecretPassword!
 *
 * Ejecutar: mvn test (al inicio todo falla: es lo esperado)
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
        assertTrue(zonaSegura.mensaje().contains("You logged into a secure area!"),
                "Mensaje real: " + zonaSegura.mensaje());
        assertTrue(zonaSegura.urlActual().endsWith("/secure"),
                "URL real: " + zonaSegura.urlActual());
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        // Arrange
        LoginPage login = new LoginPage(driver).abrir();

        // Act
        login.iniciarSesionFallido("usuarioInexistente", CLAVE_VALIDA);

        // Assert
        assertTrue(login.mensaje().contains("Your username is invalid!"),
                "Mensaje real: " + login.mensaje());
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        // Arrange
        LoginPage login = new LoginPage(driver).abrir();

        // Act
        login.iniciarSesionFallido(USUARIO_VALIDO, "claveIncorrecta");

        // Assert
        assertTrue(login.mensaje().contains("Your password is invalid!"),
                "Mensaje real: " + login.mensaje());
    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        // Arrange
        SecureAreaPage zonaSegura = new LoginPage(driver).abrir()
                .iniciarSesion(USUARIO_VALIDO, CLAVE_VALIDA);

        // Act
        LoginPage login = zonaSegura.cerrarSesion();

        // Assert
        assertTrue(login.mensaje().contains("You logged out of the secure area!"),
                "Mensaje real: " + login.mensaje());
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // Arrange
        CheckboxesPage casillas = new CheckboxesPage(driver).abrir();

        // Assert del estado inicial
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
        pagina.eliminarPrimero();

        // Assert
        assertEquals(2, pagina.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // Arrange
        TablePage tabla = new TablePage(driver).abrir();

        // act
        tabla.ordenarPor("Last Name");

        // assert
        int ultimaFila = tabla.cantidadDeFilas() - 1;
        assertEquals("Bach", tabla.celda(0, 0));
        assertEquals("Smith", tabla.celda(ultimaFila, 0));

    }

    @Test
    void t9_bono_correoDeUnaPersona() {
        // Arrange
        TablePage tabla = new TablePage(driver).abrir();

        // Act
        String correo = tabla.correoPorApellido("Doe");

        // Assert
        assertEquals("jdoe@hotmail.com", correo);
    }

}