package cr.ac.utn.lab2.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cr.ac.utn.lab2.base.BaseTest;
import cr.ac.utn.lab2.pages.*;
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
 * Ejecutar: mvn test
 */
class Lab2Test extends BaseTest {

    // ---------------- Login (LoginPage, SecureAreaPage) ----------------

    @Test
    void t1_loginValidoLlevaALaZonaSegura() {
        // Arrange: abrimos la pagina de login
        LoginPage login = new LoginPage(driver).abrir();

        // Act: iniciamos sesion con el usuario y la clave validos
        SecureAreaPage segura = login.iniciarSesion("tomsmith", "SuperSecretPassword!");

        // Assert: revisamos el titulo, el mensaje de exito y que la URL termine en
        // /secure
        assertEquals("Secure Area", segura.titulo());
        assertTrue(segura.mensaje().contains("You logged into a secure area!"));
        assertTrue(segura.urlActual().endsWith("/secure"));
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        // Arrange: abrimos la pagina de login
        LoginPage login = new LoginPage(driver).abrir();

        // Act: probamos con un usuario que no existe (la clave es la correcta)
        login.iniciarSesionFallido("usuarioQueNoExiste", "SuperSecretPassword!");

        // Assert: el mensaje debe decir que el usuario es invalido
        assertTrue(login.mensaje().contains("Your username is invalid!"));
    }

    @Test
    void t3_claveInvalidaMuestraError() {
        // Arrange: abrimos la pagina de login
        LoginPage login = new LoginPage(driver).abrir();

        // Act: probamos con el usuario correcto pero una clave incorrecta
        login.iniciarSesionFallido("tomsmith", "claveIncorrecta");

        // Assert: el mensaje debe decir que la clave es invalida
        assertTrue(login.mensaje().contains("Your password is invalid!"));
    }

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
        // Arrange: entramos con un login valido para tener una sesion abierta
        SecureAreaPage segura = new LoginPage(driver).abrir()
                .iniciarSesion("tomsmith", "SuperSecretPassword!");

        // Act: cerramos la sesion (nos regresa a la pagina de login)
        LoginPage login = segura.cerrarSesion();

        // Assert: aparece el mensaje de que salimos de la zona segura
        assertTrue(login.mensaje().contains("You logged out of the secure area!"));
    }

    // ---------------- Otras pantallas ----------------

    @Test
    void t5_casillasDeVerificacion() {
        // Arrange: abrimos la pagina de casillas (se numeran desde 0)
        CheckboxesPage casillas = new CheckboxesPage(driver).abrir();

        // Assert inicial: la primera viene sin marcar y la segunda marcada
        assertFalse(casillas.estaMarcada(0));
        assertTrue(casillas.estaMarcada(1));

        // Act: hacemos clic en la primera casilla para marcarla
        casillas.alternar(0);

        // Assert: ahora las dos quedan marcadas
        assertTrue(casillas.estaMarcada(0));
        assertTrue(casillas.estaMarcada(1));
    }

    @Test
    void t6_listaDesplegable() {
        // Arrange: abrimos la pagina de la lista desplegable
        DropdownPage lista = new DropdownPage(driver).abrir();

        // Act: elegimos "Option 2"
        lista.seleccionar("Option 2");

        // Assert: la opcion seleccionada es "Option 2"
        assertEquals("Option 2", lista.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // Arrange: abrimos la pagina de agregar y quitar elementos
        AddRemovePage pagina = new AddRemovePage(driver).abrir();

        // Act: agregamos 3 elementos y eliminamos 1
        pagina.agregar().agregar().agregar();
        pagina.eliminarPrimero();

        // Assert: deben quedar 2 botones Delete (3 - 1)
        assertEquals(2, pagina.cantidadDeBotonesEliminar());
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // Arrange: abrimos la pagina de la tabla
        TablePage tabla = new TablePage(driver).abrir();

        // Act: ordenamos por la columna "Last Name"
        tabla.ordenarPor("Last Name");

        // Assert: el primer apellido es Bach y el ultimo es Smith
        // (celda(fila, columna): la columna 0 es Last Name)
        assertEquals("Bach", tabla.celda(0, 0));
        assertEquals("Smith", tabla.celda(tabla.cantidadDeFilas() - 1, 0));
    }

    // ---------------- Bono ----------------

    @Test
    void t9_bono_correoDeUnaPersona() {
        // Arrange: abrimos la pagina de la tabla
        TablePage tabla = new TablePage(driver).abrir();

        // Act + Assert: usamos el metodo nuevo correoPorApellido (agregado en
        // TablePage)
        // para buscar el correo de la persona con apellido "Doe"
        assertEquals("jdoe@hotmail.com", tabla.correoPorApellido("Doe"));
    }
}