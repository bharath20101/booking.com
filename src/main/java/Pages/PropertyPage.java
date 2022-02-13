package Pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import Wrappers.PageBase;

public class PropertyPage extends PageBase {

	public PropertyPage(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public void selectReserve() {
		switchToSelectedWindow();
		clickByXpath("(//span[contains(text(),'Reserve')])[3]");
	}

	public SearchResultsPage switchToSearchResultsPage() {
		switchToHomeWindow();
		return new SearchResultsPage(driver, test);
	}

	public void closeSession() {
		endTestcase();
		quitBrowser();
		unloadObjects();
		endResult();
	}

}