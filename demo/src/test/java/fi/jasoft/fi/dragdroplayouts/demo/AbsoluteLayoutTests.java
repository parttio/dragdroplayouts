package fi.jasoft.fi.dragdroplayouts.demo;

import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class AbsoluteLayoutTests extends LayoutTest {

	@Test
	public void testDraggingAndDroppingButton() throws Exception {
			
		openUrl();
		
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