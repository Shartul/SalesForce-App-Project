package com.qtpselenium.app.salesforce.util;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebConnector {
	// initialize properties file
	// logging
	// selenium/webdriver layer -  selenium commands
	
	Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
	Properties OR = null;
	Properties CONFIG=null;
	WebDriver driver =null;
	WebDriver mozilla=null;
	WebDriver chrome=null;
	WebDriver ie=null;
	static WebConnector w;
	public DesiredCapabilities chromeCaps;
	
	private WebConnector(){
		
		if(OR==null){
			try{
				// initialize OR
				OR = new Properties();
				
				// Auto_Home is a "user variable" set till the path of eclipse workspace i.e. Auto_Home=C:\workspace
				// Please make sure to replace with absolute path of files if needed.
				FileInputStream fs  = new FileInputStream(System.getenv("Auto_Home")+"\\SalesForce-App-Project\\src\\main\\java\\com\\qtpselenium\\app\\salesforce\\config\\OR.properties");
				OR.load(fs);
				
				// initialize CONFIG to corresponding env
				CONFIG= new Properties();
				fs  = new FileInputStream(System.getenv("Auto_Home")+"\\SalesForce-App-Project\\src\\main\\java\\com\\qtpselenium\\app\\salesforce\\config\\"+OR.getProperty("testEnv")+"_config.properties");
				CONFIG.load(fs);
				
				//System.out.println(OR.getProperty("loginusername"));
				//System.out.println(CONFIG.getProperty("loginURL"));
				
				
			}catch(Exception e){
				System.out.println("Error on intializing properties files");
			}
			
			
			
		}
		
	}
	
	/// ****************Application Independent functions************************ ///

	// opening the browser
	public void openBrowser(String browserType){
		log("Opening browser "+browserType);
		if(browserType.equals("Mozilla") && mozilla==null){
			// Please set the Firefox binary path on your system and initialize(if needed)
			System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla\\firefox.exe");
			driver = new FirefoxDriver();
			mozilla=driver;
		}else if(browserType.equals("Mozilla") && mozilla!=null){
			driver=mozilla;
		}else if(browserType.equals("Chrome") && chrome==null){
			 chromeCaps = DesiredCapabilities.chrome();
			    chromeCaps.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
			    HashMap<String, String> chromePreferences = new HashMap<String, String>();
		        chromePreferences.put("profile.password_manager_enabled", "false");
		        chromeCaps.setCapability("chrome.prefs", chromePreferences);
		     // Please set the Chrome Driver server exe path on your system and initialize
				System.setProperty("webdriver.chrome.driver", "C:\\selenium-grid\\chromedriver.exe");
				driver=new ChromeDriver(chromeCaps);
			chrome=driver;
		}else if(browserType.equals("Chrome") && chrome==null){
			driver=chrome;
		}
		
		else if(browserType.equals("IE")){
			// Please set the IE Driver server exe path on your system and initialize
			System.setProperty("webdriver.ie.driver", "C:\\selenium-grid\\IEDriverServer.exe");
			// set the IE server exe path and initialize
		}
		// max
		driver.manage().window().maximize();
		// implicit wait
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	// navigates to a URL
	public void navigate(String URL){
		log("Naviating to "+CONFIG.getProperty(URL));
		driver.get(CONFIG.getProperty(URL));
	}
	// clicking on any object
	public void click(String objectName){
		log("Clicking on " + objectName);
		driver.findElement(By.xpath(OR.getProperty(objectName))).click();
	}
	
	public void type(String text, String objectName){
		log("Typing in " + objectName);
		driver.findElement(By.xpath(OR.getProperty(objectName))).sendKeys(text);
	}
	
	public void select(String text, String objectName){
		log("Selecting from "+ objectName);
		driver.findElement(By.xpath(OR.getProperty(objectName))).sendKeys(text);
	}
	
	public boolean isElementPresent(String objectName){
		log("Checking object presence "+ objectName);
		int count = driver.findElements(By.xpath(OR.getProperty(objectName))).size();
		if(count==0)
			return false;
		else
			return true;
	}

	
	
	
	/// ****************Application dependent functions************************ ///
	
	public boolean isLoggedIn(){
		
		if(isElementPresent("searchText"))
			return true;
		else
			return false;
	}
	
	public void doDefaultLogin(){
		navigate("loginURL");
		type(CONFIG.getProperty("defaultUsername"), "loginusername");
		type(CONFIG.getProperty("defaultPassword"), "loginpassword");
		click("loginButton");
	}
	
	
	/********Singleton**********/
	public static WebConnector getInstance(){
		if(w==null)
			w= new WebConnector();
		
		return w;
	}
	
	/**************Logging***************/
	public void log(String msg){
		APPLICATION_LOGS.debug(msg);
	}
	
	
	
	
	
	
	
	
}
