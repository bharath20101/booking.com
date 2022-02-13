package Wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import Resources.Reporter;

public class PageBase extends Reporter implements IPageBase {

	public PageBase(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public RemoteWebDriver driver;
	protected static Properties prop;
	public String url, browserName, sHubUrl, sHubPort;
	public boolean remote;
	public WebDriverWait wait;

	public PageBase() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/java/Resources/config.properties")));
			url = prop.getProperty("URL");
			browserName = prop.getProperty("Browser");
			remote = Boolean.parseBoolean(prop.getProperty("Remote"));
			sHubUrl = prop.getProperty("HUB");
			sHubPort = prop.getProperty("PORT");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RemoteWebDriver invokeApp(ExtentTest test) {
		try {
			test = startTestCase(testCaseName, testDescription);

			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browserName);
			dc.setPlatform(Platform.WINDOWS);

			if (remote) {
				driver = new RemoteWebDriver(new URL("http://" + sHubUrl + ":" + sHubPort + "/wd/hub"), dc);
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}
			reportStep("The browser:" + browserName + " launched successfully", "PASS");
			driver.manage().window().maximize();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(url);

		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser:" + browserName + " could not be launched", "FAIL");
		}
		return driver;
	}

	public void enterByXpath(String xpathVal, String data) {
		try {
			driver.findElement(By.xpath(xpathVal)).clear();
			driver.findElement(By.xpath(xpathVal)).sendKeys(data);
			reportStep("The data: " + data + " entered successfully in field :" + xpathVal, "PASS");

		} catch (NoSuchElementException e) {
			reportStep("The data: " + data + " could not be entered in the field :" + xpathVal, "FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while entering " + data + " in the field :" + xpathVal, "FAIL");
		}

	}

	public boolean verifyTitle(String title) {
		boolean titleStatus = false;
		try {
			if (driver.getTitle().equalsIgnoreCase(title)) {
				reportStep("The title of the page matches with the value :" + title, "PASS");
				titleStatus = true;
			} else
				reportStep("The title of the page:" + driver.getTitle() + " did not match with the value :" + title,
						"FAIL");
		} catch (Exception e) {
			reportStep("Unknown exception occured while verifying the title", "FAIL");
		}
		return titleStatus;
	}

	public void clickByXpath(String xpathVal) {
		try {
			wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathVal)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
			driver.findElement(By.xpath(xpathVal)).click();
			reportStep("The element with xpath: " + xpathVal + " is clicked.", "PASS");
		} catch (NoSuchElementException e) {
			reportStep("The element with xpath: " + xpathVal + " could not be clicked.", "FAIL");
		} catch (StaleElementReferenceException e) {
			System.out.println(
					"The element with xpath: " + xpathVal + " could not be found so retrying to click the element");
			driver.findElement(By.xpath(xpathVal)).click();
		} catch (Exception e) {
			reportStep("Unable to click element with xpath: " + xpathVal, "FAIL");
		}
	}

	public void clickByXpathWithJavaScriptEcecutor(String xpathVal) {
		try {
			WebElement element = driver.findElement(By.xpath("(//td[@class='bui-calendar__date'])[10]"));

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", element);
			reportStep("The element : " + xpathVal + " is clicked.", "PASS");
		} catch (NoSuchElementException e) {
			reportStep("Unable to click element with xpath: " + xpathVal, "FAIL");
		} catch (StaleElementReferenceException e) {
			System.out.println(
					"The element with xpath: " + xpathVal + " could not be found so retrying to click the element");
			driver.findElement(By.xpath(xpathVal)).click();
		} catch (Exception e) {
			reportStep("Unknown error occured while clicking element with xpath: " + xpathVal, "FAIL");
		}
	}

	public void switchToSelectedWindow() {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String window : allWindows) {
				driver.switchTo().window(window);
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the last window.", "FAIL");
		}
	}

	public void switchToHomeWindow() {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String window : allWindows) {
				driver.switchTo().window(window);
				break;
			}
		} catch (Exception e) {
			reportStep("The window could not be switched to the home window.", "FAIL");
		}
	}

	public int getSizeByXpath(String xpathVal) {
		int size = 0;
		try {
			size = driver.findElements(By.xpath(xpathVal)).size();
		} catch (NoSuchElementException e) {
			System.out.println("The element with xpath: " + xpathVal + " could not be found");
		} catch (Exception e) {
			reportStep("Unable to find element with xpath: " + xpathVal, "FAIL");
		}
		return size;
	}

	public void unloadObjects() {
		prop = null;
	}

	public void quitBrowser() {
		try {
			driver.quit();
			System.out.println("*****Check extent result.html file for execution restuls*****");
		} catch (Exception e) {
			reportStep("The browser:" + driver.getCapabilities().getBrowserName() + " could not be closed.", "FAIL");
		}
	}
}
