package tasks;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

public class task10 extends BasePage {
    public String mainlink = "http://localhost/litecart/en/";

    @Test
    public void test() {
        login();
        checkProperties();

    }

    public void login() {
        loginToSite(mainlink, "div#box-campaigns.box [class=link]");
    }

    public void checkProperties() {
        Map<String, String> PropertyMain =
                getElementProperty("div#box-campaigns.box [class=link] div.name",
                        "div#box-campaigns.box [class=link] s.regular-price",
                        "div#box-campaigns.box [class=link] strong.campaign-price"
                );

        driver.findElement(By.cssSelector("div#box-campaigns.box [class=link]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("td.quantity")));

        Map<String, String> PropertyProduct =
                getElementProperty("h1.title",
                        "s.regular-price",
                        "strong.campaign-price");

        Assert.assertEquals(PropertyMain.get("name"), PropertyProduct.get("name"));
        Assert.assertEquals(PropertyMain.get("regularPrice"), PropertyProduct.get("regularPrice"));
        Assert.assertEquals(PropertyMain.get("campaignPrice"), PropertyProduct.get("campaignPrice"));
    }

}
