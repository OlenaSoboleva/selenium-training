package tasks;

import net.lightbody.bmp.core.har.Har;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

public class task17 extends BasePage{
    @Test
    public void main() throws Throwable {
        proxy.newHar();
        LoginToAdmin();
        driver.navigate().to("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        getBrowserLogs();
        wait.until(ExpectedConditions.titleIs("Catalog | My Store"));
        List<WebElement> webElementList = driver.findElements(By.cssSelector("a[href*=product_id]:not([title='Edit'])"));
        Actions act = new Actions(driver);
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();
        for (int i = 0; i < webElementList.size(); i++) {
            act.keyDown(Keys.SHIFT).click(webElementList.get(i)).keyUp(Keys.SHIFT).build().perform();
            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            driver.switchTo().window(newWindow);
            getBrowserLogs();
            driver.close();
            driver.switchTo().window(originalWindow);
        }

        Har har=proxy.endHar();
        har.getLog().getEntries().forEach(l-> System.out.println(l.getResponse().getStatus()+":"+l.getRequest().getUrl()));

    }
}
