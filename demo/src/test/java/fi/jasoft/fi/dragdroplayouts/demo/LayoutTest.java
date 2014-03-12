package fi.jasoft.fi.dragdroplayouts.demo;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public class LayoutTest extends TestBenchTestCase {
	
	protected WebDriver driver;

	private String baseUrl;	

	/*
	 * Executed before the test is run
	 */
	@Before
	public void setUp() throws Exception {
				
		String hubHost = System.getProperty("TESTBENCH_HUB_HOST");
		String hubPort = System.getProperty("TESTBENCH_HUB_PORT");
		
		if(hubHost != null && hubPort != null){		
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			driver = TestBench.createDriver(new RemoteWebDriver(new URL("http://"+hubHost+":"+hubPort+"/wd/hub"), capabilities));			
		} else {
			driver = TestBench.createDriver(new FirefoxDriver());
		}
		
		// URL where the tested application can be accessed
		String host = System.getProperty("APPLICATION_HOST", "localhost");
		String port = System.getProperty("APPLICATION_PORT", "7676");
		baseUrl = "http://"+host+":"+port;
	}	

	/*
	 * Executed after the test has been run
	 */
	@After
	public void tearDown() throws Exception {
		
		// Terminate the driver
		driver.quit();
	}
	
	public final void openUrl(){
		driver.get(concatUrl(baseUrl, "/#!dd-absolute-layout"));
	}

}
