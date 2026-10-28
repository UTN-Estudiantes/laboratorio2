package cr.ac.utn.lab2.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import cr.ac.utn.lab2.base.BaseTest;
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
        // Inicie sesion, cierre sesion y compruebe que el mensaje contiene "You logged out of the secure area!".
        fail("Pendiente: T4");
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // En CheckboxesPage hay 2 casillas. Al inicio la primera NO esta marcada y la segunda SI.
        // Marque la primera y compruebe que ahora ambas estan marcadas.
        fail("Pendiente: T5");
    }

    @Test
    void t6_listaDesplegable() {
        // En DropdownPage seleccione "Option 2" y compruebe que esa es la opcion seleccionada.
        fail("Pendiente: T6");
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // En AddRemovePage agregue 3 elementos, elimine 1 y compruebe que quedan 2 botones Delete.
        fail("Pendiente: T7");
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
