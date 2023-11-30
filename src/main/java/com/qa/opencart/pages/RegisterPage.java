package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By cnfpassword = By.id("input-confirm");

	private By agreeChkBox = By.name("agree");
	private By contBtn = By.xpath("//input[@type='submit' and @value='Continue']");

	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']/input[@type='radio']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']/input[@type='radio']");

	private By succMessage = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public boolean registerUser(String fName, String lName, String email, String telphone, String pwd,
			String subscribe) {

		eleUtil.waitForElementVisible(firstName, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(fName);
		eleUtil.doSendKeys(lastName, lName);
		eleUtil.doSendKeys(emailId, email);
		eleUtil.doSendKeys(telephone, telphone);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doSendKeys(cnfpassword, pwd);

		if (subscribe.equalsIgnoreCase("Yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doActionsClick(agreeChkBox);
		eleUtil.doClick(contBtn);

		String successMesg = eleUtil.waitForElementPresence(succMessage, AppConstants.DEFAULT_MEDIUM_TIME_OUT)
				.getText();
		System.out.println("User Registration Success Message: " + successMesg);

		if (successMesg.contains(AppConstants.USER_REG_SUCCESS_MESSAGE)) {

			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;

		}
		return false;

	}

}
