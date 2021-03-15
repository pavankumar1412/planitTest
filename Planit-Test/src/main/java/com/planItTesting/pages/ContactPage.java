package com.planItTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.planItTestAutomation.common.CommonLibrary;

public class ContactPage extends CommonLibrary{
	
	WebDriver driver;

	
	
	public static String submitButtonXpath = "//a[contains(text(),'Submit')]";
	public static String foreNameXpath = "//input[@id='forename']";
	public static String emailXpath = "//input[@id='email']";
	public static String messageXpath = "//textarea[@id='message']";
	public static String contactSubmitSuccessMessage = "//div[contains(@class,'alert-success')]";
	public static String backButton = "//a[contains(text(),'Back')]";
	public static String surNameXpath = "//input[@id='surname']";
	public static String telephoneXpath = "//input[@id='telephone']";
	
	

}
