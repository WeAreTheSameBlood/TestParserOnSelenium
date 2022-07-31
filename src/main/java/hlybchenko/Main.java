package hlybchenko;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("What video card do you want to find?");
        String searchInputValue = new Scanner(System.in).nextLine();

        WebDriver driver = driverConfig();
        driver.get("https://rozetka.com.ua/ua/");

        // Select search window on site and enter the value
        WebElement element = driver.findElement(By.xpath("//header/div/div/div/form/div/div/input"));
        element.sendKeys(searchInputValue);

        // Click to search button
        try {
            elementClicker(driver, "//header/div/div/div/form/button", 2000);
        } catch (Exception e) {
            System.out.println("Incorrect enter value.");
        }

        // Click to "only video card"
        try {
            elementClicker(driver, "//*[text()[contains(.,'Відеокарти')]]", 1000);
        } catch (Exception e) {
            System.out.println("Attention: *Video card* category not available for selection.");
        }

        // Click to "seller only Rozetka"
        try {
            elementClicker(driver, "//*[contains(@class, 'checkbox-filter__link') and contains(., 'Rozetka')]",
                    3000);
        } catch (Exception e) {
            System.out.println("Attention: Vendor selection not available.");
        }

        // Create several collections of the Leaf type and save the result
        List<WebElement> description = driver.findElements(By.className("goods-tile__title"));
        List<WebElement> links = driver.findElements(By.className("goods-tile__heading"));
        List<WebElement> price  = driver.findElements(By.className("goods-tile__price-value"));

        // Outputting collected information to the console
        for (int i = 0; i < description.size(); i++) {
            System.out.println("------\nDescription: " + description.get(i).getText() +
                    "\nPrice: " +  price.get(i).getText() + " UAH.\n" + links.get(i).getAttribute("href"));
        }

        System.out.println("\nWe find of - " + driver.getTitle());

        driver.quit();
    }

    /**
     * Configurator for driver.
     * Here we configure the driver and declare the parameters of the program.
     */
    private static WebDriver driverConfig() {
        var driverDuration = Duration.ofSeconds(5);
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(driverDuration);
        driver.manage().timeouts().scriptTimeout(driverDuration);
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * We create a web element and perform a search using XPath, click on it and wait for the browser to work.
     * @param driver configure in the driverConfig() method;
     * @param xPath XPath path to element;
     * @param sleepTime wait for the browser to work.
     */
    private static void elementClicker(WebDriver driver, String xPath, int sleepTime) throws InterruptedException {
        WebElement webElement = driver.findElement(By.xpath(xPath));
        webElement.click();
        Thread.sleep(sleepTime);
    }
}