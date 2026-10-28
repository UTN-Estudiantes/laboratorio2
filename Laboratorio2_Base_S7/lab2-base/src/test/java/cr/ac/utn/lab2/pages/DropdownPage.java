package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/** Lista desplegable (/dropdown). Page Factory. */
public class DropdownPage {

    public static final String URL = "https://the-internet.herokuapp.com/dropdown";

    private final WebDriver driver;

    @FindBy(id = "dropdown")
    private WebElement lista;

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public DropdownPage abrir() {
        driver.get(URL);
        Visual.pausa();
        return this;
    }

    public DropdownPage seleccionar(String textoVisible) {
        new Select(lista).selectByVisibleText(textoVisible);
        Visual.pausa();
        return this;
    }

    public String opcionSeleccionada() {
        return new Select(lista).getFirstSelectedOption().getText().trim();
    }
}
