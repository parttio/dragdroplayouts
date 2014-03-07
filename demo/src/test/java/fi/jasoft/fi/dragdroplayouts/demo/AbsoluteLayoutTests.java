package fi.jasoft.fi.dragdroplayouts.demo;

import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class AbsoluteLayoutTests extends TestBenchTestCase {

	private WebDriver driver;

	private String baseUrl;

	/*
	 * Executed before the test is run
	 */
	@Before
	public void setUp() throws Exception {
		
		// Using the Firefox driver (firefox need to be installed)
		driver = TestBench.createDriver(new FirefoxDriver());
		
		// URL where the tested application can be accessed
		baseUrl = "http://localhost:8080";
	}

	/*
	 * Executed after the test has been run
	 */
	@After
	public void tearDown() throws Exception {
		
		// Terminate the driver
		driver.quit();
	}

	@Test
	public void testDraggingAndDroppingButton() throws Exception {
			
		// Open application page in the browser
		driver.get(concatUrl(baseUrl, "/#!dd-absolute-layout"));
		
		WebElement buttonWrapper = driver.findElement(By.xpath("//div[@id='button']/.."));
		
		String left = buttonWrapper.getCssValue("left");
		assertEquals("50px", left);
		
		// drag and drop button 100px to the right
		Actions builder = new Actions(driver);		
		Action dragAndDrop = builder.dragAndDropBy(buttonWrapper, 100, 0).build();
		dragAndDrop.perform();
		
		left = buttonWrapper.getCssValue("left");
		assertEquals("150px", left);		
	}

}