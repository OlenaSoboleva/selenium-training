package task11;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.security.Key;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

public class task12 extends BasePage {
@Test
    public void addProductToAdmin() {
        File file = new File("src\\good.png");

        try {
            LoginToAdmin();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Date date = new Date();
        String dateString = "" + date.getTime() + "";
        String productName = "P" + dateString;
        driver.findElement(By.xpath("//a[contains(.,'Catalog')]")).click();
        driver.findElement(By.xpath("//ul[@class='docs']//a[contains(.,'Catalog')]")).click();
        driver.findElement(By.cssSelector("a.button[href*='edit_product']")).click();
        assertTrue(isElementPresent(driver, By.xpath("//h1[contains(.,'Add New Product')]")));
        driver.findElement(By.cssSelector("input[name='status'][value='1']")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name='code']")).sendKeys(dateString);
        driver.findElement(By.cssSelector("input[name='categories[]'][value='1']")).click();
        driver.findElement(By.cssSelector("input[name='product_groups[]'][value='1-3']")).click();
        WebElement quantity = driver.findElement(By.cssSelector("input[name='quantity']"));
        quantity.clear();
        quantity.sendKeys("10");
        quantity.sendKeys(Keys.TAB);
        Select soldStatus = new Select(driver.findElement(By.cssSelector("select[name*='sold_out_status_id']")));
        soldStatus.selectByVisibleText("Temporary sold out");
        driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("2017-05-15");
        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("2018-05-15");
        driver.findElement(By.cssSelector("a[href='#tab-information']")).click();
        while (driver.findElement(By.cssSelector("input[name='keywords']")) == null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='keywords']")));
        }
        Select manufacturer = new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]")));
        manufacturer.selectByVisibleText("ACME Corp.");
        driver.findElement(By.cssSelector("input[name='keywords']")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys(productName);
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Some Description "+productName);
        driver.findElement(By.cssSelector("input[name*=head_title]")).sendKeys("Some Title");
        driver.findElement(By.cssSelector("input[name*=meta_description]")).sendKeys("Some Meta Description");
        driver.findElement(By.cssSelector("a[href='#tab-prices']")).click();
        while (driver.findElement(By.cssSelector("input[name='purchase_price']")) == null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='purchase_price']")));
        }
        driver.findElement(By.cssSelector("input[name*=purchase_price]")).sendKeys("10");
        Select currency = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        currency.selectByVisibleText("US Dollars");
        driver.findElement(By.cssSelector("input[name='gross_prices[USD]']")).sendKeys("10");
        driver.findElement(By.cssSelector("input[name='gross_prices[EUR]']")).sendKeys("8");
        driver.findElement(By.cssSelector("button[name=save]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Rubber Ducks')]")).click();
        Assert.assertEquals(productName, driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]")).getText());
        isElementPresent(driver, By.xpath("//a[contains(text(),'" + productName + "')]"));
    }

}
