package StepDefinitions;

import Pages.*;
import Wrappers.PageBase;
import cucumber.api.java.en.*;

public class BookingSteps extends PageBase {

	HomePage homePage;
	SearchResultsPage searchResultsPage;
	PropertyPage propertyPage;

	@Given("^User has launched Booking\\.com website$")
	public void user_has_launched_Booking_com_website() throws Throwable {
		startResult();
		testCaseName = "Reserve Hotel";
		testDescription = "Login to Leaftaps";
		invokeApp(test);
		homePage = new HomePage(driver, test);
	}

	@When("^User selectes Stockholm$")
	public void user_selectes_Stockholm() throws Throwable {
		searchResultsPage = homePage.selectStockholm();
	}

	@When("^User sorts properties by top reviewed category$")
	public void user_sorts_properties_by_top_reviewed_category() throws Throwable {
		searchResultsPage.sortByTopReviewedCategory();
	}

	@When("^selects third property from the top$")
	public void selects_third_property_from_the_top() throws Throwable {
		propertyPage = searchResultsPage.selectThirdPropertyFromTheTop();
	}

	@When("^selects any room and clicks reserve button$")
	public void selects_any_room_and_clicks_reserve_button() throws Throwable {
		propertyPage.selectReserve();
	}

	@When("^comesback to search results page$")
	public void comesback_to_search_results_page() throws Throwable {
		searchResultsPage = propertyPage.switchToSearchResultsPage();
	}

	@When("^selects fourth preperty from the top$")
	public void selects_fourth_preperty_from_the_top() throws Throwable {
		searchResultsPage.selectFourthPropertyFromTheTop();
	}

	@When("^selects any room and clicks reserve button for the foruth result$")
	public void selects_any_room_and_clicks_reserve_button_for_the_foruth_result() throws Throwable {
		propertyPage.selectReserve();
		propertyPage.closeSession();
	}
}
