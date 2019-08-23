import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExecuteOrder {

    EventFiringWebDriver driver = null;
    String browser;
    String quantitiesOfProductBefore;
    String quantitiesOfProductAfter;

    //@Parameters({"browser"})
    //@BeforeClass
   // public void getBrowser(String browser){
        //driver = getDriver("browser");
    //}

    @BeforeClass
    @Parameters({"browser"})
    public void setBrowser(String browser) {
        driver = new EventFiringWebDriver(DriverManager.getDriver("browser"));
        MyWebDriverListener listener = new MyWebDriverListener();
        driver.register(listener);

    }

    @Test
    public void checkSite(){
        driver.manage().window().maximize();
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        if(browser == "mobile"){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"material-icons d-inline\"]")));
            if(driver.findElements(By.xpath("//*[@class=\"material-icons d-inline\"]")).size()>0){
                System.out.println("Hamburger button is displayed for mobile");
            }else {
                System.out.println("Hamburger button isn't displayed for mobile");
            }

            if(driver.findElements(By.xpath("//*[@class=\"logo img-responsive\"]")).size()>0){
                System.out.println("Logo is displayed for mobile");
            }else {
                System.out.println("Logo isn't displayed for mobile");
            }

            if(driver.findElements(By.xpath("//*[@title=\"Войти в учетную запись\"]")).size()>0){
                System.out.println("Login button is displayed for mobile");
            }else {
                System.out.println("Login button isn't displayed for mobile");
            }

            if(driver.findElements(By.xpath("//*[@class=\"material-icons shopping-cart\"]")).size()>0){
                System.out.println("Cart button is displayed for mobile");
            }else {
                System.out.println("Cart button isn't displayed for mobile");
            }
        }
        else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"logo img-responsive\"]")));
            if(driver.findElements(By.xpath("//*[@class=\"logo img-responsive\"]")).size()>0){
                System.out.println("Logo is displayed for desktop");
            }else {
                System.out.println("Logo isn't displayed for desktop");
            }

            if(driver.findElements(By.xpath("//*[@id=\"category-3\"]/a")).size()>0){
                System.out.println("WOMEN dropdown is displayed for desktop");
            }else {
                System.out.println("WOMEN dropdown isn't displayed for desktop");
            }

            if(driver.findElements(By.xpath("//*[@class=\"language-selector dropdown js-dropdown\"]")).size()>0){
                System.out.println("Language dropdown is displayed for desktop");
            }else {
                System.out.println("Language dropdown isn't displayed for desktop");
            }

            if(driver.findElements(By.xpath("//*[@class=\"currency-selector dropdown js-dropdown\"]")).size()>0){
                System.out.println("Currency dropdown is displayed for desktop");
            }else {
                System.out.println("Currency dropdown isn't displayed for desktop");
            }

            if(driver.findElements(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a")).size()>0){
                System.out.println("Login button is displayed for desktop");
            }else {
                System.out.println("Login button isn't displayed for desktop");
            }

            if(driver.findElements(By.xpath("//*[@class=\"blockcart cart-preview inactive\"]")).size()>0){
                System.out.println("Cart button is displayed for desktop");
            }else {
                System.out.println("Cart button isn't displayed for desktop");
            }
        }
    }

    @Test
    public void orderOfProduct(){
        //driver = getDriver("chrome");

        //driver.manage().window().maximize();
        //driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products")));

        WebElement allProducts = driver.findElement(By.xpath("//*[@id=\"content\"]/section/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allProducts);
        allProducts.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-md-6")));

        Random random = new Random();

        List <WebElement> listings = driver.findElements(By.xpath("//*[@class=\"h3 product-title\"]"));
        int numberOfProduct = random.nextInt(listings.size()-1) + 1;
        System.out.println(numberOfProduct);
        WebElement requiredlisting = listings.get(numberOfProduct);
        requiredlisting.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@itemprop=\"name\"]")));

        String urlOfProduct = driver.getCurrentUrl();

        String nameOfDesiredProduct = driver.findElement(By.xpath("//h1[@itemprop=\"name\"]")).getText().toUpperCase();
        String priceOfDesiredProduct = driver.findElement(By.xpath("//*[@itemprop=\"price\"]")).getText();

        if(elemetIsPresent(By.xpath("//*[@class=\"nav-link\"]"))){

            WebElement productDetailesTab = driver.findElement(By.xpath("//*[@class=\"nav-link\"]"));
            productDetailesTab.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-details\"]/div[3]/span")));

            quantitiesOfProductBefore = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[3]/span")).getText().replaceAll("\\D","");
        }
        else {
            quantitiesOfProductBefore = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/span")).getText().replaceAll("\\D","");
        }

        WebElement placeOrderButton = driver.findElement(By.xpath("//*[@class=\"btn btn-primary add-to-cart\"]"));
        placeOrderButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"h6 product-name\"]")));

        String quantityOfOrderingProduct = driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/p[2]")).getText().replaceAll("\\D","");
        Assert.assertEquals(quantityOfOrderingProduct,"1","Wrong quantity");

        String nameOfOrderingProduct = driver.findElement(By.xpath("//*[@class=\"h6 product-name\"]")).getText().toUpperCase();
        Assert.assertEquals(nameOfDesiredProduct,nameOfOrderingProduct,"Wrong product name");

        String priceOfOrderingProduct = driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/p[1]")).getText();
        Assert.assertEquals(priceOfDesiredProduct,priceOfOrderingProduct,"Wrong product price");

        WebElement placingOrderButton = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        placingOrderButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[1]/a")));

        String checkNameOfProduct = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[1]/a")).getText().toUpperCase();
        String checkPriceOfProduct = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[2]/span")).getText();
        String checkQuantityOfProduct = driver.findElement(By.xpath("//*[@class=\"js-cart-line-product-quantity form-control\"]")).getAttribute("value");

        Assert.assertEquals(checkNameOfProduct, nameOfDesiredProduct, "Wrong product name");
        Assert.assertEquals(checkPriceOfProduct,priceOfDesiredProduct, "Wrong product price");
        Assert.assertEquals(checkQuantityOfProduct, "1", "Wrong quantity of product");

        WebElement makeTheOrder = driver.findElement(By.xpath("//*[@class=\"btn btn-primary\"]"));
        makeTheOrder.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@name=\"firstname\"]")));

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

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name=\"address1\"]")));

        WebElement addressField = driver.findElement(By.xpath("//input[@name=\"address1\"]"));
        addressField.sendKeys("Lev street, 12");

        WebElement postcodeField = driver.findElement(By.xpath("//input[@name=\"postcode\"]"));
        postcodeField.sendKeys("50001");

        WebElement cityField = driver.findElement(By.xpath("//input[@name=\"city\"]"));
        cityField.sendKeys("Krivoy Rog");

        WebElement continue2Button = driver.findElement(By.xpath("//button[@name=\"confirm-addresses\"]"));
        continue2Button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@name=\"confirmDeliveryOption\"]")));

        WebElement continue3Button = driver.findElement(By.xpath("//button[@name=\"confirmDeliveryOption\"]"));
        continue3Button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"payment-options\"]")));

        WebElement billRadioButton = driver.findElement(By.xpath("//input[@id=\"payment-option-1\"]"));
        billRadioButton.click();
        WebElement conditionsToApprove = driver.findElement(By.xpath("//input[@id=\"conditions_to_approve[terms-and-conditions]\"]"));
        conditionsToApprove.click();

        WebElement continue4Button = driver.findElement(By.xpath("//button[@class=\"btn btn-primary center-block\"]"));
        continue4Button.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class=\"material-icons done\"]")));

        String successfulMessage = driver.findElement(By.xpath("//*[@id=\"content-hook_order_confirmation\"]/div/div/div/h3")).getText().replaceAll("[^а-яА-ЯЁ\\s]", "");

        Assert.assertEquals(successfulMessage,"ВАШ ЗАКАЗ ПОДТВЕРЖДЁН","Wrong message");

        String nameOfProductOrdered = driver.findElement(By.xpath("//*[@id=\"order-items\"]/div/div/div[2]/span")).getText().split(" -")[0].toUpperCase();
        System.out.println(nameOfProductOrdered);
        Assert.assertEquals(nameOfProductOrdered,nameOfDesiredProduct,"Wrong ordered product name");

        String quantityOrderedProduct = driver.findElement(By.xpath("//div[@class=\"col-xs-2\"]")).getText();
        Assert.assertEquals(quantityOrderedProduct,"1","Wrong quantity");

        String priceOrderedProduct = driver.findElement(By.xpath("//div[@class=\"col-xs-5 text-xs-right bold\"]")).getText();
        Assert.assertEquals(priceOrderedProduct,priceOfDesiredProduct,"Wrong price");

        driver.get(urlOfProduct);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@itemprop=\"name\"]")));


        if(elemetIsPresent(By.xpath("//*[@class=\"nav-link\"]"))){

            WebElement productDetailesTab = driver.findElement(By.xpath("//*[@class=\"nav-link\"]"));
            productDetailesTab.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"product-details\"]/div[3]/span")));

            quantitiesOfProductAfter = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[3]/span")).getText().replaceAll("\\D","");
        }
        else {
            quantitiesOfProductAfter = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/span")).getText().replaceAll("\\D","");
        }

        int quantitiesOfProductBeforeInt = Integer.parseInt(quantitiesOfProductBefore);
        int quantity = quantitiesOfProductBeforeInt - 1;
        String quantitiesOfProductBeforeString = Integer.toString(quantity);

        Assert.assertEquals(quantitiesOfProductBeforeString,quantitiesOfProductAfter, "Wrong quantity");


    }

/*
    public static WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(ExecuteOrder.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();

            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(ExecuteOrder.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                return new InternetExplorerDriver();

            case "mobile":
                System.setProperty(
                        "webdriver.cgrome.driver",
                        new File(ExecuteOrder.class.getResource("/chromedriver.exe").getFile()).getPath());
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone 6");

                ChromeOptions mobChromeOptions = new ChromeOptions();
                mobChromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(mobChromeOptions);

            case "remote-firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                try {
                    return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            case "remote-ie":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                try {
                    return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), ieOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            case "remote-chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                try {
                    return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(ExecuteOrder.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }*/

    public boolean elemetIsPresent(By by){
        return driver.findElements(by).size() > 0;
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }

}