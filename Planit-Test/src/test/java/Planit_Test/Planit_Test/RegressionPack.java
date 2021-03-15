package Planit_Test.Planit_Test;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import com.planItTestAutomation.common.CommonLibrary;
import com.planItTestAutomation.common.PlanItLibrary;
import com.planItTesting.pages.ContactPage;

/**
 * The RegressionPack class contains Test Methods to run test cases.
 * @author Ram Pavan Kumar
 * @since 15-Mar 2021
 * @version 1.0
 */

public class RegressionPack {

	@BeforeClass
	public static void tearUp() throws Exception {
		CommonLibrary.startReport();
		CommonLibrary.InitiateReport("PlanitTestingReport");
		CommonLibrary.initiateDriverInstance("chrome");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void validateContactPage() throws Exception {
		PlanItLibrary.LaunchContactPage();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.submitButtonXpath)).click();
		boolean errorMessageForeName = PlanItLibrary.verifyErrorMessageByText("Forename is required");
		boolean errorMessageEmail = PlanItLibrary.verifyErrorMessageByText("Email is required");
		boolean errorMessageMessage = PlanItLibrary.verifyErrorMessageByText("Message is required");
		if (errorMessageForeName && errorMessageEmail && errorMessageMessage) {
			CommonLibrary.logReport(true, "validateContactPage: Errors displayed correctly in mandatory fields",
					"warningsCheck");
		} else {
			CommonLibrary.logReport(false, "validateContactPage: Errors are not displayed", "warningsCheckFail");
		}
		PlanItLibrary.fillContactPageDetails("TestUser", "testuser123@gmail.com", "Please contact me. I have queries",
				"88562458", "Family");

		// To verify if the errors are gone.
		// This test case takes a little time as driver instance is searching
		// for error messages explicitly
		boolean errorMsgforeName = PlanItLibrary.verifyErrorMessageisDisplayed("Forename is required");
		boolean errorMsgEmail = PlanItLibrary.verifyErrorMessageisDisplayed("Email is required");
		boolean errorMsgMessage = PlanItLibrary.verifyErrorMessageisDisplayed("Message is required");
		if (errorMsgforeName && errorMsgEmail && errorMsgMessage) {
			CommonLibrary.logReport(true, "Errors are no longer displayed", "warningsCheckAgain");
		} else {
			CommonLibrary.logReport(false, "validateContactPage: Errors are  displayed", "warningsCheckFailAgain");
		}
		CommonLibrary.flushExtentReport();

	}

	// Test Case 2
	@SuppressWarnings("deprecation")
	@Test
	public void submitContactPage() throws Exception {
		PlanItLibrary.LaunchContactPage();
		PlanItLibrary.fillContactPageDetails("TestUser", "testuser123@gmail.com", "Please contact me. I have queries",
				"88562458", "Family");
		CommonLibrary.driver.findElement(By.xpath(ContactPage.submitButtonXpath)).click();
		CommonLibrary.waitForOperationInSeconds(10);
		CommonLibrary.waitForElementToBeVisible(ContactPage.backButton);
		try {
			if (CommonLibrary.driver.findElement(By.xpath(ContactPage.contactSubmitSuccessMessage)).isDisplayed()) {
				CommonLibrary.logReport(true, "submitContactPage: Contacts Submitted", "Submitted");
			} else {
				CommonLibrary.logReport(false, "submitContactPage: Contact not submitted", "NotSubmitted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			CommonLibrary.logReport(false, "submitContactPage: Exception running the test case",
					"Exception occured while ruuning the test case");
		}
		CommonLibrary.flushExtentReport();

	}

	// Test Case 3
	// Even with invalid data - application is accepting users to submit * This
	// test case just validates if errors are present before submitting.
	@SuppressWarnings("deprecation")
	@Test
	public void validateContactPageFields() throws Exception {

		PlanItLibrary.LaunchContactPage();
		PlanItLibrary.fillContactPageDetails("TestUser", "testuser", "Please contact me. I have queries",
				"sampleErrorPhone", "Family");
		CommonLibrary.waitForOperationInSeconds(10);
		boolean errorMessageEmail = PlanItLibrary.verifyErrorMessageByText("Please enter a valid email");
		boolean errorMessagePhone = PlanItLibrary.verifyErrorMessageByText("Please enter a valid telephone number");
		if (errorMessagePhone && errorMessageEmail) {
			CommonLibrary.logReport(true, "Errors for valid data are displayed for Telephone and email id field",
					"warningsCheckAgain1");
		} else {
			CommonLibrary.logReport(false, "Errors are  not displayed", "warningsCheckFailAgain1");
		}
		CommonLibrary.flushExtentReport();
	}

	// Test Case 4
	@SuppressWarnings("deprecation")
	@Test
	public void validateItemsInCart() throws Exception {
		PlanItLibrary.LaunchShopPage();
		PlanItLibrary.addItemstoCart("Funny Cow", 2);
		PlanItLibrary.addItemstoCart("Fluffy Bunny", 1);
		CommonLibrary.driver.findElement(By.xpath(PlanItLibrary.cartHeaderButton)).click();
		boolean cartCount = PlanItLibrary.verifyCartItemsCount("Funny Cow", "2");
		boolean cartItemsCount = PlanItLibrary.verifyCartItemsCount("Fluffy Bunny", "1");
		if (cartCount && cartItemsCount) {
			CommonLibrary.logReport(true, "Cart Item values verified. The number of items is the same as added. ",
					"CartItems");
		} else {
			CommonLibrary.logReport(false, "Cart Item values verified. The number of items is not the same as added.",
					"CartItemsList");
		}
		CommonLibrary.flushExtentReport();
	}

	@AfterClass
	public static void tearDown() {
		CommonLibrary.driver.quit();
	}

}
