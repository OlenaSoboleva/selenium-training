import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class login_task3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {

        driver = new InternetExplorerDriver();
        wait = new WebDriverWait(driver, 10);

    }

    @Test
    public void firstTest() throws Throwable {
        String stringinput = "admin";
        driver.navigate().to("http://localhost/litecart/admin");
        driver.findElement(By.name("username")).sendKeys(stringinput);
        driver.findElement(By.name("password")).sendKeys(stringinput);
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='sidebar']")));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


}
