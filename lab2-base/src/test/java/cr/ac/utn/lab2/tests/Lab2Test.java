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
        // Arrange
        LoginPage loginPage = new LoginPage(driver).abrir();

        // Act
        SecureAreaPage secureArea = loginPage.iniciarSesion("tomsmith", "SuperSecretPassword!");

        // Assert
        assertEquals("Secure Area", secureArea.titulo());
        assertTrue(secureArea.mensaje().contains("You logged into a secure area!"));
        assertTrue(secureArea.urlActual().endsWith("/secure"));
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver).abrir();

        // Act
        loginPage.iniciarSesionFallido("usuario_falso", "SuperSecretPassword!");

        // Assert
        assertTrue(loginPage.mensaje().contains("Your username is invalid!"));
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver).abrir();

        // Act
        loginPage.iniciarSesionFallido("tomsmith", "clave_incorrecta");

        // Assert
        assertTrue(loginPage.mensaje().contains("Your password is invalid!"));
    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        // Arrange
        LoginPage loginPage = new LoginPage(driver).abrir();
        SecureAreaPage secureArea = loginPage.iniciarSesion("tomsmith", "SuperSecretPassword!");

        // Act
        LoginPage loginPostLogout = secureArea.cerrarSesion();

        // Assert
        assertTrue(loginPostLogout.mensaje().contains("You logged out of the secure area!"));
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // Arrange
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver).abrir();
        assertFalse(checkboxesPage.estaMarcada(0));
        assertTrue(checkboxesPage.estaMarcada(1));

        // Act
        checkboxesPage.alternar(0);

        // Assert
        assertTrue(checkboxesPage.estaMarcada(0));
        assertTrue(checkboxesPage.estaMarcada(1));
    }

    @Test
    void t6_listaDesplegable() {
        // Arrange
        DropdownPage dropdownPage = new DropdownPage(driver).abrir();

        // Act
        dropdownPage.seleccionar("Option 2");

        // Assert
        assertEquals("Option 2", dropdownPage.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // Arrange
        AddRemovePage addRemovePage = new AddRemovePage(driver).abrir();

        // Act
        addRemovePage.agregar()
                     .agregar()
                     .agregar();
        addRemovePage.eliminarPrimero();

        // Assert
        assertEquals(2, addRemovePage.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // Arrange
        TablePage tablePage = new TablePage(driver).abrir();

        // Act
        tablePage.ordenarPor("Last Name");

        // Assert
        int ultimaFila = tablePage.cantidadDeFilas() - 1;
        assertEquals("Bach", tablePage.celda(0, 0));
        assertEquals("Smith", tablePage.celda(ultimaFila, 0));
    }

    // ---------------- Bono ----------------

    @Test
    void t9_bono_correoDeUnaPersona() {
        // Arrange
        TablePage tablePage = new TablePage(driver).abrir();

        // Act
        String correo = tablePage.correoPorApellido("Doe");

        // Assert
        assertEquals("jdoe@hotmail.com", correo);
    }
}
