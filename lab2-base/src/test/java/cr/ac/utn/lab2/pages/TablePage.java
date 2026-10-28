package cr.ac.utn.lab2.pages;

import cr.ac.utn.lab2.base.Visual;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Tabla ordenable (/tables), primera tabla (id table1).
 * Columnas, de izquierda a derecha: Last Name, First Name, Email, Due, Web
 * Site, Action.
 * Page Factory.
 */
public class TablePage {

    public static final String URL = "https://the-internet.herokuapp.com/tables";

    private final WebDriver driver;

    @FindBy(css = "#table1 thead th")
    private List<WebElement> encabezados;

    @FindBy(css = "#table1 tbody tr")
    private List<WebElement> filas;

    public TablePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public TablePage abrir() {
        driver.get(URL);
        Visual.pausa();
        return this;
    }

    public int cantidadDeFilas() {
        return filas.size();
    }

    /**
     * Ordena la tabla haciendo clic en el encabezado con ese texto, por ejemplo
     * "Last Name".
     */
    public TablePage ordenarPor(String nombreColumna) {
        for (WebElement th : encabezados) {
            if (th.getText().trim().equals(nombreColumna)) {
                th.click();
                Visual.pausa();
                return this;
            }
        }
        throw new IllegalArgumentException("No existe la columna: " + nombreColumna);
    }

    /** Texto de una celda; fila y columna se numeran desde 0. */
    public String celda(int fila, int columna) {
        List<WebElement> celdas = filas.get(fila).findElements(By.tagName("td"));
        return celdas.get(columna).getText().trim();
    }

    // metodo para el test de mas.
    public String correoPorApellido(String apellido) {
        for (int i = 0; i < cantidadDeFilas(); i++) {
            if (celda(i, 0).equals(apellido)) {
                return celda(i, 2);
            }
        }
        throw new java.util.NoSuchElementException("No se encontró una persona con el apellido: " + apellido);
    }
}
