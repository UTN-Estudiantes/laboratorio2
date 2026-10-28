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
 * Reglas: - Ninguna prueba contiene By ni @FindBy: solo llama metodos de las
 * paginas. - Las aserciones (assertEquals, assertTrue...) van en la prueba, no
 * en la pagina. - Cada prueba es independiente (BaseTest abre un Chrome limpio
 * para cada una). - Si necesita un metodo que la pagina no tiene, agreguelo a
 * la pagina (solo en el bono es obligatorio).
 *
 * Datos del sitio https://the-internet.herokuapp.com : usuario valido: tomsmith
 * clave valida: SuperSecretPassword!
 *
 * Ejecutar: mvn test (al inicio todo falla: es lo esperado)
 */
class Lab2Test extends BaseTest {

    // ---------------- Login (LoginPage, SecureAreaPage) ----------------
    @Test
    void t1_loginValidoLlevaALaZonaSegura() {
        LoginPage login = new LoginPage(driver);
        login.abrir();

        SecureAreaPage secure = login.iniciarSesion("tomsmith", "SuperSecretPassword!");

        assertEquals("Secure Area", secure.titulo());
        assertTrue(secure.mensaje().contains("You logged into a secure area!"));
        assertTrue(secure.urlActual().endsWith("/secure"));
    }

    @Test
    void t2_usuarioInvalidoMuestraError() {
        LoginPage login = new LoginPage(driver);
        login.abrir();

        login.iniciarSesion("usuarioIncorrecto", "SuperSecretPassword!");

        assertTrue(login.mensaje().contains("Your username is invalid!"));
    }

    @Test
    void t3_claveInvalidaMuestraError() {
    // Arrange: abrir la pagina de inicio de sesion.
    LoginPage login = new LoginPage(driver);
    login.abrir();

    // Act: ingresar el usuario valido con una clave incorrecta.
    login.iniciarSesionFallido("tomsmith", "claveIncorrecta");

    // Assert: comprobar el mensaje de contraseña invalida.
    assertTrue(
        login.mensaje().contains("Your password is invalid!"),
        "Debe aparecer el mensaje de contraseña invalida."
    );
}

    @Test
    void t4_cerrarSesionVuelveAlLogin() {
    // Arrange: abrir el login e iniciar sesion correctamente.
    LoginPage login = new LoginPage(driver);
    login.abrir();

    SecureAreaPage zonaSegura = login.iniciarSesion(
        "tomsmith",
        "SuperSecretPassword!"
    );

    // Act: cerrar sesion y obtener la pagina de login.
    LoginPage loginDespuesDeSalir = zonaSegura.cerrarSesion();

    // Assert: comprobar el mensaje de salida exitosa.
    assertTrue(
        loginDespuesDeSalir.mensaje().contains(
            "You logged out of the secure area!"
        ),
        "Debe aparecer el mensaje de cierre de sesion exitoso."
    );
}

    // ---------------- Otras pantallas ----------------
    @Test
    void t5_casillasDeVerificacion() {
        //Arrange

        CheckboxesPage pagina = new CheckboxesPage(driver).abrir();

        //Assert
        assertEquals(2, pagina.cantidadDeCasillas());
        assertFalse(pagina.estaMarcada(0));
        assertTrue(pagina.estaMarcada(1));

        //Act
        pagina.alternar(0);
        assertTrue(pagina.estaMarcada(0));
        assertTrue(pagina.estaMarcada(1));

    }

    @Test
    void t6_listaDesplegable() {
        // Arrange + Act: abrir la página y elegir "Option 2"
        DropdownPage pagina = new DropdownPage(driver)
                .abrir()
                .seleccionar("Option 2");

        // Assert: la opción seleccionada es "Option 2"
        assertEquals("Option 2", pagina.opcionSeleccionada());
    }

    @Test
    void t7_agregarYQuitarElementos() {
        // Arrange: abrir la pagina de agregar/quitar elementos
        AddRemovePage pagina = new AddRemovePage(driver).abrir();

        // Act: agregar 3 elementos y registrar cuantos hay, luego eliminar 1
        pagina.agregar().agregar().agregar();
        int botonesTrasAgregar = pagina.cantidadDeBotonesEliminar();
        pagina.eliminarPrimero();
        int botonesTrasEliminar = pagina.cantidadDeBotonesEliminar();

        // Assert
        assertEquals(3, botonesTrasAgregar, "Tras agregar 3 elementos debe haber 3 botones Delete");
        assertEquals(2, botonesTrasEliminar, "Tras eliminar 1 elemento deben quedar 2 botones Delete");
    }

    @Test
    void t8_ordenarTablaPorApellido() {
        // Arrange: abrir la tabla; la columna "Last Name" es la 0
        final int columnaApellido = 0;
        TablePage tabla = new TablePage(driver).abrir();

        // Act: ordenar una vez por Last Name (ascendente)
        tabla.ordenarPor("Last Name");
        int ultimaFila = tabla.cantidadDeFilas() - 1;

        // Assert
        assertEquals("Bach", tabla.celda(0, columnaApellido), "La primera fila debe ser Bach");
        assertEquals("Smith", tabla.celda(ultimaFila, columnaApellido), "La ultima fila debe ser Smith");
    }

    // ---------------- Bono ----------------
    @Test
    void t9_bono_correoDeUnaPersona() {
        // Arrange: abrir la tabla
        TablePage tabla = new TablePage(driver).abrir();

        // Act: obtener el correo de la persona con apellido Doe
        String correo = tabla.correoPorApellido("Doe");

        // Assert
        assertEquals("jdoe@hotmail.com", correo, "El correo de Doe debe ser jdoe@hotmail.com");
    }
}
