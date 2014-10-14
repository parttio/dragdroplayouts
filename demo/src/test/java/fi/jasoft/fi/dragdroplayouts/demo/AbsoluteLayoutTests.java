package fi.jasoft.fi.dragdroplayouts.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

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
