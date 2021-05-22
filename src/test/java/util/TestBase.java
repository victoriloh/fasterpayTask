package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.browserstack.local.Local;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


public class TestBase {
    public static ExtentReports reports;
    public static ExtentHtmlReporter htmlReporter;
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
    private static OptionsManager optionsManager = new OptionsManager();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    public static String toAddress;
    private Local l;
    public String local = "local";

    @Parameters("testEnv")
    public static String myUrl(String testEnv) throws IOException, ParseException {
        String filePath = System.getProperty("user.dir") + File.separator + "files" + File.separator + "pay_form(1).html";

        String myUrl = filePath;
        if (testEnv.equalsIgnoreCase("stagingData")) {
            myUrl = System.getProperty("instance-url", filePath);
        } else {
            myUrl = System.getProperty("instance-url", filePath);
        }
        return myUrl;
    }


    @BeforeSuite
    @Parameters({"groupReport", "testEnv"})
    public void setUp(String groupReport, String testEnv) throws Exception {

        htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir") + groupReport));
        //	htmlReporter.loadXMLConfig(String.valueOf(new File(System.getProperty("user.dir") + "/resources/extent-config.xml")));
        htmlReporter.config().setDocumentTitle("ITranscript Report");
        htmlReporter.config().setReportName("Automation Test Reports for ITranscript");
        reports = new ExtentReports();
        reports.setSystemInfo("TEST ENVIRONMENT", myUrl(testEnv));
        reports.attachReporter(htmlReporter);
    }

    @Parameters({"myBrowser", "config", "environment", "server", "testEnv"})
    @BeforeClass
    public void beforeClass(String myBrowser, String config_file, String environment, String server, String testEnv) throws Exception {
        ExtentTest parent = reports.createTest(getClass().getName());
        parentTest.set(parent);
        if (server.equals(local)) {
            // Local Directory
            File classpathRoot = new File(System.getProperty("user.dir"));
            File chromeDriver = new File(classpathRoot, "chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
            driver.set(new ChromeDriver(optionsManager.getChromeOptions()));
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        getDriver().get(myUrl(testEnv));

    }


    @AfterClass
    public void afterClass() {
        getDriver().quit();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod(description = "fetch test cases name")
    public void register(Method method) throws InterruptedException {

        ExtentTest child = parentTest.get().createNode(method.getName());
        testInfo.set(child);
        testInfo.get().assignCategory("Test");
        testInfo.get().getModel().setDescription(TestUtils.CheckBrowser());
        if (TestUtils.isAlertPresents()) {
            getDriver().switchTo().alert().accept();
            Thread.sleep(1000);
        }

    }

    @AfterMethod(description = "to display the result after each test method")
    public void captureStatus(ITestResult result) throws IOException {
        for (String group : result.getMethod().getGroups())
            testInfo.get().assignCategory(group);
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = TestUtils.addScreenshot();
            testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
            testInfo.get().fail(result.getThrowable());
            getDriver().navigate().refresh();
        }
        else if (result.getStatus() == ITestResult.SKIP)
            testInfo.get().skip(result.getThrowable());
        else
            testInfo.get().pass(result.getName() +" Test passed");
        reports.flush();
    }

    @Parameters({"toMails", "groupReport"})
    @AfterSuite(description = "clean up report after test suite")
    public void cleanup(String toMails, String groupReport) {
        toAddress = System.getProperty("email_list", toMails);
    }
}
