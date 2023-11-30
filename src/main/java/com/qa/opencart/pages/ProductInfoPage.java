package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private Map<String , String> productInfoMap;

	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By prodDescHeasers= By.cssSelector("div.cpt_product_description b");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPricingData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity= By.id("input-quantity");
	private By addToCartBtn= By.id("button-cart");
	private By cartSuccessMessg= By.cssSelector("div.alert.alert-success");
	

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);

	}

	public String getProductHeaderValue() {
		String productHeaderVal = eleUtil.doElementGetText(productHeader);
		System.out.println("product header : " + productHeaderVal);
		return productHeaderVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count : " + imagesCount);
		return imagesCount;

	}
	
	public void enterQuantity(int qty) {
		System.out.println("Product quqntity: "+qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));  //convert int into string because sendKeys method take only String 
		
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String succMsg=eleUtil.waitForElementVisible(cartSuccessMessg, AppConstants.DEFAULT_LONG_TIME_OUT).getText();
		
		StringBuilder sb = new StringBuilder(succMsg);
		String mesg=sb.substring(0,succMsg.length()-1).replace("\n", "").toString();
		System.out.println("Cart SuccessMessage: "+mesg);
		return mesg;
	}
	
	public Map<String, String> getProductInfo() {
		
	//	productInfoMap= new HashMap<String, String>();  //doesn't maintain the order
		productInfoMap= new LinkedHashMap<String, String>(); // maintain the order
	//	productInfoMap= new LinkedHashMap<String, String>();// TreeMap maintain the Alphabatic (sorted) order 
		
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData(); 
		System.out.println(productInfoMap);
		return productInfoMap;
		
		
		
		
		
	}
	//fetching the meta data:
	private void getProductMetaData() {
		//meta data:
				List<WebElement> metaList=eleUtil.getElemnts(productMetaData);
				for(WebElement e: metaList) {
					String metaText= e.getText();
					String metaIfo[]=metaText.split(":");
					String key=metaIfo[0].trim();
					String value= metaIfo[1].trim();
					productInfoMap.put(key, value);
				}
		
	}
	//fetching the meta data:
	private void getProductPriceData() {
		//pricing data:
		List<WebElement> priceList=eleUtil.getElemnts(productPricingData);
		String price=priceList.get(0).getText();
		String exTax= priceList.get(1).getText();
		String exTaxVal=exTax.split(":")[1].trim();
		
		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", exTaxVal);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
