package fasterPay;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.AssertsBase;
import util.TestBase;
import util.TestUtils;

import java.io.FileReader;
import java.util.ArrayList;

public class PayForm extends TestBase {

    @Test
    @Parameters("testEnv")
    public void emptyPayFormDetails(String testEnv) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Invalid_PayFormDetails");
        String amount = (String) envs.get("amount");
        String publicKey = (String) envs.get("publicKey");
        String recurringPeriod = (String) envs.get("recurringPeriod");
        String pingBackUrl = (String) envs.get("pingBackUrl");
        JSONObject envs1 = (JSONObject) config.get("Valid_PayFormDetails");
        String validAmount = (String) envs1.get("amount");
        String validPublicKey = (String) envs1.get("publicKey");
        String validRecurringPeriod = (String) envs1.get("recurringPeriod");
        String validPingBackUrl = (String) envs1.get("pingBackUrl");
        String validDescription = (String) envs1.get("description");
        String validRecurringName = (String) envs1.get("recurringName");
        String validRecurringSkuId = (String) envs1.get("recurringSkuId");

        TestUtils.testTitle("Check when user submits form with an empty order id");
        getDriver().findElement(By.id("merchant_order_id")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits the pay form with an empty amount");
        getDriver().findElement(By.id("merchant_order_id")).sendKeys("10ab");
        getDriver().findElement(By.name("amount")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits the pay form with an empty public key");
        getDriver().findElement(By.name("amount")).sendKeys(validAmount);
        getDriver().findElement(By.id("public-key-input")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits the pay form with an empty description");
        getDriver().findElement(By.id("public-key-input")).sendKeys(validPublicKey);
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        Thread.sleep(100);
        TestUtils.testTitle("Check when user submits the pay form with an empty recurring name");
        getDriver().findElement(By.name("description")).sendKeys(validDescription);
        getDriver().findElement(By.name("recurring_name")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH","//div[@id='fp-widget-container']/div/div[2]/div/p","You are now running in Test Mode. Please do not use your real card details. Learn More.");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits the pay form with an empty recurring sku id");
        getDriver().findElement(By.name("recurring_name")).sendKeys(validRecurringName);
        getDriver().findElement(By.name("recurring_sku_id")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH","//p","You are now running in Test Mode. Please do not use your real card details. Learn More.");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits the pay form with an empty recurring period, recurring trial amount, recurring trial period and empty pingback url");
        getDriver().findElement(By.name("recurring_sku_id")).sendKeys(validRecurringSkuId);
        getDriver().findElement(By.name("recurring_period")).clear();
        getDriver().findElement(By.name("recurring_trial_amount")).clear();
        getDriver().findElement(By.name("recurring_trial_period")).clear();
        getDriver().findElement(By.name("pingback_url")).clear();
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH","//p","You are now running in Test Mode. Please do not use your real card details. Learn More.");

//        ArrayList<String> tabs2 = new ArrayList<String> (getDriver().getWindowHandles());
//        getDriver().switchTo().window(tabs2.get(1));
//        getDriver().close();
//        getDriver().switchTo().window(tabs2.get(0));
    }
    @Test
    @Parameters("testEnv")
    public void invalidPayFormDetails(String testEnv) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("Invalid_PayFormDetails");
        String amount = (String) envs.get("amount");
        String publicKey = (String) envs.get("publicKey");
        String recurringPeriod = (String) envs.get("recurringPeriod");
        String pingBackUrl = (String) envs.get("pingBackUrl");


        getDriver().navigate().back();
        Thread.sleep(100);
        TestUtils.testTitle("Check when user submits form with an invalid amount");
        getDriver().findElement(By.name("amount")).clear();
        getDriver().findElement(By.name("amount")).sendKeys(amount);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits form with an invalid public key");
        getDriver().findElement(By.id("public-key-input")).clear();
        getDriver().findElement(By.id("public-key-input")).sendKeys(publicKey);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits form with an invalid recurring period");
        getDriver().findElement(By.name("recurring_period")).clear();
        getDriver().findElement(By.name("recurring_period")).sendKeys(recurringPeriod);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
        TestUtils.testTitle("Check when user submits form with an invalid pingback url");
        getDriver().findElement(By.name("pingback_url")).clear();
        getDriver().findElement(By.name("pingback_url")).sendKeys(pingBackUrl);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(100);
        TestUtils.assertSearchText("XPATH","//*[@id=\"payment-form-container\"]/div","Invalid requested parameters");
        getDriver().navigate().back();
    }

    @Test
    @Parameters("testEnv")
    public void validPayFormDetails(String testEnv) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs1 = (JSONObject) config.get("Valid_PayFormDetails");
        String validAmount = (String) envs1.get("amount");
        String validOrderId = (String) envs1.get("orderId");
        String validPublicKey = (String) envs1.get("publicKey");
        String validRecurringPeriod = (String) envs1.get("recurringPeriod");
        String validPingBackUrl = (String) envs1.get("pingBackUrl");
        String validDescription = (String) envs1.get("description");
        String validRecurringName = (String) envs1.get("recurringName");
        String validRecurringTrialAmount = (String) envs1.get("recurringTrialAmount");
        String validRecurringSkuId = (String) envs1.get("recurringSkuId");
        String validRecurringTrialPeriod = (String) envs1.get("recurringTrialPeriod");

        getDriver().findElement(By.id("merchant_order_id")).clear();
        getDriver().findElement(By.name("amount")).clear();
        getDriver().findElement(By.id("public-key-input")).clear();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("recurring_sku_id")).clear();
        getDriver().findElement(By.name("recurring_period")).clear();
        getDriver().findElement(By.name("recurring_trial_amount")).clear();
        getDriver().findElement(By.name("recurring_trial_period")).clear();
        getDriver().findElement(By.name("recurring_name")).clear();
        getDriver().findElement(By.name("pingback_url")).clear();
        getDriver().findElement(By.id("merchant_order_id")).sendKeys(validOrderId);
        getDriver().findElement(By.name("amount")).sendKeys(validAmount);
        getDriver().findElement(By.id("public-key-input")).sendKeys(validPublicKey);
        getDriver().findElement(By.name("description")).sendKeys(validDescription);
        getDriver().findElement(By.name("recurring_sku_id")).sendKeys(validRecurringSkuId);
        getDriver().findElement(By.name("recurring_period")).sendKeys(validRecurringPeriod);
        getDriver().findElement(By.name("recurring_trial_amount")).sendKeys(validRecurringTrialAmount);
        getDriver().findElement(By.name("recurring_trial_period")).sendKeys(validRecurringTrialPeriod);
        getDriver().findElement(By.name("pingback_url")).sendKeys(validPingBackUrl);
        getDriver().findElement(By.name("recurring_name")).sendKeys(validRecurringName);
        getDriver().findElement(By.xpath("/html/body/form/button")).click();
        Thread.sleep(2000);
        TestUtils.assertSearchText("XPATH","//div[@id='fp-widget-container']/div/div[2]/div/p","You are now running in Test Mode. Please do not use your real card details. Learn More. ");
        AssertsBase.assertOrderDescAndAmount();


    }
}
