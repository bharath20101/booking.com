package Wrappers;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

public interface IPageBase {

	public RemoteWebDriver invokeApp(ExtentTest test);
	
	public void enterByXpath(String xpathValue, String data);
	
	public boolean verifyTitle(String title);
	
	public void clickByXpath(String xpathVal);
				
	public void quitBrowser();
}
