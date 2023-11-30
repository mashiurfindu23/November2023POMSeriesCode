package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@Test
	public void accPageTitleTest() {
		String actualTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}

	@Test
	public void accPageUrlTest() {
		String actualUrl = accPage.getAccPageURL();
		Assert.assertTrue(actualUrl.contains(AppConstants.ACCIUNTS_PAGE_URL_FRACTION_VALUE));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("accounts page headers list: " + actualAccHeadersList);
		Assert.assertEquals(actualAccHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);

	}

	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccHeadersList = accPage.getAccountsPageHeadersList();
		System.out.println("Actual Accounts page headers list: " + actualAccHeadersList);
		System.out.println("Expected Accounts page headers list: " + AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);

	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
			
		};
	}

	@Test(dataProvider="getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchResultCount() > 0);
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Air"},
			{"MacBook", "MacBook Pro"},
			{"iMac", "iMac"},
			{"Apple", "Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"},
			
		};
	}

	@Test(dataProvider="getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage=accPage.performSearch(searchKey);
		
		if(searchPage.getSearchResultCount()>0) {
			productInfoPage=searchPage.selectProduct(productName);
			String actProductHeader= productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
		
		
	}

}
