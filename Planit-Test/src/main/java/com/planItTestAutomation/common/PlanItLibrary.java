package com.planItTestAutomation.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.planItTesting.pages.ContactPage;

/**
 * The PlanIt class contains methods, variables to perform common operations on planit website.
 * @author Ram Pavan Kumar
 * @since 15-Mar 2021
 * @version 1.0
 */

public class PlanItLibrary extends CommonLibrary{
	
	public static String contactHeaderButton = "//a[contains(text(),'Contact')]";
	public static String shopHeaderButton = "//a[contains(text(),'Shop')]";
	public static String submitButtonContactPage = "//a[contains(text(),'Submit')]";
	public static String cartHeaderButton = "//a[contains(text(),'Cart')]";
	
	
	public static void LaunchContactPage() throws Exception {
		CommonLibrary.driver.get(appURL);
		CommonLibrary.logReport(true,"HomeScreen launched", "HomeScreen");
		driver.findElement(By.xpath(contactHeaderButton)).click();
		CommonLibrary.waitForElementToBeVisible(submitButtonContactPage);
				
	}
	
	public static void LaunchShopPage() throws Exception {
		CommonLibrary.driver.get(appURL);
		CommonLibrary.logReport(true,"HomeScreen launched", "HomeScreen");
		driver.findElement(By.xpath(shopHeaderButton)).click();
		CommonLibrary.waitForElementToBeVisible(submitButtonContactPage);
	}
	
	public static boolean verifyErrorMessageByText(String name)
	{
		try{
		WebElement ErrorMessage = CommonLibrary.driver.findElement(By.xpath("//span[text()='"+name+"']"));
		return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		
	}	
	
	public static void fillContactPageDetails(String name, String email, String message, String telephone, String surname)
	{
		CommonLibrary.driver.findElement(By.xpath(ContactPage.foreNameXpath)).click();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.foreNameXpath)).sendKeys(name);
		CommonLibrary.driver.findElement(By.xpath(ContactPage.emailXpath)).click();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.emailXpath)).sendKeys(email);
		CommonLibrary.driver.findElement(By.xpath(ContactPage.messageXpath)).click();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.messageXpath)).sendKeys(message);
		CommonLibrary.driver.findElement(By.xpath(ContactPage.surNameXpath)).click();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.surNameXpath)).sendKeys(surname);
		CommonLibrary.driver.findElement(By.xpath(ContactPage.telephoneXpath)).click();
		CommonLibrary.driver.findElement(By.xpath(ContactPage.telephoneXpath)).sendKeys(telephone);
	}
	
	public static boolean verifyErrorMessageisDisplayed(String name)
	{
		try{
		WebElement ErrorMessage = CommonLibrary.driver.findElement(By.xpath("//span[text()='"+name+"']"));
		if(ErrorMessage.isDisplayed()){
			return false;
		}
		return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return true;
		}
		
	}
	
	public static void addItemstoCart(String name, int count) throws InterruptedException
	{
		
		WebElement we = CommonLibrary.driver.findElement(By.xpath("//h4[text()='"+name+"']/..//a[text()='Buy']"));
		for(int i=0;i<count;i++)
		{
			we.click();
			CommonLibrary.waitForOperationInSeconds(1);
		}
	}
	public static boolean verifyCartItemsCount(String name, String count)
	{
		WebElement countField = CommonLibrary.driver.findElement(By.xpath("//td[contains(text(),'"+name+"')]/..//input"));
		System.out.println(countField.getAttribute("value"));
		if(countField.getAttribute("value").equals(count)){
		return true;}
		else
		{
			return false;
		}
	}

}
