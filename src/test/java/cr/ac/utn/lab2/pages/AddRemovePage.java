package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/** Agregar y quitar elementos (/add_remove_elements/). Page Factory. */
public class AddRemovePage {

    public static final String URL = "https://the-internet.herokuapp.com/add_remove_elements/";

    private final WebDriver driver;

    @FindBy(css = "button[onclick='addElement()']")
    private WebElement botonAgregar;

    // Puede estar vacia (no hay botones Delete al inicio): por eso es una lista.
    @FindBy(css = "#elements button.added-manually")
    private List<WebElement> botonesEliminar;

    public AddRemovePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AddRemovePage abrir() {
        driver.get(URL);
        Visual.pausa();
        return this;
    }

    public AddRemovePage agregar() {
        botonAgregar.click();
        Visual.pausa();
        return this;
    }

    public AddRemovePage eliminarPrimero() {
        botonesEliminar.get(0).click();
        Visual.pausa();
        return this;
    }

    public int cantidadDeBotonesEliminar() {
        return botonesEliminar.size();
    }
}
