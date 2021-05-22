package util;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author soli
 * 
 * Using herokuapp for image uploading and download for webDriver remote.
 * http://the-internet.herokuapp.com/upload
 * http://the-internet.herokuapp.com/download
 *
 */
public class FileLoader extends TestBase {
	
	public static String pdf = System.getProperty("user.dir") + "/files/Managementl.pdf";
	
	
	public static void upLoadFile () throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		
		
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/upload");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
		
		getDriver().findElement(By.id("file-upload")).sendKeys(pdf);
		
		getDriver().findElement(By.id("file-submit")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		assertEquals(getDriver().findElement(By.xpath("//h3")).getText(), "File Uploaded!");
		
	}
	
	@Test 
	public static void downLoadFile (String myURL) throws Exception {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		Thread.sleep(1000);
		getDriver().navigate().to("http://the-internet.herokuapp.com/download");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3")));
		
		getDriver().findElement(By.linkText("Managementl.pdf")).click();
		Thread.sleep(1000);
		
		// Navigate back to url
	    Thread.sleep(500);
	    getDriver().get(myURL);
	}
	

}
