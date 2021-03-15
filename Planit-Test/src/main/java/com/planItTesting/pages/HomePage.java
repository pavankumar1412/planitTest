package com.planItTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	WebDriver driver;

	@FindBy(xpath = "//input[@id='forename']")
	WebElement contactHeader;
}
