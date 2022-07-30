package hlybchenko;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Duration duration = Duration.ofSeconds(5);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("What video card do you want to find?");
        String searchInputValue = new Scanner(System.in).nextLine();

        WebDriver driver = driverConfig();
        driver.get("https://rozetka.com.ua/ua/");

        // Select search window on site and enter the value
        WebElement element = driver.findElement(By.xpath("//header/div/div/div/form/div/div/input"));
        element.sendKeys(searchInputValue);

        // Click to search button
        elementClicker(driver, "//header/div/div/div/form/button", 2000);

        // Click to "only video card"
        elementClicker(driver, "//*[text()[contains(.,'Відеокарти')]]", 2000);

        // Click to "seller only Rozetka"
        try {
            elementClicker(driver, "//*[contains(@class, 'checkbox-filter__link') and contains(., 'Rozetka')]",
                    1000);
        }catch (Exception e) {
            System.out.println("Vendor selection not available.");
        }

        List<WebElement> description = driver.findElements(By.className("goods-tile__title"));
        List<WebElement> links = driver.findElements(By.className("goods-tile__heading"));
        List<WebElement> price  = driver.findElements(By.className("goods-tile__price-value"));

        for (int i = 1; i <= description.size()-20; i++) {
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
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(duration);
        driver.manage().timeouts().scriptTimeout(duration);
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