package tasks;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

public class task14 extends BasePage {
    @Test
    public void main() throws Throwable {
        LoginToAdmin();
        driver.navigate().to("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector(("a.button"))).click();
        wait.until(ExpectedConditions.titleIs("Add New Country | My Store"));
        List<WebElement> webElementList = driver.findElements(By.cssSelector("td>a[target*=_blank]"));
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();
        for (int i = 0; i < webElementList.size(); i++) {
            webElementList.get(i).click();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(originalWindow);
        }

    }
}
