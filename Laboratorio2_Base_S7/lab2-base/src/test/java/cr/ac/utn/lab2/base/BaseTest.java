package cr.ac.utn.lab2.base;

import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Clase base: abre Chrome antes de cada prueba y lo cierra al terminar.
 * Las pruebas de este laboratorio usan el sitio publico https://the-internet.herokuapp.com
 * (sitio de practica con pantallas tipicas: login, casillas, listas, tablas).
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void abrirNavegador() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--window-size=1280,900");
        // Evita las ventanas de Chrome sobre guardar/revisar contrasenas, que tapan la pagina.
        opciones.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        if (Boolean.getBoolean("headless")) {
            opciones.addArguments("--headless=new");
        }
        driver = new ChromeDriver(opciones);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
