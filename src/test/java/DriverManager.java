import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
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

    }
}
