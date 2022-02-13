package Resources;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter {
	public ExtentTest test;
	public static ExtentReports extent;
	public String testCaseName, testDescription, category, authors;

	
	public void reportStep(String desc, String status) {
		
		if(status.toUpperCase().equals("PASS")){
			test.log(LogStatus.PASS, desc);
		}else if(status.toUpperCase().equals("FAIL")){
			test.log(LogStatus.FAIL, desc);
			throw new RuntimeException("FAILED");
		}else if(status.toUpperCase().equals("INFO")){
			test.log(LogStatus.INFO, desc);
		}else if(status.toUpperCase().equals("WARN")){
			test.log(LogStatus.WARNING, desc);
		}
	}	

	public ExtentReports startResult(){
		extent = new ExtentReports("./reports/result.html", false);
		return extent;
	}

	public ExtentTest startTestCase(String testCaseName, String testDescription){
		test = extent.startTest(testCaseName, testDescription);
		return test;
	}

	public void endResult(){		
		extent.flush();
	}

	public void endTestcase(){
		extent.endTest(test);
	}
	
}