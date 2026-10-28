package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Pagina de login (/login). Hecha con Page Factory: NO se modifica en el
 * laboratorio.
 */
public class LoginPage {

    public static final String URL = "https://the-internet.herokuapp.com/login";

    private final WebDriver driver;

    @FindBy(id = "username")
    private WebElement campoUsuario;

    @FindBy(id = "password")
    private WebElement campoClave;

    @FindBy(css = "button.radius")
    private WebElement botonLogin;

    @FindBy(id = "flash")
    private WebElement mensaje;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage abrir() {
        driver.get(URL);
        Visual.pausa();
        return this;
    }

    /** Login con credenciales validas: lleva a la zona segura. */
    public SecureAreaPage iniciarSesion(String usuario, String clave) {
        escribirYEnviar(usuario, clave);
        return new SecureAreaPage(driver);
    }

    /** Login que se espera que falle: se permanece en la pagina de login. */
    public LoginPage iniciarSesionFallido(String usuario, String clave) {
        escribirYEnviar(usuario, clave);
        return this;
    }

    /** Texto del mensaje (flash) sin el simbolo de cierre. */
    public String mensaje() {
        return mensaje.getText().replace("×", "").trim();
    }

    private void escribirYEnviar(String usuario, String clave) {
        campoUsuario.sendKeys(usuario);
        campoClave.sendKeys(clave);
        Visual.pausa();
        botonLogin.click();
        Visual.pausa();
    }
}