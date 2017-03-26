package task9;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertTrue;


public class BasePage {

    public static ThreadLocal<WebDriver> tlDriver;

    static {
        tlDriver = new ThreadLocal<>();
    }

    public WebDriver driver;
    public WebDriverWait wait;

    public boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    public boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public void loginToSite(String link, String locator) {
        driver.navigate().to(link);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));
        assertTrue(isElementPresent(driver, By.cssSelector(locator)));
    }

    public void LoginToAdmin() throws Throwable {
        String stringinput = "admin";
        driver.navigate().to("http://localhost/litecart/admin");
        assertTrue(isElementPresent(driver, By.cssSelector("[name=username]")));
        driver.findElement(By.name("username")).sendKeys(stringinput);
        driver.findElement(By.name("password")).sendKeys(stringinput);
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='sidebar']")));

    }


    public List elementsToList(String locator) {
        List<WebElement> CountryList = driver.findElements(By.xpath(locator));
        List<String> all_elements_text = new ArrayList<>();
        for (int i = 0; i < CountryList.size(); i++) {
               all_elements_text.add(CountryList.get(i).getAttribute("textContent"));
        }
        return all_elements_text;
    }

    public boolean checkSortList(List list) {
        return list.stream().sorted().collect(Collectors.toList()).equals(list);
    }

    public void manipulation(List list,String link,String locator){
        for (int i = 0; i < list.size(); i++) {
            driver.findElement((By.xpath(list.get(i).toString()))).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name=name]")));
            if (checkSortList(elementsToList("locator"))) {
                System.out.println("State " + list.get(i).toString() + "sorted= true");
            } else {
                System.out.println("State " + list.get(i).toString() + "sorted= false");
            }
            driver.navigate().to(link);
        }

    }


    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.MARIONETTE, false);
        driver = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        tlDriver.set(driver);
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                }));

    }

    @After
    public void stop() {

    }


}
