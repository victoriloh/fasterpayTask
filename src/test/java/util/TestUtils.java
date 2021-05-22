package util;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import enums.TargetTypeEnum;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.plexus.util.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
//import verificationRequest.ForgotPassword;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//import static verificationRequest.DeferredPayment.alumniPaymentForDefferedRequest;
//import static verificationRequest.ProfileCreation.verifyProfileCreation;

/**
 * @author soli@seamfix.com
 */
public class TestUtils extends TestBase {


    /**
     * @param driver
     * @param screenshotName
     * @return
     * @throws IOException
     * @description to take a screenshot
     */
    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "/TestsScreenshots/" + screenshotName + dateName + ".png";

        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    /**
     * @param type
     * @param element
     * @throws InterruptedException
     * @description to scroll to a particular element on the page.
     */
    public static void scrollToElement(String type, String element) throws InterruptedException {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        WebElement scrollDown = null;

        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
        switch (targetTypeEnum) {
            case ID:
                scrollDown = getDriver().findElement(By.id(element));
                break;
            case NAME:
                scrollDown = getDriver().findElement(By.name(element));
                break;
            case CSSSELECTOR:
                scrollDown = getDriver().findElement(By.cssSelector(element));
                break;

            case XPATH:
                scrollDown = getDriver().findElement(By.xpath(element));
                break;

            default:
                scrollDown = getDriver().findElement(By.id(element));
                break;
        }
//        jse.executeScript("window.scrollBy(0,1000)");
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
//        jse.executeScript("arguments[0].scrollIntoView();", scrollDown);
        Thread.sleep(1000);
    }

    /**
     * @param type
     * @param element
     * @param value
     * @throws InterruptedException
     * @description to check if the expected text is present in the page.
     */
    public static void assertSearchText(String type, String element, String value) {

        StringBuffer verificationErrors = new StringBuffer();
        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
        String ttype = null;

        switch (targetTypeEnum) {
            case ID:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
            case NAME:
                ttype = getDriver().findElement(By.name(element)).getText();
                break;
            case CSSSELECTOR:
                ttype = getDriver().findElement(By.cssSelector(element)).getText();
                break;

            case XPATH:
                ttype = getDriver().findElement(By.xpath(element)).getText();
                break;

            default:
                ttype = getDriver().findElement(By.id(element)).getText();
                break;
        }

        try {
            Assert.assertEquals(ttype, value);
            testInfo.get().log(Status.INFO, value + " found");
        } catch (Error e) {
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error(value + " not found");
            testInfo.get().error(verificationErrorString);
        }
    }

    /**
     * @return number
     * @description to generate a 11 digit number.
     */
    public static String generateOrderId() {

        long y = (long) (Math.random() * 10000 + 0333330000L);
        String Surfix = "123";
        String num = Long.toString(y);
        String number = Surfix + num;
        return number;

    }

    public static String generateEmail(){
        long y = (long) (Math.random() * 100000 + 1000000L);
        String num = Long.toString(y);
        String suffix= "sfxtest@yopmail.com";
        String prefix= num;
        String email = prefix + suffix;
        return email;
    }

    /**
     * @param value
     * @return string value.
     * @throws InterruptedException
     * @description to convert string value to int value for calculations
     */
    public static Integer convertToInt(String value) throws InterruptedException {
        Integer result = null;
        String convertedString = value.replaceAll("[^0-9]", "");
        if (validateParams(convertedString)) {
            try {
                return result = Integer.parseInt(convertedString);
            } catch (NumberFormatException e) {
                testInfo.get().error("convertToInt  Error converting to integer ");
                testInfo.get().error(e);
            }
        }
        return result;
    }

    public static Long convertToLong(String value) {
        Long result = null;
        String convertedString = value.replaceAll("[^0-9]", "");
        if (validateParams(convertedString)) {
            try {
                return result = Long.parseLong(convertedString);
            } catch (NumberFormatException e) {
                testInfo.get().error("ConvertToLong  Error converting to long");
                testInfo.get().error(e);
            }
        }
        return result;
    }

    public static boolean validateParams(Object... params) {

        for (Object param : params) {
            if (param == null) {
                return false;
            }

            if (param instanceof String && ((String) param).isEmpty()) {
                return false;
            }

            if (param instanceof Collection<?> && ((Collection<?>) param).isEmpty()) {
                return false;
            }

            if (param instanceof Long && ((Long) param).compareTo(0L) == 0) {
                return false;
            }
            if (param instanceof Double && ((Double) param).compareTo(0D) == 0) {
                return false;
            }

            if (param instanceof Integer && ((Integer) param).compareTo(0) == 0) {
                return false;
            }

        }

        return true;
    }

    public static String addScreenshot() {

        TakesScreenshot ts = (TakesScreenshot) getDriver();
        File scrFile = ts.getScreenshotAs(OutputType.FILE);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64," + encodedBase64;
    }

    public static boolean isAlertPresents() {
        try {
            getDriver().switchTo().alert();
            return true;
        } // try
        catch (Exception e) {
            return false;
        } // catch
    }

    public static boolean isLoaderPresents() {
        try {
            getDriver().findElement(By.className("dataTables_processing")).isDisplayed();
            return true;
        }// try
        catch (Exception e) {
            return false;
        }// catch
    }

    public static void clickElement(String type, String element) {
        JavascriptExecutor ex = (JavascriptExecutor) getDriver();
        WebElement clickThis = null;
        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(type);
        switch (targetTypeEnum) {
            case ID:
                clickThis = getDriver().findElement(By.id(element));
                break;
            case NAME:
                clickThis = getDriver().findElement(By.name(element));
                break;
            case CSSSELECTOR:
                clickThis = getDriver().findElement(By.cssSelector(element));
                break;
            case XPATH:
                clickThis = getDriver().findElement(By.xpath(element));
                break;
            default:
                clickThis = getDriver().findElement(By.id(element));
        }
        ex.executeScript("arguments[0].click()", clickThis);
    }

    public static Calendar stringToDate(String dateString) {

        Calendar dateDate = Calendar.getInstance();

        try {
            String[] dateArray = dateString.split("-");
            int year = Integer.valueOf(dateArray[0]);
            int month = Integer.valueOf(dateArray[1]) - 1;
            int day = Integer.valueOf(dateArray[2]);

            dateDate.set(year, month, day);
        } catch (NumberFormatException e) {
            String[] dateArray = dateString.split("/");
            int month = Integer.valueOf(dateArray[0]) - 1;
            int day = Integer.valueOf(dateArray[1]);
            int year = Integer.valueOf(dateArray[2]);

            dateDate.set(year, month, day);
        }

        return dateDate;
    }


    public static String generateName() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public static String CheckBrowser() {
        //Get Browser name and version.
        Capabilities caps = ((RemoteWebDriver) getDriver()).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getVersion();

        //return browser name and version.
        String os = browserName + " " + browserVersion;

        return os;
    }


    public static WebElement getElement(String type, String locator){
        type = type.toLowerCase();

        if (type.equals("id")){
            return getDriver().findElement(By.id(locator));
        }
        else if(type.equals("xpath")){
            return getDriver().findElement(By.xpath(locator));
        }
        else if (type.equals("name")){
            return getDriver().findElement(By.name(locator));
        }
        else if (type.equals("linkText")){
            return getDriver().findElement(By.linkText(locator));
        }
        else if (type.equals("tagName")){
            return getDriver().findElement(By.tagName(locator));
        }
        else if (type.equals("css")){
            return getDriver().findElement(By.cssSelector(locator));
        }
        else if (type.equals("partialLinkText")){
            return getDriver().findElement(By.partialLinkText(locator));
        }
        else if (type.equals("className")){
            return getDriver().findElement(By.className(locator));
        }
        else {
            System.out.println("locator type not supported");
            return null;
        }
    }

    public static ExtentTest elementIsPresent(String elementType, String locator, String message){

        WebElement elementPresent = null;

        TargetTypeEnum targetTypeEnum = TargetTypeEnum.valueOf(elementType);
        switch (targetTypeEnum) {
            case ID:
                try{
                    elementPresent = getDriver().findElement(By.id(locator));
                }catch (Exception e){}
                break;
            case NAME:
                try{
                    elementPresent = getDriver().findElement(By.name(locator));
                }catch (Exception e){}
                break;
            case CSSSELECTOR:
                try{
                    elementPresent = getDriver().findElement(By.cssSelector(locator));
                }catch (Exception e){}
                break;
            case XPATH:
                try{
                    elementPresent = getDriver().findElement(By.xpath(locator));
                }catch (Exception e){}
                break;
            default:
                try{
                    elementPresent = getDriver().findElement(By.id(locator));
                }catch (Exception e){}
        }
        if(elementPresent != null){
            return testInfo.get().log(Status.INFO, message+" is present");

        }
        else {
            return testInfo.get().fail(message+" is not present");
        }
    }


    @Parameters({"testEnv"})
    public static void navToYopmail(String testEnv, String email)
            throws Exception {
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("resources/" + testEnv + "/data.conf.json"));
        JSONObject envs = (JSONObject) config.get("NavigateToYopmail");
        String link = (String) envs.get("link");

        String currentHandle = getDriver().getWindowHandle();
        ((JavascriptExecutor) getDriver()).executeScript("window.open()");
        Thread.sleep(1000);
        Set<String> handles = getDriver().getWindowHandles();
        for (String actual : handles) {
            if (!actual.equalsIgnoreCase(currentHandle)) {
// switching to the opened tab
                getDriver().switchTo().window(actual);
                getDriver().get(link);
// Navigate To YOPMAIL
                String mail = "Open a New Tab and Navigate to YOPMAIL.";
                Markup a = MarkupHelper.createLabel(mail, ExtentColor.BLUE);
                testInfo.get().info(a);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));

                TestUtils.assertSearchText("CSSSELECTOR", "h1", "YOPmail : Disposable and Free email address.");

                String mail1 = "Search for " + email + " FasterPay Mail.";
                Markup b = MarkupHelper.createLabel(mail1, ExtentColor.BLUE);
                testInfo.get().info(b);
                Thread.sleep(1000);
                getDriver().findElement(By.id("login")).sendKeys(email);
                Thread.sleep(1000);
                TestUtils.clickElement("CSSSELECTOR", "input.sbut");
                try {
                    if (getDriver().findElement(By.id("r_parent")).isDisplayed()) {
                        TestUtils.clickElement("ID", "recaptcha-anchor");
                    }
                } catch (Exception e1) {
                }

                TestUtils.assertSearchText("XPATH", "//div[@id='webmailhaut']/table[2]/tbody/tr/td/div[3]", email);
                Thread.sleep(4000);
                try {
                    Alert alert = getDriver().switchTo().alert();
                    Thread.sleep(500);
                    alert.accept();
                } catch (Exception e1) {
                }

                getDriver().switchTo().frame("ifinbox");
                TestUtils.clickElement("XPATH", "//*[contains(text(),'FasterPay')]");

                getDriver().switchTo().defaultContent();
                getDriver().switchTo().frame("ifmail");
                String nameReciept = getDriver().findElement(By.xpath("//div[@id='mailhaut']/div")).getText();
                TestUtils.assertSearchText("XPATH","//div[@id='mailhaut']/div",nameReciept);
                TestUtils.assertSearchText("XPATH","//*[@id=\"mailmillieu\"]/div[2]/table[3]/tbody/tr/th[1]/table/tbody/tr/th/p/b","Payment successful");
                getDriver().close();
            }
        }
    }

    public static void testTitle(String phrase) {
        String word = "<b>"+phrase+"</b>";
        Markup w = MarkupHelper.createLabel(word, ExtentColor.BLUE);
        testInfo.get().info(w);
    }



}
