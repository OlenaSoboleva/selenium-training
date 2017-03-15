import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FirstTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new InternetExplorerDriver();
    }

    @Test
    public void firstTest() throws Throwable {
        String stringsearch = "Apple";
        driver.navigate().to("http://rozetka.com.ua");
        driver.findElement(By.cssSelector("#rz-search input[placeholder='Поиск']")).sendKeys(stringsearch);
        driver.findElement(By.name("rz-search-button")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
