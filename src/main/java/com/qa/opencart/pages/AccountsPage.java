package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	

	By logoutLink = By.linkText("Logout");
	By acctHeaders = By.cssSelector("div#content h2");
	By search = By.name("search");
	By searchIcon= By.cssSelector("#search button");

	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil= new ElementUtil(driver);
	}

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
		System.out.println("Accounts Page Title is :" + title);
		return title;
	}

	public String getAccPageURL() {
		String url = eleUtil.waitForUrlContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCIUNTS_PAGE_URL_FRACTION_VALUE);
		System.out.println("Page URL is: " + url);
		return url;

	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	public boolean isSearchExist() {
		return eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	public List<String> getAccountsPageHeadersList() {
		List<WebElement> acctHeadersList = eleUtil.waitForElementsVisible(acctHeaders, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> acctHeadersValList= new ArrayList<String>();
		
		for(WebElement e: acctHeadersList) {
			String text=e.getText();
			acctHeadersValList.add(text);
		}
		return acctHeadersValList;
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
			
		}
		else {
			System.out.println("Search field is not present on the page....");
			return null;
		}
		
	}

}
