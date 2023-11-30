package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Pro", 4},
			{"iMac", "iMac",3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung","Samsung SyncMaster 941BW", 1},
		};
	}
	
	@Test(dataProvider= "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchPage=accPage.performSearch(searchKey);
		productInfoPage=searchPage.selectProduct(productName);
		int actImagesCount= productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	@Test
	public void productInfoTest() {
		searchPage=accPage.performSearch("MacBook");
		productInfoPage=searchPage.selectProduct("MacBook Pro");
		Map<String, String>actProductInfoMap=productInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productprice"), "$2,000.00");
		
		softAssert.assertAll();
		
	}
	@DataProvider
	public Object[][] getAddToCartProductTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Pro"},
			{"iMac", "iMac"},
			{"Samsung","Samsung SyncMaster 941BW"},
		};
	}
	@Test(dataProvider= "getAddToCartProductTestData")
	public void addToCartTest(String key, String value) {
		searchPage=accPage.performSearch(key);
		productInfoPage=searchPage.selectProduct(value);
		productInfoPage.enterQuantity(2);
		String actCartMsg=productInfoPage.addProductToCart();
		softAssert.assertTrue(actCartMsg.contains("Success"));
		softAssert.assertTrue(actCartMsg.contains(value));
		softAssert.assertEquals(actCartMsg, "Success: You have added "+value+" to your shopping cart!");
		softAssert.assertAll();
		
		
	}
	
	

}
