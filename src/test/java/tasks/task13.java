package tasks;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class task13 extends BasePage {
    int count = 0;

    @Test
    public void main() {
        for (int i = 0; i < 3; i++) {
            loginToSiteMainPage("div#box-campaigns.box [class=link]");
            WebElement product = driver.findElements(By.cssSelector("div#box-most-popular.box [class=link]")).get(0);
            product.click();
            if (driver.findElements(By.cssSelector("select[name*=options]")).size() > 0) {
                Select size = new Select(driver.findElement(By.cssSelector("select[name*=options]")));
                size.selectByVisibleText("Small");
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name*=add_cart_product]")));
            driver.findElement(By.cssSelector("button[name*=add_cart_product]")).click();
            count = Integer.parseInt(driver.findElement(By.cssSelector("span.quantity")).getText());
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.cssSelector("span.quantity")), String.valueOf(count + 1)));
        }
        driver.findElement(By.cssSelector("span.quantity")).click();
        WebElement table = driver.findElement(By.cssSelector(".dataTable.rounded-corners"));
        List<WebElement> tablecount = driver.findElements(By.cssSelector("td.item"));
        int tablecountsize = tablecount.size();
        for (int i = 0; i < tablecountsize; i++) {
            driver.findElement(By.cssSelector("button[name*=remove_cart_item]")).click();
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(table)));
        }
    }
}