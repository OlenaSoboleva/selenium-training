package task8;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class Main extends BasePage {
@Test
public void stickerTest()throws Throwable {
    loginTest();
    listDuggs();
}

    public void loginTest() throws Throwable {
        driver.navigate().to("http://localhost/litecart/en/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("nav#site-menu")));
        assertTrue(isElementPresent(driver, By.cssSelector("nav#site-menu")));
    }

    public void listDuggs() throws Throwable {
        List<WebElement> CatalogList = driver.findElements(By.cssSelector("div.middle [class=link]"));
        for(int i = 0 ; i < CatalogList.size(); i++){
            assertNotNull(CatalogList.get(i).findElement(By.cssSelector("div > div[class*=sticker]")));
        }
    }
}
