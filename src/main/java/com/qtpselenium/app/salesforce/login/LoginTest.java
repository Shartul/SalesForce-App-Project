package com.qtpselenium.app.salesforce.login;



import org.junit.Assert;

import com.qtpselenium.app.salesforce.util.WebConnector;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

//step layer
public class LoginTest {
	WebConnector selenium = WebConnector.getInstance(); //new WebConnector();
	
	@Given("^Runmode is \"([^\"]*)\"$")
	public void Runmode_is(String Runmode) throws Throwable {
	}
	
	@Given("^I go to \"([^\"]*)\" on \"([^\"]*)\"$")
	public void I_go_to(String URL,String browser){
		System.out.println("I am going to "+ URL +" on  "+ browser);
		selenium.openBrowser(browser);
		selenium.navigate(URL);
	}
	
	@And("^I enter \"([^\"]*)\" as \"([^\"]*)\"$")
	public void I_enter(String object, String text) throws Throwable {
		selenium.type(text, object);
	}
	

	@And("^I click on \"([^\"]*)\"$")
	public void I_click_on(String object) throws Throwable {
	    // Express the Regexp above with the code you wish you had
	    selenium.click(object);
		throw new Exception();
	}
	
	@Then("^Login should be \"([^\"]*)\"$")
	public void Then_login_should_be(String expectedResult){
		System.out.println("Login - "+ expectedResult);
		boolean result=selenium.isElementPresent("searchText");
		String actualResult=null;
		
		if(result)
			actualResult="success";
		else
			actualResult="failure";
		
		Assert.assertEquals(expectedResult, actualResult);
	}
	

}
