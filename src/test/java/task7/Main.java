package task7;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class Main extends BasePage {

    @Test
    public void Menu() throws Throwable {
        LoginTest();


        Lists();
        Reports();
        Modules();
        Settings();
    }

    public void LoginTest() throws Throwable {
        String stringinput = "admin";
        driver.navigate().to("http://localhost/litecart/admin");
        assertTrue(isElementPresent(driver, By.cssSelector("[name=username]")));
        driver.findElement(By.name("username")).sendKeys(stringinput);
        driver.findElement(By.name("password")).sendKeys(stringinput);
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='sidebar']")));

    }

    public void Modules() throws Throwable {
        driver.findElement(By.xpath("//a[contains(.,'Modules')]")).click();
        assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'Job Modules')]")));
        assertTrue(areElementsPresent(driver, By.cssSelector("ul.docs span.name")));
        List<WebElement> CatalogList = driver.findElements(By.cssSelector("ul.docs span.name"));
        String xpathstringMenu;
        String xpathstringh1;
        List<String> all_elements_text = new ArrayList<>();

        for (int i = 0; i < CatalogList.size(); i++) {

            all_elements_text.add(CatalogList.get(i).getText());
        }

        driver.findElement(By.xpath("//ul[@class='docs']//a[contains(.,'Background Jobs')]")).click();
        assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'Job Modules')]")));

        for (int i = 1; i < all_elements_text.size(); i++) {
            xpathstringMenu = "//ul[@class='docs']//a[contains(.,'" + all_elements_text.get(i).toString() + "')]";
            xpathstringh1 = "//h1[contains(.,'" + all_elements_text.get(i).toString() + " Modules" + "')]";
            driver.findElement(By.xpath(xpathstringMenu)).click();
            assertTrue(isElementPresent(driver, By.xpath(xpathstringh1)));
        }
    }

    public void Settings() throws Throwable {
        driver.findElement(By.xpath("//a[contains(.,'Settings')]")).click();
        assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'Settings')]")));
        assertTrue(areElementsPresent(driver, By.cssSelector("ul.docs span.name")));
        List<WebElement> CatalogList = driver.findElements(By.cssSelector("ul.docs span.name"));
        String xpathstringMenu;
        String xpathstringh1 = "//h1[contains(.,'Settings')]";
        List<String> all_elements_text = new ArrayList<>();

        for (int i = 0; i < CatalogList.size(); i++) {

            all_elements_text.add(CatalogList.get(i).getText());
        }

        driver.findElement(By.xpath("//ul[@class='docs']//a[contains(.,'Store Info')]")).click();
        assertTrue(isElementPresent(driver, By.xpath(xpathstringh1)));

        for (int i = 1; i < all_elements_text.size(); i++) {
            xpathstringMenu = "//ul[@class='docs']//a[contains(.,'" + all_elements_text.get(i).toString() + "')]";
            driver.findElement(By.xpath(xpathstringMenu)).click();
            assertTrue(isElementPresent(driver, By.xpath(xpathstringh1)));
        }
    }

    public void Reports() throws Throwable {
        driver.findElement(By.xpath("//a[contains(.,'Reports')]")).click();
        assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'Monthly Sales')]")));
        assertTrue(areElementsPresent(driver, By.cssSelector("ul.docs span.name")));
        List<WebElement> CatalogList = driver.findElements(By.cssSelector("ul.docs span.name"));
        String xpathstringMenu;
        String xpathstringh1;
        List<String> all_elements_text = new ArrayList<>();

        for (int i = 0; i < CatalogList.size(); i++) {

            all_elements_text.add(CatalogList.get(i).getText());
        }

        for (int i = 0; i < all_elements_text.size(); i++) {
            xpathstringMenu = "//ul[@class='docs']//a[contains(.,'" + all_elements_text.get(i).toString() + "')]";
            xpathstringh1 = "//h1[contains(.,'" + all_elements_text.get(i).toString() + "')]";
            driver.findElement(By.xpath(xpathstringMenu)).click();
            assertTrue(isElementPresent(driver, By.xpath(xpathstringh1)));
        }
    }

    public void Lists() throws Throwable {

        List<WebElement> MenuLinks = driver.findElements(By.cssSelector("li#app-"));
        List<String> all_elements = new ArrayList<>();
        ArrayList<String> ignore_elements = new ArrayList<String>(Arrays.asList("Settings", "Modules", "Reports"));
        for (int n = 0; n < MenuLinks.size(); n++) {
            all_elements.add(MenuLinks.get(n).getText());
        }
        Iterator<String> iter = all_elements.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            for (int i = 0; i < ignore_elements.size(); i++) {
                if (s.equals(ignore_elements.get(i))) {
                    iter.remove();
                }
            }
        }

        for (int k = 0; k < all_elements.size(); k++) {

            driver.findElement(By.xpath("//a[contains(.,'" + all_elements.get(k).toString() + "')]")).click();
            if (areElementsPresent(driver, By.cssSelector("ul.docs span.name"))) {
                List<WebElement> CatalogList = driver.findElements(By.cssSelector("ul.docs span.name"));
                String xpathstringMenu;
                String xpathstringh1;
                List<String> all_elements_text = new ArrayList<>();
                for (int i = 0; i < CatalogList.size(); i++) {
                    all_elements_text.add(CatalogList.get(i).getText());
                }
                for (int j = 0; j < all_elements_text.size(); j++) {
                    xpathstringMenu = "//ul[@class='docs']//a[contains(.,'" + all_elements_text.get(j).toString() + "')]";
                    xpathstringh1 = "//h1[contains(.,'" + all_elements_text.get(j).toString() + "')]";
                    driver.findElement(By.xpath(xpathstringMenu)).click();
                    assertTrue(isElementPresent(driver, By.xpath(xpathstringh1)));
                }
            } else
                assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'" + all_elements.get(k).toString() + "')]")));
        }
    }
}