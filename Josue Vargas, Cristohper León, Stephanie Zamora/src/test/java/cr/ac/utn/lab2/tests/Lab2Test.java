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

class Lab2Test extends BaseTest {

    // ---------------- Login (LoginPage, SecureAreaPage) ----------------

    // T1: Login valido lleva a la zona segura
    @Test
    void t1_loginValidoLlevaALaZonaSegura() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        SecureAreaPage secureAreaPage = loginPage.iniciarSesion("tomsmith", "SuperSecretPassword!");

        assertEquals("Secure Area", secureAreaPage.titulo());
        assertTrue(secureAreaPage.mensaje().contains("You logged into a secure area!"));
        assertTrue(secureAreaPage.urlActual().endsWith("/secure"));
    }

    // T2: Usuario invalido muestra error
    @Test
    void t2_usuarioInvalidoMuestraError() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        loginPage.iniciarSesionFallido("usuario_inexistente", "SuperSecretPassword!");

        assertTrue(loginPage.mensaje().contains("Your username is invalid!"));
    }

    // T3: Clave invalida muestra error
    @Test
    void t3_claveInvalidaMuestraError() {
        LoginPage loginPage = new LoginPage(driver).abrir();

        loginPage.iniciarSesionFallido("tomsmith", "ClaveIncorrecta123");

        assertTrue(loginPage.mensaje().contains("Your password is invalid!"));
    }

    // T4: Cerrar sesion vuelve al login
    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        SecureAreaPage secureAreaPage = new LoginPage(driver)
                .abrir()
                .iniciarSesion("tomsmith", "SuperSecretPassword!");

        LoginPage loginPage = secureAreaPage.cerrarSesion();

        assertTrue(loginPage.mensaje().contains("You logged out of the secure area!"));
    }

    // ---------------- Otras pantallas ----------------

    // T5: Casillas de verificacion
    @Test
    void t5_casillasDeVerificacion() {
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver).abrir();
        assertFalse(checkboxesPage.estaMarcada(0));
        assertTrue(checkboxesPage.estaMarcada(1));

        checkboxesPage.alternar(0);

        assertTrue(checkboxesPage.estaMarcada(0));
        assertTrue(checkboxesPage.estaMarcada(1));
    }

    // T6: Lista desplegable
    @Test
    void t6_listaDesplegable() {
        DropdownPage dropdownPage = new DropdownPage(driver).abrir();

        dropdownPage.seleccionar("Option 2");

        assertEquals("Option 2", dropdownPage.opcionSeleccionada());
    }

    // T7: Agregar y quitar elementos
    @Test
    void t7_agregarYQuitarElementos() {
        AddRemovePage addRemovePage = new AddRemovePage(driver).abrir();

        addRemovePage.agregar()
                .agregar()
                .agregar()
                .eliminarPrimero();

        assertEquals(2, addRemovePage.cantidadDeBotonesEliminar());
    }

    // T8: Ordenar tabla por apellido
    @Test
    void t8_ordenarTablaPorApellido() {
        TablePage tablePage = new TablePage(driver).abrir();

        tablePage.ordenarPor("Last Name");

        int ultimaFila = tablePage.cantidadDeFilas() - 1;
        assertEquals("Bach", tablePage.celda(0, 0));
        assertEquals("Smith", tablePage.celda(ultimaFila, 0));
    }

    // ---------------- Bono ----------------

    // T9 (Bono): Correo de una persona por apellido
    @Test
    void t9_bono_correoDeUnaPersona() {
        TablePage tablePage = new TablePage(driver).abrir();

        String correo = tablePage.correoPorApellido("Doe");

        assertEquals("jdoe@hotmail.com", correo);
    }
}
