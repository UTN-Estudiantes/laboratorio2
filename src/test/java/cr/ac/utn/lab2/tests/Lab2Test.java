package cr.ac.utn.lab2.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // ---------------- Login (LoginPage, SecureAreaPage) ----------------

    @Test
    void t1_loginValidoLlevaALaZonaSegura() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        SecureAreaPage zonaSegura = loginPage.iniciarSesion("tomsmith", "SuperSecretPassword!");

        assertEquals("Secure Area", zonaSegura.titulo());
        assertTrue(zonaSegura.mensaje().contains("You logged into a secure area!"));
        assertTrue(zonaSegura.urlActual().endsWith("/secure"));
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        loginPage.iniciarSesionFallido("usuarioInexistente", "SuperSecretPassword!");

        assertTrue(loginPage.mensaje().contains("Your username is invalid!"));
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        loginPage.iniciarSesionFallido("tomsmith", "claveIncorrecta");

        assertTrue(loginPage.mensaje().contains("Your password is invalid!"));
    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        SecureAreaPage zonaSegura = new LoginPage(driver).abrir()
                .iniciarSesion("tomsmith", "SuperSecretPassword!");

        LoginPage loginPage = zonaSegura.cerrarSesion();

        assertTrue(loginPage.mensaje().contains("You logged out of the secure area!"));
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        CheckboxesPage casillas = new CheckboxesPage(driver).abrir();
        assertFalse(casillas.estaMarcada(0));
        assertTrue(casillas.estaMarcada(1));

        casillas.alternar(0);

        assertTrue(casillas.estaMarcada(0));
        assertTrue(casillas.estaMarcada(1));
    }

    @Test
    void t6_listaDesplegable() {
        DropdownPage lista = new DropdownPage(driver).abrir();

        lista.seleccionar("Option 2");

        assertEquals("Option 2", lista.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        AddRemovePage pagina = new AddRemovePage(driver).abrir();

        pagina.agregar().agregar().agregar();
        assertEquals(3, pagina.cantidadDeBotonesEliminar());
        pagina.eliminarPrimero();

        assertEquals(2, pagina.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        TablePage tabla = new TablePage(driver).abrir();

        tabla.ordenarPor("Last Name");

        int columnaApellido = 0;
        int ultimaFila = tabla.cantidadDeFilas() - 1;
        assertEquals("Bach", tabla.celda(0, columnaApellido));
        assertEquals("Smith", tabla.celda(ultimaFila, columnaApellido));
    }

    // ---------------- Bono ----------------

    @Test
    void t9_bono_correoDeUnaPersona() {
        TablePage tabla = new TablePage(driver).abrir();

        String correo = tabla.correoPorApellido("Doe");

        assertEquals("jdoe@hotmail.com", correo);
    }
}
