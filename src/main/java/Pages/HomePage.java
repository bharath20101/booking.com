package Pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import Wrappers.PageBase;

public class HomePage extends PageBase {

	public HomePage(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		if (!verifyTitle("Booking.com | Official site | The best hotels, flights, car rentals & accommodations")) {
			reportStep("This is not Home Page", "FAIL");
		}
	}

	public SearchResultsPage selectStockholm() {
		clickByXpath("//button[text()='Accept']");
		enterByXpath("//input[@type='search']", "Stockholm");
		clickByXpath("//span[contains(text(),'Search')]");
		return new SearchResultsPage(driver, test);
	}
}