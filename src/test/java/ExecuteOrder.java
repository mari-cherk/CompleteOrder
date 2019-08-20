import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.DriverManager;
import java.util.List;
import java.util.Random;

public class ExecuteOrder {

    @Test
    public void orderOfProduct(){
        WebDriver driver = getDriver("chrome");
        driver.manage().window().maximize();
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products")));

        WebElement allProducts = driver.findElement(By.xpath("//*[@id=\"content\"]/section/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allProducts);
        allProducts.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-md-6")));

        Random random = new Random();

        List <WebElement> listings = driver.findElements(By.xpath("//article[@class=\"product-miniature js-product-miniature\"]"));
        int numberOfProduct = random.nextInt(listings.size()) + 1;
        System.out.println(numberOfProduct);
        WebElement requiredlisting = listings.get(numberOfProduct);
        requiredlisting.click();

        WebElement addToCartButton = driver.findElement(By.xpath("//button[@class=\"btn btn-primary add-to-cart\"]"));
        addToCartButton.click();
        String nameOfDesiredProduct = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]")).getText();
        String priceOfDesiredProduct = driver.findElement(By.xpath("//*[@itemprop=\"price\"]")).getText();

        WebElement placeOrderButton = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        placeOrderButton.click();

        String quantityOfOrderingProduct = driver.findElement(By.xpath("//input[@class=\"js-cart-line-product-quantity form-control\"]")).getAttribute("value");
        Assert.assertEquals(quantityOfOrderingProduct,"1","Wrong quantity");

        String nameOfOrderingProduct = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[1]/a")).getText();
        Assert.assertEquals(nameOfDesiredProduct,nameOfOrderingProduct,"Wrong product name");

        String priceOfOrderingProduct = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[2]/span")).getText();
        Assert.assertEquals(priceOfDesiredProduct,priceOfOrderingProduct,"Wrong product price");

        WebElement placingOrderButton = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        placingOrderButton.click();

        WebElement userNameField = driver.findElement(By.xpath("//*[@name=\"firstname\"]"));
        userNameField.sendKeys("Mari");

        WebElement userLastnameField = driver.findElement(By.xpath("//*[@name=\"lastname\"]"));
        userLastnameField.sendKeys("Cherk");

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"customer-form\"]/section/div[4]/div[1]/input"));

        int n = random.nextInt(100)+1;
        String email = "maricherk"+n+"@gmail.com";
        System.out.println(email);
        emailField.sendKeys(email);

        WebElement continueButton = driver.findElement(By.xpath("//button[@data-link-action=\"register-new-customer\"]"));
        continueButton.click();

        WebElement addAddressLink = driver.findElement(By.linkText("добавить адрес"));
        addAddressLink.click();

        WebElement addressField = driver.findElement(By.xpath("//input[@name=\"address1\"]"));
        addressField.sendKeys("Lev street, 12");

        WebElement postcodeField = driver.findElement(By.xpath("//input[@name=\"postcode\"]"));
        postcodeField.sendKeys("50001");

        WebElement cityField = driver.findElement(By.xpath("//input[@name=\"city\"]"));
        cityField.sendKeys("Krivoy Rog");

        WebElement continue2Button = driver.findElement(By.xpath("//button[@name=\"confirm-addresses\"]"));
        continue2Button.click();

        WebElement continue3Button = driver.findElement(By.xpath("//button[@name=\"confirmDeliveryOption\"]"));
        continue3Button.click();

        WebElement billRadioButton = driver.findElement(By.xpath("//input[@id=\"payment-option-1\"]"));
        billRadioButton.click();
        WebElement conditionsToApprove = driver.findElement(By.xpath("//input[@id=\"conditions_to_approve[terms-and-conditions]\"]"));
        conditionsToApprove.click();

        WebElement continue4Button = driver.findElement(By.xpath("//button[@class=\"btn btn-primary center-block\"]"));
        continue4Button.click();

        String successfulMessage = driver.findElement(By.xpath("//i[@class=\"material-icons done\"]")).getText();

        Assert.assertEquals(successfulMessage,"Ваш заказ подтверждён","Wrong message");

        String quantityOrderedProduct = driver.findElement(By.xpath("//div[@class=\"col-xs-2\"]")).getText();
        Assert.assertEquals(quantityOrderedProduct,"1","Wrong quantity");

        String priceOderedProduct = driver.findElement(By.xpath("//div[@class=\"col-xs-5 text-xs-right bold\"]")).getText();
        Assert.assertEquals(priceOfOrderingProduct,priceOfDesiredProduct,"Wrong price");











    }


    public static WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(DriverManager.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(DriverManager.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                return new InternetExplorerDriver();
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverManager.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

}