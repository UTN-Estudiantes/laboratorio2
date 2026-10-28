package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Casillas de verificacion (/checkboxes). Las casillas se numeran desde 0. Page Factory. */
public class CheckboxesPage {

    public static final String URL = "https://the-internet.herokuapp.com/checkboxes";

    private final WebDriver driver;

    @FindBy(css = "#checkboxes input[type='checkbox']")
    private List<WebElement> casillas;

    public CheckboxesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CheckboxesPage abrir() {
        driver.get(URL);
        Visual.pausa();
        return this;
    }

    public int cantidadDeCasillas() {
        return casillas.size();
    }

    public boolean estaMarcada(int indice) {
        return casillas.get(indice).isSelected();
    }

    /** Hace clic en la casilla: si estaba marcada la desmarca y viceversa. */
    public CheckboxesPage alternar(int indice) {
        casillas.get(indice).click();
        Visual.pausa();
        return this;
    }
}
