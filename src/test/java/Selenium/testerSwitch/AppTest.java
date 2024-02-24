package Selenium.testerSwitch;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

//import javax.swing.text.html.HTMLDocument.Iterator;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {

		@Test
	public void Launch() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize(); // For maximize of chrome browser.
		String baseUrl = "https://petstore.com/";
		String expectedTitle = "Petstore.com";
		String actualTitle = "";

		driver.get(baseUrl);
		
		System.out.println("Launch is Successfull");
		
		
	
		// get the actual value of the title

		actualTitle = driver.getTitle();

		/*
		 * compare the actual title of the page with the expected one and print the
		 * result as "Passed" or "Failed"
		 */

		if (actualTitle.contentEquals(expectedTitle)) {
			System.out.println("Test Passed!");
		} else {
			System.out.println("Test Failed");
		}

		// *************Hard Assertion*****************//

		Assert.assertEquals(expectedTitle, actualTitle);

		// *************Soft Assertion*****************//

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actualTitle, expectedTitle, "Title is Matching");
		
		// *******IMPLICIT WAIT*********//

	
		//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Implicit wait -before Selenium 4
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //Implicit wait -after Selenium 4
		
		driver.findElement(By.xpath("//span[text()='Search']/parent::a")).click();

		// *******ACTION CLASS*********//

		Actions action = new Actions(driver);
		// action.sendKeys("//input[@type='search']","cat").build().perform();
		action.sendKeys("cat").build().perform();
		action.sendKeys(Keys.ENTER).build().perform();

		// ******* FOR Scrolling**********//

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // -->Scroll to most bottom vertically
		Thread.sleep(3000); // Avoid thread.sleep in practical here only using for observing behaviour.
		js.executeScript("window.scrollTo(0,3000)"); // -->Scroll to vertical y axis for 300 pixel
		Thread.sleep(3000);
		js.executeScript("window.scrollTo(0,0)");// -->Scroll back to vertical top

		// *******EXPLICIT WAIT*********//

		//WebDriverWait wait = new WebDriverWait(driver, 30); // Explicit wait - before Selenium 4
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));// Explicit wait - after Selenium 4
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Not today']")));
		driver.findElement(By.xpath("//button[text()='Not today']")).click();

		// *******FLUENT WAIT*********//

		Wait<WebDriver> waits = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(2))
				.pollingEvery(Duration.ofMillis(300)).ignoring(ElementNotInteractableException.class);
		waits.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Site navigation']/parent::button")));

		// *******Select class******** : used for selcting dropdown values.//

		driver.findElement(By.xpath("//span[text()='Site navigation']/parent::button")).click();
		// Select drpCountry = new Select(driver.findElement(By.name("country")));
		// drpCountry.selectByVisibleText("ANTARCTICA");

		driver.switchTo().newWindow(WindowType.TAB); //Provided in Selenium 4.
		driver.get("https://demo.automationtesting.in/Alerts.html");
		
		
		//driver.navigate().to("https://demo.automationtesting.in/Alerts.html");
		driver.findElement(By.xpath("//button[contains(text(),'click the button to display an  alert box:')]")).click();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		driver.findElement(By.xpath("//a[contains(text(),'Alert with OK & Cancel')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'click the button to display a confirm box')]")).click();
		Thread.sleep(3000);
		driver.switchTo().alert().dismiss();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(text(),'Alert with Textbox')]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'click the button to demonstrate the prompt box')]"))
				.click();
		Thread.sleep(3000);

		driver.switchTo().alert();

		Actions action_1 = new Actions(driver);
		//action_1.sendKeys(Keys.CLEAR);
		driver.switchTo().alert().sendKeys("Writing in console");
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		
		//********Window Handling***************//
		
		ArrayList<String> win =new ArrayList<>(driver.getWindowHandles());
		//driver.switchTo().window(win.get(0));
		Iterator<String>It=win.iterator();
		String ParentId=It.next();
		String childID= It.next();
		driver.switchTo().window(ParentId);
		

		softAssert.assertAll();
		driver.quit();

		}
}
