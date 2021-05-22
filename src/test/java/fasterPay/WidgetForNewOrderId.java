package fasterPay;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.AssertsBase;
import util.TestBase;
import util.TestUtils;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

public class WidgetForNewOrderId extends TestBase {
    @Test
    @Parameters("testEnv")
    public void emptyCardDetails(String testEnv) throws Exception{
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        getDriver().findElement(By.id("merchant_order_id")).clear();
        String orderId1 = TestUtils.generateOrderId();
        getDriver().findElement(By.id("merchant_order_id")).sendKeys(orderId1);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH","//div[@id='fp-widget-container']/div/div[2]/div/p","You are now running in Test Mode. Please do not use your real card details. Learn More.");
        AssertsBase.assertOrderDescAndAmount();
        TestUtils.testTitle("Check when user no user is logged in, the email field should be on the card details page");
        TestUtils.assertSearchText("XPATH","//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[10]/div/div/div[1]","Pay with Credit or Debit Card");
        TestUtils.elementIsPresent("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[1]/div/div/input","Email Field");
        TestUtils.elementIsPresent("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/div/input","First Name Field");
        TestUtils.elementIsPresent("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/div/input","Last Name Field");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[3]/div/div/div[2]/iframe"))));
        TestUtils.elementIsPresent("NAME","card[number]","Card Number Field");
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/div/div[2]/iframe"))));
        TestUtils.elementIsPresent("NAME","card[exp]","MM/YY Field");
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/div/div[2]/iframe"))));
        TestUtils.elementIsPresent("NAME","card[cvv]","Card CVV/CVC Field");
        getDriver().switchTo().defaultContent();
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[5]/div/span","By paying you agree to FasterPay Privacy Policy and Terms of Service.");
        TestUtils.elementIsPresent("XPATH","//*[@id=\"js-form-guest-checkout\"]/button/span","PAY BUTTON");

        TestUtils.testTitle("Check when user Attempts to Pay with no entry entered on the card details");
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/button/span")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[1]/div/ul[1]/li","Please fill in this field");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/ul/li","Please fill in this field");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/ul/li","Please fill in this field");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[3]/div/ul/li","Please enter your card number");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/ul/li","Please enter an expiry date");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/ul/li","Please enter CVV code");

    }

    @Test
    @Parameters("testEnv")
    public void invalidCardDetails(String testEnv) throws Exception{
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Invalid_Card_Details");
        String email = (String) envs.get("email");
        String firstName = (String) envs.get("firstName");
        String lastName = (String) envs.get("lastName");
        String cardNumber = (String) envs.get("cardNumber");
        String cardExpiry = (String) envs.get("cardExpiry");
        String cardCVV = (String) envs.get("cardCVV");
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[1]/div/div/input")).sendKeys(email);
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/div/input")).sendKeys(firstName);
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/div/input")).sendKeys(lastName);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[3]/div/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[number]")).sendKeys(cardNumber);
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[exp]")).sendKeys(cardExpiry);
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[cvv]")).sendKeys(cardCVV);
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[exp]")).click();
        getDriver().switchTo().defaultContent();

        Thread.sleep(100);
        TestUtils.testTitle("Check when user enters an invalid email("+email+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[1]/div/ul[1]/li","Please check your email address");
        TestUtils.testTitle("Check when user enters an invalid FirstName("+firstName+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/ul/li","Please check the cardholder first name");
        TestUtils.testTitle("Check when user enters an invalid LastName("+lastName+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/ul/li","Please check the cardholder last name");
        TestUtils.testTitle("Check when user enters an invalid Card Number("+cardNumber+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[3]/div/ul/li","Please check your card number");
        TestUtils.testTitle("Check when user enters an invalid Card Expiry date("+cardExpiry+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/ul/li","Please check your card expiry date");
        TestUtils.testTitle("Check when user enters an invalid Card CVV("+cardCVV+")");
        TestUtils.assertSearchText("XPATH","//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/ul/li","Please check your card CVV");

    }

    @Test
    @Parameters("testEnv")
    public void validCardDetails(String testEnv) throws Exception{
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Valid_Card_Details");
        String email = (String) envs.get("email");
        String firstName = (String) envs.get("firstName");
        String lastName = (String) envs.get("lastName");
        String cardNumber = (String) envs.get("cardNumber");
        String cardExpiry = (String) envs.get("cardExpiry");
        String cardCVV = (String) envs.get("cardCVV");

        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[1]/div/div/input")).clear();
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/div/input")).clear();
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/div/input")).clear();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[3]/div/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[number]")).clear();
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[exp]")).clear();
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[cvv]")).clear();
        getDriver().switchTo().defaultContent();
        TestUtils.testTitle("Enter a valid email("+email+"), firstName ("+firstName+"), lastName("+lastName+"), Card Number("+cardNumber+"), Card Expiry("+cardExpiry+"), Card cvv("+cardCVV+")");
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[1]/div/div/input")).sendKeys(email);
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[1]/div/input")).sendKeys(firstName);
        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[2]/div[2]/div/input")).sendKeys(lastName);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[3]/div/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[number]")).sendKeys(cardNumber);
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[1]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[exp]")).sendKeys(cardExpiry);
        getDriver().switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt((By.xpath("//*[@id=\"js-form-guest-checkout\"]/div[4]/div[2]/div/div[2]/iframe"))));
        getDriver().findElement(By.name("card[cvv]")).sendKeys(cardCVV);
        getDriver().switchTo().defaultContent();

        getDriver().findElement(By.xpath("//*[@id=\"js-form-guest-checkout\"]/button/span")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[21]/div[1]/div[1]/div[2]")));
        TestUtils.assertSearchText("XPATH","//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[21]/div[1]/div[1]/div[2]","TRANSACTION SUCCESSFUL");
        TestUtils.assertSearchText("XPATH","//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[21]/div[1]/div[1]/div[5]/button/span[1]/span","DOWNLOAD RECEIPT");
        String transactionCode = getDriver().findElement(By.xpath("//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[21]/div[1]/div[1]/div[4]")).getText();
        TestUtils.testTitle("Get the transaction code of the transaction");
        TestUtils.assertSearchText("XPATH","//*[@id=\"fp-widget-container\"]/div/div[3]/div/div[1]/div[21]/div[1]/div[1]/div[4]",transactionCode);

        String url = ("file:///C:/Users/SEAMFIX/Downloads/FasterPay_Transaction_PO-210520-34A4.pdf");
        URL pdfUrl = new URL(url);
        InputStream in = pdfUrl.openStream();
        BufferedInputStream bf = new BufferedInputStream(in);
        PDDocument doc = PDDocument.load(bf);
        int numberOfPages = getPageCount(doc);
        TestUtils.testTitle("The total number of pages " + numberOfPages);
        String content = new PDFTextStripper().getText(doc);
        TestUtils.testTitle("number of rows " + content.length());
        doc.close();
        Thread.sleep(1000);
    }


    public static int getPageCount(PDDocument doc) {
        //get the total number of pages in the pdf document
        int pageCount = doc.getNumberOfPages();
        return pageCount;

    }

    @Test
    @Parameters("testEnv")
    public void navigateToYopmai(String testEnv) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("NavigateToYopmail");
        String emailAddress = (String) envs.get("email");
        TestUtils.testTitle("verify user gets an email for payment receipt");
        TestUtils.navToYopmail(testEnv,emailAddress);
    }

    }

