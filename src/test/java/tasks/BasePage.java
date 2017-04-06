package tasks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertTrue;

public class BasePage {
    static private String siteLink, adminLink;

    static {
        siteLink = "http://localhost/litecart";
        adminLink = "http://localhost/litecart/admin";
    }


    public static ThreadLocal<WebDriver> tlDriver;

    static {
        tlDriver = new ThreadLocal<>();
    }

    public WebDriver driver;
    public WebDriverWait wait;

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
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                }));

    }

    public boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
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

    public void loginToSiteMainPage(String locator) {
        driver.navigate().to(siteLink);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));
        assertTrue(isElementPresent(driver, By.cssSelector(locator)));
    }

    public void LoginToAdmin() throws Throwable {
        String stringinput = "admin";
        driver.navigate().to(adminLink);
        assertTrue(isElementPresent(driver, By.cssSelector("[name=username]")));
        driver.findElement(By.name("username")).sendKeys(stringinput);
        driver.findElement(By.name("password")).sendKeys(stringinput);
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='sidebar']")));

    }

    public boolean checkSortList(List list) {
        return list.stream().sorted().collect(Collectors.toList()).equals(list);
    }

    public void manipulation(List list, String link, String locator) {
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

    public List elementsToList(String locator) {
        List<WebElement> CountryList = driver.findElements(By.xpath(locator));
        List<String> all_elements_text = new ArrayList<>();
        for (int i = 0; i < CountryList.size(); i++) {
            all_elements_text.add(CountryList.get(i).getAttribute("textContent"));
        }
        return all_elements_text;
    }

    public Map<String, String> getElementProperty(String nameLocator, String regularPriceLocator,
                                                  String campaignPriceLocator) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("name", driver.findElement(By.cssSelector(nameLocator)).getAttribute("textContent"));
        hashMap.put("regularPriceDecor", driver.findElement(By.cssSelector(regularPriceLocator)).getCssValue("text-decoration"));
        hashMap.put("campaignColor", driver.findElement(By.cssSelector(campaignPriceLocator)).getCssValue("color"));
        hashMap.put("regularPriceColor", driver.findElement(By.cssSelector(regularPriceLocator)).getCssValue("color"));
        hashMap.put("campaignFontSize", driver.findElement(By.cssSelector(campaignPriceLocator)).getCssValue("font-size").replace("px", ""));
        hashMap.put("regularPriceFontSize", driver.findElement(By.cssSelector(regularPriceLocator)).getCssValue("font-size").replace("px", ""));
        hashMap.put("strong", driver.findElement(By.cssSelector(campaignPriceLocator)).getTagName());
        hashMap.put("regularPrice", driver.findElement(By.cssSelector(regularPriceLocator)).getAttribute("textContent"));
        hashMap.put("campaignPrice", driver.findElement(By.cssSelector(campaignPriceLocator)).getAttribute("textContent"));

        Assert.assertTrue(Float.parseFloat(hashMap.get("regularPriceFontSize")) < Float.parseFloat(hashMap.get("campaignFontSize")));
        Assert.assertEquals("line-through", hashMap.get("regularPriceDecor"));
        Assert.assertEquals("grey", rgb(hashMap.get("regularPriceColor")));
        Assert.assertEquals("red", rgb(hashMap.get("campaignColor")));
        Assert.assertEquals("strong", hashMap.get("strong"));

        return hashMap;
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> existingWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(existingWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }

        };
    }

    public String rgb(String color) {
        String[] numbers;
        String rgb;
        numbers = color.replace("rgba(", "").replace(")", "").split(", ");
        int r = Integer.parseInt(numbers[0]);
        int g = Integer.parseInt(numbers[1]);
        int b = Integer.parseInt(numbers[2]);

        if (r == g && g == b) {
            rgb = "grey";
        } else if (r != g && g == b) {
            rgb = "red";
        } else {
            rgb = "none";
        }
        return rgb;
    }

    @After
    public void stop() {

    }
}
