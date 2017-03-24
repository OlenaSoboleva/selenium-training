package task8;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class Main extends BasePage {
@Test
public void test()throws Throwable {
    LoginTest();
    ListDuggs();
}

    public void LoginTest() throws Throwable {
        driver.navigate().to("http://localhost/litecart/en/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("nav#site-menu")));
        assertTrue(isElementPresent(driver, By.cssSelector("nav#site-menu")));
    }

    public void ListDuggs() throws Throwable {
        List<WebElement> CatalogList = driver.findElements(By.cssSelector("div.middle [class=link]"));
        for(int i = 0 ; i < CatalogList.size(); i++){
            Assert.assertEquals(1, CatalogList.get(i).findElements(By.cssSelector("div > div[class*=sticker]")).size());
        }
    }
}
