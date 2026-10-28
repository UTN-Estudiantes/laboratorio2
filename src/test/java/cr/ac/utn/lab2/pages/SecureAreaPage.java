package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Zona segura (/secure), a la que se llega despues de iniciar sesion. Page Factory. */
public class SecureAreaPage {

    private final WebDriver driver;

    @FindBy(css = "h2")
    private WebElement titulo;

    @FindBy(id = "flash")
    private WebElement mensaje;

    @FindBy(linkText = "Logout")
    private WebElement enlaceSalir;

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String titulo() {
        return titulo.getText().trim();
    }

    public String mensaje() {
        return mensaje.getText().replace("×", "").trim();
    }

    public String urlActual() {
        return driver.getCurrentUrl();
    }

    public LoginPage cerrarSesion() {
        enlaceSalir.click();
        Visual.pausa();
        return new LoginPage(driver);
    }
}
