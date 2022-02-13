package Pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import Wrappers.PageBase;

public class SearchResultsPage extends PageBase {

	public SearchResultsPage(RemoteWebDriver driver,  ExtentTest test) {
		this.driver = driver;
		this.test = test;
		if (!verifyTitle("Booking.com : Hotels in Stockholm . Book your hotel now!")) {
			reportStep("This is not search results page", "FAIL");
		}
	}

	public SearchResultsPage sortByTopReviewedCategory() {
		clickByXpathWithJavaScriptEcecutor("(//td[@class='bui-calendar__date'])[10]");
		if (getSizeByXpath("//a[text()='Top Reviewed']") == 1) {
			clickByXpath("//a[text()='Top Reviewed']");
		} else {
			clickByXpath("//a[@data-type='dropdown']");
			clickByXpath("(//a[text()='Top Reviewed'])[2]");

		}
		return this;
	}

	public PropertyPage selectThirdPropertyFromTheTop() {
		clickByXpath("(//div[@data-testid='property-card']/following::a[@class='fb01724e5b'])[2]");
		return new PropertyPage(driver, test);
	}

	public PropertyPage selectFourthPropertyFromTheTop() {
		clickByXpath("(//div[@data-testid='property-card']/following::a[@class='fb01724e5b'])[3]");
		return new PropertyPage(driver, test);
	}
}