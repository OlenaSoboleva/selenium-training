package task9;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Main extends BasePage {
    public String mainlink = "http://localhost/litecart/admin/?app=countries&doc=countries";
    public String geozones = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";

    @Test
    public void Menu() throws Throwable {
        login();
        checkCountry();
        checkCountryWithState();
        geozonelist();
    }

    public void login() throws Throwable {
        String locator = "[name=username]";
        loginToSite(mainlink, locator);
        String stringinput = "admin";
        driver.findElement(By.name("username")).sendKeys(stringinput);
        driver.findElement(By.name("password")).sendKeys(stringinput);
        driver.findElement(By.name("login")).click();
    }

    public void checkCountry() {
        System.out.println("Countries sorted= " + checkSortList(elementsToList("//tr[@class='row']//td[5]")));
    }

    public List<String> xpathListCountryWhithState() {

        List<WebElement> CountryList = driver.findElements(By.xpath("//tr[@class='row']//td[5]"));
        List<WebElement> CountryListValue = driver.findElements(By.xpath("//tr[@class='row']//td[6]"));
        List<String> CountryState = new ArrayList<>();
        String state;
        for (int i = 0; i < CountryList.size(); i++) {
            if (!CountryListValue.get(i).getAttribute("textContent").equalsIgnoreCase("0")) {
                state = CountryList.get(i).getAttribute("textContent");
                CountryState.add("//tr[@class='row']//td[5]//a[contains(.,'" + state + "')]");
            }
        }
        return CountryState;
    }

    public void checkCountryWithState() {
        driver.navigate().to(mainlink);
        List<String> CountryState = xpathListCountryWhithState();
        String locator = "//tr[@class='row']//td[3]";
        manipulation(CountryState, mainlink, locator);
    }


    public void geozonelist() {
        driver.navigate().to(geozones);
        List<WebElement> CountryList = driver.findElements(By.xpath("//tr[@class='row']//td[3]"));
        List<String> xpathCountry = new ArrayList<>();
        String state;
        for (int i = 0; i < CountryList.size(); i++) {
            state = CountryList.get(i).getAttribute("textContent");
            xpathCountry.add("//tr[@class='row']//td[3]//a[contains(.,'" + state + "')]");
        }
        String locator = "//table[@id='table-zones']/tbody//td[3]//option[@selected='selected']";
        manipulation(xpathCountry, geozones, locator);
    }
}
