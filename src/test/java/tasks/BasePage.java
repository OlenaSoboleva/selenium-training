package tasks;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
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

    public static ThreadLocal<EventFiringWebDriver> tlDriver;
//    public static ThreadLocal<WebDriver> tlDriver;
    static {
        tlDriver = new ThreadLocal<>();
    }
//    public WebDriver driver;
    public EventFiringWebDriver driver;
    public WebDriverWait wait;
    public BrowserMobProxy proxy;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }
    }

    @Before
    public void start() throws MalformedURLException {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
//        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
//        caps.setCapability("platform", "Windows 8.1");
//        caps.setCapability("version", "11.0");
//        caps.setCapability(FirefoxDriver.MARIONETTE, false);
//        driver = new FirefoxDriver(new FirefoxOptions().setLegacy(true));
        driver = new EventFiringWebDriver(new ChromeDriver(capabilities));
//          driver = new ChromeDriver();
        driver.register(new MyListener());
//        new RemoteWebDriver(new URL("http://192.168.4.103:4444/wd/hub"),//DesiredCapabilities.firefox());
//                DesiredCapabilities.internetExplorer());
//        new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"),//DesiredCapabilities.firefox());
//                DesiredCapabilities.internetExplorer());
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

    public void getBrowserLogs() {
        for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
            System.out.println(l);
            assertTrue(!l.getMessage().toString().isEmpty());
        }
        assertTrue(driver.manage().logs().get("browser").getAll().isEmpty());
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
