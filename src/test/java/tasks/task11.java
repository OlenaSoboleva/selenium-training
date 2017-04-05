package tasks;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.Date;

import static junit.framework.Assert.assertTrue;

/**
 * Created by osob on 3/30/2017.
 */
public class task11 extends BasePage {
    String newAccountLink = "http://localhost/litecart/en/create_account";

    @Test
    public void fillingFields() {
        loginToSite(newAccountLink, "input[ name='address1']");
        Date date = new Date();
        String email = "email" + date.getTime() + "@test.com";
        Select country = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        driver.findElement(By.cssSelector("input[ name='firstname']")).sendKeys("user_firstname");
        driver.findElement(By.cssSelector("input[ name='lastname']")).sendKeys("user_lastname");
        driver.findElement(By.cssSelector("input[ name='address1']")).sendKeys("address1");
        driver.findElement(By.cssSelector("input[ name='postcode']")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[ name='city']")).sendKeys("New York");
        country.selectByVisibleText("United States");
        Select state = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        state.selectByIndex(12);
        driver.findElement(By.cssSelector("input[ name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[ name='phone']")).sendKeys("+180671231111");
        driver.findElement(By.cssSelector("input[ name='password']")).sendKeys("password");
        driver.findElement(By.cssSelector("input[ name='confirmed_password']")).sendKeys("password");
        driver.findElement(By.cssSelector("button[name='create_account']")).click();
        assertTrue(isElementPresent(driver, By.cssSelector("div.content a[href*=logout]")));
        driver.findElement(By.cssSelector("div.content a[href*=logout]")).click();
        assertTrue(isElementPresent(driver, By.cssSelector("input[ name='email']")));
        driver.findElement(By.cssSelector("input[ name='email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[ name='password']")).sendKeys("password");
        driver.findElement(By.cssSelector("button[name='login']")).click();
        driver.findElement(By.cssSelector("div.content a[href*=logout]")).click();
        assertTrue(isElementPresent(driver, By.cssSelector("input[ name='email']")));
    }

}
