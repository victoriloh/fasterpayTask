package util;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class AssertsBase extends TestBase {

    private static StringBuffer verificationErrors = new StringBuffer();
    public static void assertOrderDescAndAmount() throws Exception { //*[@id="fp-widget-container"]/div/div[2]/div[2]/div/div[2]/div[2]

        String orderDescription = TestUtils.getElement("xpath","//*[@id=\"fp-widget-container\"]/div/div[2]/div[2]/div/div[2]/div[2]").getText();
        String totalAmount = TestUtils.getElement("xpath","//*[@id=\"fp-widget-container\"]/div/div[2]/div[2]/div/div[1]/div[2]").getText();


        String NA = "EMPTY";

        String[] toList = {"ORDER DESCRIPTION:"+ orderDescription, "Total Amount:" + totalAmount};


        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertAlumniProfilePersonalInfo() throws Exception {
        String surname =  TestUtils.getElement("XPATH", "//input").getAttribute("value");
        String firstName =  TestUtils.getElement("XPATH", "//div[2]/div/div/input").getAttribute("value");
        String middlename = TestUtils.getElement("XPATH", "//div[3]/div/div/input").getAttribute("value");
        String email = TestUtils.getElement("XPATH", "//div[4]/div/div/input").getAttribute("value");
        String phoneNumber = TestUtils.getElement("XPATH", "//div[5]/div/div/input").getAttribute("value");

        String NA = "EMPTY";

        String[] toList = {"Surname:" + surname, "First-Name:" + firstName , "Middle-Name:" + middlename, "Email:" + email,"Phone-Number:" + phoneNumber};


        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertAlumiDetailsLocalDestination(String copyType) throws Exception {

        copyType.toLowerCase();
        String destinationPhone = null;
        String emailAddress = null;
        String yesUploaded = null;
        String notUploaded = null;

        String institution = TestUtils.getElement("ID","organization").getAttribute("value");
        String address = TestUtils.getElement("ID","address").getAttribute("value");
        String continent = TestUtils.getElement("ID","continent").getText();
        String country = TestUtils.getElement("ID","country").getText();
        String state = TestUtils.getElement("ID","state").getText();
        String city = TestUtils.getElement("ID","city").getText();

        String zipCode = TestUtils.getElement("ID","zipcode").getAttribute("value");
        if (copyType.equals("hardcopy")){
            destinationPhone = TestUtils.getElement("ID","phoneNumber").getAttribute("value");
            emailAddress = "Not Applicable";
        }else if (copyType.equals("softcopy")){
            emailAddress = TestUtils.getElement("ID","email").getAttribute("value");
            destinationPhone = "Not Applicable";
        }else {
            testInfo.get().error("Copy type does not exist");
        }

        yesUploaded = TestUtils.getElement("XPATH","//h4").getText();
        if (yesUploaded.equals(notUploaded)){
            testInfo.get().error("File not uploaded");
        }

        String NA = "EMPTY";

        String[] toList = {"Institution:" + institution, "Address:" + address , "Continent:" + continent, "Country:" + country,"State:" + state,"City:" + city
                /*"Reference Number:"+ referenceNumber*/, "Destination Contact phone:" + destinationPhone, "Destination Email Address:" + emailAddress, "Zip/Postcode:" +zipCode ,
				"File uploaded:" + yesUploaded};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void assertDeferPayment(String statusOfDeferred) throws Exception{

        String deferred;
        String noDeferred;
        if (statusOfDeferred.equals("deferred")){
            deferred = TestUtils.getElement("XPATH","//div[4]/div/span[2]").getText();
            noDeferred = TestUtils.getElement("XPATH","//div[4]/div/span[3]").getText();
        }else {
            deferred = TestUtils.getElement("XPATH","//div[4]/div/span[2]").getText();
            noDeferred = "Not Applicable";
        }
        String NA = "EMPTY";
        String[] toList = {"Deferred:"+ deferred, "Non Deferred:" + noDeferred};

        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void assertDeferredDashboard(String paymentStatus) throws Exception{
        String orderID = null;
        String alumnusName = TestUtils.getElement("XPATH","//td[2]").getText();
        String matricNo = TestUtils.getElement("XPATH","//td[3]").getText();
        String email = TestUtils.getElement("XPATH","//td[4]").getText();
        String verifiedDocs = TestUtils.getElement("XPATH","//td[5]").getText();
        String amount = TestUtils.getElement("XPATH","//td[6]").getText();
        String paymentMode = TestUtils.getElement("XPATH","//td[7]").getText();
        String paymentID = TestUtils.getElement("XPATH","//td[8]").getText();
        String status = TestUtils.getElement("XPATH","//td[9]").getText();
        String requestDate = TestUtils.getElement("XPATH","//td[10]").getText();

        if (paymentStatus.equalsIgnoreCase("pending")){
            orderID = "Not Available Yet";
        }else {
            orderID = TestUtils.getElement("XPATH","//td").getText();
        }

        String NA = "EMPTY";


        String[] toList = {"Order ID:"+ orderID, "Alumnus Name:" + alumnusName, "Matric No:"+matricNo ,"Email:"+email,"Verified Docs:"+ verifiedDocs,"Amount:"+ amount,"Payment Mode:"+paymentMode ,"Payment ID:"+paymentID,"Status:"+status,"Request Date:"+requestDate};

        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void assertCartinfo(String user) throws Exception{
        String deferPaymentStatus = null;

        String alumniName = TestUtils.getElement("XPATH","//mat-panel-title/div/div/span[2]").getText();
        String studentCategory = TestUtils.getElement("XPATH","//div[3]/span[2]").getText();
        String amount = TestUtils.getElement("XPATH","//div[4]/div/span").getText();
        if (user.equalsIgnoreCase("organization")){
            deferPaymentStatus = TestUtils.getElement("XPATH","//div[4]/div/span[2]").getText();
        }
        else{
            deferPaymentStatus = "Not applicable";
        }
        String NA = "EMPTY";

        String[] toList = {"Alumnus Name:" + alumniName, "Total:" + amount, "Defer Payment Status:" + deferPaymentStatus,"Student Category:" +studentCategory};

        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
    public static void assertCartDocuments(String numberOfDoc) throws Exception{
        String trancript = null;
        String certificate = null;
        String documents = null;
        String total = null;

        if (numberOfDoc.equalsIgnoreCase("three")){
            trancript = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div/span[2]").getText();
            certificate = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div[2]/span[2]").getText();
            documents = TestUtils.getElement("XPATH","//div/div/div[3]/span[2]").getText();
            total = TestUtils.getElement("XPATH","//div[4]/span[2]").getText();
        }else if (numberOfDoc.equalsIgnoreCase("two")){
            trancript = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div/span[2]").getText();
            certificate = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div[2]/span[2]").getText();
            total = TestUtils.getElement("XPATH","//div/div/div[3]/span[2]").getText();
            documents = "Not Applicable";
        }

        String NA = "EMPTY";

        String[] toList = {"Transcript:" + trancript, "Certificate ID:" + certificate, "Iunn Documents:" +documents, "Total:" + total};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
    public static void assertTotalAmountOfRequestInCart() throws Exception{
        String trancript = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div/span[2]").getText();
        String certificate = TestUtils.getElement("XPATH","//mat-expansion-panel/div/div/div/div/div[2]/span[2]").getText();
        String documents = TestUtils.getElement("XPATH","//div/div/div[3]/span[2]").getText();
        String total = TestUtils.getElement("XPATH","//div[4]/span[2]").getText();
        int actualTranscriptAmount = TestUtils.convertToInt(trancript)/100;
        int actualCertificateAmount = TestUtils.convertToInt(certificate)/100;
        int actualDocumentAmount = TestUtils.convertToInt(documents)/100;
        int actualTotalAmount = TestUtils.convertToInt(total)/100;

        int expectedTotalRequest = actualTranscriptAmount + actualCertificateAmount + actualDocumentAmount;
        try {
            Assert.assertEquals(actualTotalAmount,expectedTotalRequest);
            testInfo.get().log(Status.INFO,"Total Request Amount ("+ expectedTotalRequest+ ") is equal to the Transcript/Dob Amount ("
                    + actualTranscriptAmount + ") + Certificate Amount (" +actualCertificateAmount+") + Iunn Documents (" +actualDocumentAmount+").");
        }catch (Error e){
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            testInfo.get().error(verificationErrorString);
        }
    }


    public static void assertTotalRequestAmountSoftCopyProfiency() throws Exception{

        String transcriptAmount = TestUtils.getElement("ID","pBreakdownTranscript").getText();
        String proficiencyAmount =  TestUtils.getElement("ID","pBreakdownProficiency").getText();
        String TotalAmount = TestUtils.getElement("ID","rSoftcopyfeeproficiency").getText();

        int actualTranscriptAmount = TestUtils.convertToInt(transcriptAmount)/100;
        int actualProfiencyAmount = TestUtils.convertToInt(proficiencyAmount)/100;
        int actualTotalAmount = TestUtils.convertToInt(TotalAmount)/100;

        int expectedTotalRequest = actualTranscriptAmount + actualProfiencyAmount;
        try {
            Assert.assertEquals(actualTotalAmount,expectedTotalRequest);
            testInfo.get().log(Status.INFO,"Total Transcript request ("+ expectedTotalRequest+ ") is equal to the Transcript Amount ("
                    + actualTranscriptAmount + ") + Proficiency Amount (" +actualProfiencyAmount+").");
        }catch (Error e){
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            testInfo.get().error(verificationErrorString);
        }
    }



    public static void assertTotalRequestAmount() throws Exception{

        String transcriptAmount = TestUtils.getElement("ID","pBreakdownTranscript").getText();
        String proficiencyAmount =  TestUtils.getElement("ID","pBreakdownProficiency").getText();
        String courierAmount   =    TestUtils.getElement("xpath","//label/div[2]").getText();
        String TotalAmount = TestUtils.getElement("ID","rHardcopyfeeproficiency").getText();


        int actualTranscriptAmount = TestUtils.convertToInt(transcriptAmount)/100;
        int actualProfiencyAmount = TestUtils.convertToInt(proficiencyAmount)/100;
        int actualCourierAmount = TestUtils.convertToInt(courierAmount)/100;
        int actualTotalAmount = TestUtils.convertToInt(TotalAmount)/100;

        int expectedTotalRequest = actualTranscriptAmount + actualProfiencyAmount + actualCourierAmount;
        try {
            Assert.assertEquals(actualTotalAmount,expectedTotalRequest);
            testInfo.get().log(Status.INFO,"Total Transcript request ("+ expectedTotalRequest+ ") is equal to the Transcript Amount ("
                    + actualTranscriptAmount + ") + Proficiency Amount (" +actualProfiencyAmount +") +  Courier Amount Request ("+actualCourierAmount+").");
        }catch (Error e){
            verificationErrors.append(e.toString());
            String verificationErrorString = verificationErrors.toString();
            testInfo.get().error("Summation not equal");
            testInfo.get().error(verificationErrorString);
        }
    }
    public static void assertGetStartedVerificationSecondPage() throws Exception {

        String orgAddress = TestUtils.getElement("XPATH","//input").getAttribute("value");
        String phoneNumber = TestUtils.getElement("XPATH","//p[2]/mat-form-field/div/div/div[3]/input").getAttribute("value");
        String continent = TestUtils.getElement("XPATH","//mat-select/div/div").getText();
        String country =  TestUtils.getElement("XPATH","//div[2]/mat-form-field/div/div/div[3]/mat-select/div/div").getText();
        String city = TestUtils.getElement("XPATH","//div/mat-form-field/div/div/div[3]/input").getAttribute("value");
        String zipCode = TestUtils.getElement("XPATH","//div[2]/mat-form-field/div/div/div[3]/input").getAttribute("value");

        String NA = "EMPTY";

        String[] toList = {"Institution/Organization Address:" + orgAddress, "Organization Phone Number:" + phoneNumber , "Continent:" + continent, "country:" + country, "City:" + city, "Zipcode/Postcode:" + zipCode};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
    public static void assertVerificationReceipt() throws Exception {
        String orderId = TestUtils.getElement("XPATH","//div[2]/div/span[2]").getText();
        String transactionID = TestUtils.getElement("XPATH","//div[2]/span[2]").getText();
        String schoolName = TestUtils.getElement("XPATH","//div[2]/div/span").getText();
        String nameOnReceipt = TestUtils.getElement("XPATH","//div[2]/div/div/span").getText();
        String document = TestUtils.getElement("XPATH","//span[2]/span").getText();
        String amount1 = TestUtils.getElement("XPATH","//div[2]/div/div[2]").getText();
        String subTotal = TestUtils.getElement("XPATH","//div[3]/div/div/span[2]").getText();
        String total = TestUtils.getElement("XPATH","//div[3]/div[2]/div/div/span[2]").getText();

        String NA = "EMPTY";

        String[] toList = {"Order ID:" + orderId, "Transaction ID:" + transactionID,"School Name:"+schoolName, "Alumni Name:"+ nameOnReceipt,
        "Document paid for:" + document,"Amount of Request:" + amount1, "Sub Total:"+ subTotal,"Total:"+ total};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
    public static void AssertAlumiDetailsForeignDestination(String copyType) throws Exception {

        copyType.toLowerCase();
        String destinationPhone = null;
        String emailAddress = null;
        String yesUploaded = null;
        String notUploaded = null;

        String institution = TestUtils.getElement("ID","organization").getAttribute("value");
        String address = TestUtils.getElement("ID","address").getAttribute("value");
        String continent = TestUtils.getElement("ID","continent").getText();
        String country = TestUtils.getElement("ID","country").getText();
        String stateName = TestUtils.getElement("ID","state").getAttribute("value");
        String cityName = TestUtils.getElement("ID","city").getAttribute("value");


        String zipCode = TestUtils.getElement("ID","zipcode").getAttribute("value");
        if (copyType.equals("hardcopy")){
            destinationPhone = TestUtils.getElement("ID","phoneNumber").getAttribute("value");
            emailAddress = "Not Applicable";
        }else if (copyType.equals("softcopy")){
            emailAddress = TestUtils.getElement("ID","email").getAttribute("value");
            destinationPhone = "Not Applicable";
        }else {
            testInfo.get().info("Copy type does not exist");
        }
        yesUploaded = TestUtils.getElement("XPATH","//h4").getText();
        if (yesUploaded.equals(notUploaded)){
            testInfo.get().error("File not uploaded");
        }

        String NA = "EMPTY";

        String[] toList = {"Institution:" + institution, "Address:" + address , "Continent:" + continent, "Country:" + country,"State:" + stateName,"City:" + cityName
                /*"Reference Number:"+ referenceNumber*/, "Destination Contact phone:" + destinationPhone, "Destination Email Address:" + emailAddress, "Zip/Postcode:" +zipCode ,
                "File uploaded:" + yesUploaded};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Error e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }


    public static void AssertAlumiReview(String copyType) throws Exception {

		  copyType.toLowerCase();

		  String destinationPhone = null;
		  String emailAddress = null;
		  String referenceNumber = TestUtils.getElement("ID","reviewReferenceNumber").getText();
		  String institution = TestUtils.getElement("ID","reviewInstitution").getText();
		  String address = TestUtils.getElement("ID","reviewAddress").getText();
		  String continent = TestUtils.getElement("ID","reviewContinent").getText();
		  String country  = TestUtils.getElement("ID","reviewCountry").getText();
		  String state = TestUtils.getElement("ID","reviewState").getText();
		  String city = TestUtils.getElement("ID","reviewCity").getText();
		  String copy = TestUtils.getElement("ID","reviewRequestType").getText();
		  String zipCode = TestUtils.getElement("ID","reviewZip").getText();
		  String purpose = TestUtils.getElement("ID","reviewDocumentIntent").getText();
		  String profiencyIncluded = TestUtils.getElement("ID","reviewProficiency").getText();
		  if (copyType.equals("hardcopy")){
		  	destinationPhone = TestUtils.getElement("ID","reviewDestinationContactPhone").getText();
		  	emailAddress = "Not Applicable";
		  }else if (copyType.equals("softcopy")){
		  	emailAddress = TestUtils.getElement("ID","reviewDestinationEmail").getText();
		  	destinationPhone = "Not Applicable";
		  }else {
		  	testInfo.get().info("Copy type does not exist");
		  }
		  String NA = "";

		  String[] toList = {"Institution:" + institution, "Address:" + address , "Continent:" + continent, "Country:" + country,"State:" + state, "City:" + city,
				  "Destination Contact phone:" + destinationPhone, "Destination Email Address:" + emailAddress,"reference Number:" + referenceNumber, "Zip/Postcode:" +zipCode ,
				  "Copy type:" + copy, "Purpose:" + purpose,"English proficiency:"+profiencyIncluded};
		  for (String field : toList) {
			  String name = "";
			  String val = NA;
			  if(field.endsWith(":")) {
				  field=field + val;
			  }
			  try {
				  String[] fields = field.split(":");
				  name = fields[0];
				  val = fields[1];
				  Assert.assertNotEquals(val, NA);
				  testInfo.get().log(Status.INFO, name + " : " + val);
			  } catch (Exception e) {
				  testInfo.get().error(name + " : " + val);
			  }
		  }
	  }

	  public static void AssertSuccessfulPaymentsByOrg() throws Exception {

		  	String Date = getDriver().findElement(By.xpath("//td[2]")).getText();
		    String Amount = getDriver().findElement(By.xpath("//td[3]")).getText();
		    String RRR =getDriver().findElement(By.xpath("//td[4]")).getText();
		    String TransactionID  = getDriver().findElement(By.xpath("//td[5]")).getText();
		    String RespCode = getDriver().findElement(By.xpath("//td[6]")).getText();
		    String Alumus = getDriver().findElement(By.xpath("//td[7]")).getText();
		    String Matric_No = getDriver().findElement(By.xpath("//td[8]")).getText();
		    String Verification = getDriver().findElement(By.xpath("//li/span[2]")).getText();
	        String NA = "N/A";

	        String[] toList = {"Date:" + Date, "Amount:" + Amount, "RRR:" + RRR, "TransactionID:" + TransactionID,
	                "RespCode:" + RespCode, "Alumus:" + Alumus, "Matric_No:" + Matric_No, "Verification:" + Verification};
	        for (String field : toList) {
	            String name = "";
	            String val = NA;
	            try {
	                String[] fields = field.split(":");
	                name = fields[0];
	                val = fields[1];
	                Assert.assertNotEquals(val, NA);
	                testInfo.get().log(Status.INFO, name + " : " + val);
	            } catch (Error e) {
	                testInfo.get().error(name + " : " + val);
	            }
	        }
	    }
    public static void shippingCourier() throws InterruptedException {
        String head = null;
        String body = null;
        List<WebElement> headSize = getDriver().findElements(By.name("shippingMode"));
//        List<WebElement> headSize = getDriver().findElements(By.xpath("//label/div"));
        for(int i = 0; i < headSize.size(); i++ ){
            head = TestUtils.getElement("XPATH","//div["+(i+2)+"]/div/label/div").getText();
            body = TestUtils.getElement("XPATH","//div["+(i+2)+"]/div/label/div[2]").getText();

            String NA = "EMPTY";

            String[] toList = { head + ": " + body };
            for (String field : toList) {
                String name = "";
                String val = NA;
                if (field.endsWith(":")) {
                    field = field + val;
                }
                try {
                    String[] fields = field.split(":");
                    name = fields[0];
                    val = fields[1];
                    Assert.assertNotEquals(val, NA);
                    testInfo.get().log(Status.INFO, name + " : " + val);
                } catch (Error e) {
                    testInfo.get().error(name + " : " + val);
                }
            }
        }
    }
    public static void AssertNullPreview() throws Exception{
        String amount = TestUtils.getElement("XPATH", "//td[4]").getText();
        String NA = null;
        String[] toList = {"Amount:" + amount};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if (field.endsWith(":")) {
                field = field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception er) {
                testInfo.get().error(name + " : " + val);
            }
    }
    }

    public static void AssertNullPreviewNoProficiency() throws Exception{
        String total =  null;
        String amount = TestUtils.getElement("XPATH", "//td[4]").getText();
   //     total = TestUtils.getElement("ID","rHardcopyfee").getText();
        String NA = null;
        String[] toList = {"Amount:" + amount};
        for (String field : toList) {
            String name = "";
            String val = NA;
            if (field.endsWith(":")) {
                field = field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception er) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertPaymentPreviewHardCopy() throws Exception {

        String requestType = TestUtils.getElement("XPATH","//td").getText();
        String recieveInstitution = TestUtils.getElement("XPATH","//td[2]").getText();
        String copyTypeI = TestUtils.getElement("XPATH","//td[3]").getText();
        String amount = TestUtils.getElement("XPATH","//td[4]").getText();
        String transcript = TestUtils.getElement("ID","pBreakdownTranscript").getText();
        String profiency = TestUtils.getElement("ID","pBreakdownProficiency").getText();
        String total = TestUtils.getElement("ID","rHardcopyfeeproficiency").getText();

        String NA = null;

        String[] toList = {"Request Type:" + requestType , "Recieving Institution:" + recieveInstitution , "Copy Type:" + copyTypeI, "Amount:" + amount ,"Transcript Amount:" + transcript,"Proficiency Amount:" + profiency,"Total Amount:" + total };
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertRequestCart() throws Exception {

//        String requestType = TestUtils.getElement("XPATH","//tr["+i+"]//td["+i+"]").getText();
        String requestType = TestUtils.getElement("XPATH","//td[1]").getText();
        String recieveInstitution = TestUtils.getElement("XPATH","//td[2]").getText();
        String copyTypeI = TestUtils.getElement("XPATH","//td[3]").getText();
        String amount = TestUtils.getElement("XPATH","//td[4]").getText();
        String total = TestUtils.getElement("xpath","//td[4]").getText();

        String NA = null;

        String[] toList = {"Request Type:" + requestType , "Recieving Institution:" + recieveInstitution , "Copy Type:" + copyTypeI, "Amount:" + amount ,"Total Amount:" + total };
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

    public static void AssertPaymentPreviewHardCopyNoProficiency() throws Exception {

        String requestType = TestUtils.getElement("XPATH","//td").getText();
        String recieveInstitution = TestUtils.getElement("XPATH","//td[2]").getText();
        String copyTypeI = TestUtils.getElement("XPATH","//td[3]").getText();
        String amount = TestUtils.getElement("XPATH","//td[4]").getText();
        String transcript = TestUtils.getElement("ID","pBreakdownTranscript").getText();
        String total = TestUtils.getElement("ID","rHardcopyfee").getText();

        String NA = null;

        String[] toList = {"Request Type:" + requestType , "Recieving Institution:" + recieveInstitution , "Copy Type:" + copyTypeI, "Amount:" + amount ,"Transcript Amount:" + transcript,"Total Amount:" + total };
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }
    public static void AssertPaymentPreviewSoftCopy() throws Exception {

        String requestType = TestUtils.getElement("XPATH","//td").getText();
        String recieveInstitution = TestUtils.getElement("XPATH","//td[2]").getText();
        String copyTypeI = TestUtils.getElement("XPATH","//td[3]").getText();
        String amount = TestUtils.getElement("ID","softCopyTranscriptFee").getText();
        String transcript = TestUtils.getElement("ID","pBreakdownTranscript").getText();
        String profiency = TestUtils.getElement("ID","pBreakdownProficiency").getText();
        String total = TestUtils.getElement("ID","rSoftcopyfeeproficiency").getText();

        String NA = null;

        String[] toList = {"Request Type:" + requestType , "Recieving Institution:" + recieveInstitution , "Copy Type:" + copyTypeI, "Amount:" + amount ,"Transcript Amount:" + transcript,"Proficiency Amount:" + profiency,"Total Amount:" + total };
        for (String field : toList) {
            String name = "";
            String val = NA;
            if(field.endsWith(":")) {
                field=field + val;
            }
            try {
                String[] fields = field.split(":");
                name = fields[0];
                val = fields[1];
                Assert.assertNotEquals(val, NA);
                testInfo.get().log(Status.INFO, name + " : " + val);
            } catch (Exception e) {
                testInfo.get().error(name + " : " + val);
            }
        }
    }

	  public static void AssertPaymentReceipt() throws Exception {

		  
		  	String Organization = getDriver().findElement(By.xpath("//input")).getAttribute("value");
		    String Email = getDriver().findElement(By.xpath("//tr[2]/td[3]/input")).getAttribute("value");
		    String Date =getDriver().findElement(By.xpath("//tr[3]/td[3]/input")).getAttribute("value");
		    String TransactionID  = getDriver().findElement(By.xpath("//tr[3]/td[6]/input")).getAttribute("value");
		    String Amount_Paid = getDriver().findElement(By.xpath("//tr[7]/td/fieldset/table/tbody/tr/td[3]")).getText();
		    String Payment_Mode = getDriver().findElement(By.xpath("//tr[7]/td/fieldset/table/tbody/tr[2]/td[3]/input")).getAttribute("value");
		    String NA = "";

	        String[] toList = {"Organization:" + Organization, "Email:" + Email, "Date:" + Date, "TransactionID:" + TransactionID,
	                "Amount Paid:" + Amount_Paid, "Payment Mode:" + Payment_Mode};
	        for (String field : toList) {
	            String name = "";
	            String val = NA;
	            try {
	                String[] fields = field.split(":");
	                name = fields[0];
	                val = fields[1];
	                Assert.assertNotEquals(val, NA);
	                testInfo.get().log(Status.INFO, name + " : " + val);
	            } catch (Error e) {
	                testInfo.get().error(name + " : " + val);
	            }
	        }
	    }
	  
	  public static void AssertSuccessfulPaymentsByAlumni() throws Exception {
		  
		  	//Org Payment Review Details
		  
		  	String Date = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[2]")).getText();
		    String Amount = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[3]")).getText();
		    String RRR =getDriver().findElement(By.xpath("//form/table/tbody/tr/td[4]")).getText();
		    String TransactionID  = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[5]")).getText();
		    String RespCode = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[6]")).getText();
		    String Alumnus = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[7]")).getText();
		    String Matric_No = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[8]")).getText();
		    String Verification = getDriver().findElement(By.xpath("//form/table/tbody/tr/td[9]")).getText();
	        String NA = "";

	        String[] toList = {"Date:" + Date, "Amount:" + Amount, "RRR:" + RRR, "TransactionID:" + TransactionID,
	                "RespCode:" + RespCode, "Alumnus:" + Alumnus, "Matric_No:" + Matric_No, "Verification:" + Verification};
	        for (String field : toList) {
	            String name = "";
	            String val = NA;
	            try {
	                String[] fields = field.split(":");
	                name = fields[0];
	                val = fields[1];
	                Assert.assertNotEquals(val, NA);
	                testInfo.get().log(Status.INFO, name + " : " + val);
	            } catch (Error e) {
	                testInfo.get().error(name + " : " + val);
	            }
	        }
	    }


    public static void assertTextNotNull(String XPATH, String locator){

        if (XPATH.equals("XPATH") && getDriver().findElement(By.xpath(locator)) == null){
            testInfo.get().error("text is null");
        }
        else {
            String elementToLocate = getDriver().findElement(By.xpath(locator)).getText();
            TestUtils.assertSearchText(XPATH,locator,elementToLocate);
        }
    }

	  
	  public static void AssertPendingPaymentsByOrg() throws Exception {
		  
		   //Org Payment Review Details
		  
		  	String Date = getDriver().findElement(By.xpath("//td[2]")).getText();
		    String Amount = getDriver().findElement(By.xpath("//td[3]")).getText();
		    String RRR =getDriver().findElement(By.xpath("//td[4]")).getText();
		    String TransactionID  = getDriver().findElement(By.xpath("//td[5]")).getText();
		    String RespCode = getDriver().findElement(By.xpath("//td[6]")).getText();
		    String Alumus = getDriver().findElement(By.xpath("//td[7]")).getText();
		    String Matric_No = getDriver().findElement(By.xpath("//td[8]")).getText();
		    String Verification = getDriver().findElement(By.xpath("//td[9]")).getText();
	        String NA = "EMPTY";

	        String[] toList = {"Date:" + Date, "Amount:" + Amount, "RRR:" + RRR, "TransactionID:" + TransactionID,
	                "RespCode:" + RespCode, "Alumus:" + Alumus, "Matric_No:" + Matric_No, "Verification:" + Verification};
	        for (String field : toList) {
	            String name = "";
	            String val = NA;
	            if(field.endsWith(":")) {
	            	field=field + val;
              }
	            try {
	                String[] fields = field.split(":");
	                name = fields[0];
	                val = fields[1];
	                Assert.assertNotEquals(val, NA);
	                testInfo.get().log(Status.INFO, name + " : " + val);
	            } catch (Error e) {
	                testInfo.get().error(name + " : " + val);
	            }
	        }
	    }
	  
	  public static void AssertSuccessfulPayments() throws Exception {
		  
		   // Payment Review Details
		  	String Date = getDriver().findElement(By.xpath("//td[2]")).getText();
		    String Amount = getDriver().findElement(By.xpath("//td[3]")).getText();
		    String Mode =getDriver().findElement(By.xpath("//td[4]")).getText();
		    String TransactionID  = getDriver().findElement(By.xpath("//td[5]")).getText();
		    String RRR = getDriver().findElement(By.xpath("//td[6]")).getText();
		    String Trans_Status = getDriver().findElement(By.xpath("//td[7]")).getText();
		    String NA = "";

	        String[] toList = {"Trans.Date:" + Date, "Amount:" + Amount, "Mode:" + Mode, "TransactionID:" + TransactionID,
	        		"RRR:" + RRR, "Trans.Status:" + Trans_Status};
	        for (String field : toList) {
	            String name = "";
	            String val = NA;
	            try {
	                String[] fields = field.split(":");
	                name = fields[0];
	                val = fields[1];
	                Assert.assertNotEquals(val, NA);
	                testInfo.get().log(Status.INFO, name + " : " + val);
	            } catch (Error e) {
	                testInfo.get().error(name + " : " + val);
	            }
	        }
	    }
	  public static void SuccessfulPaymentsDetails() throws Exception {
		  
		   // iTranscript Payment Review Details
		  String firstName = getDriver().findElement(By.id("firstName")).getAttribute("value");
		  String lastName = getDriver().findElement(By.id("lastName")).getAttribute("value");
		  String otherName =getDriver().findElement(By.id("otherName")).getAttribute("value");
		  String email  = getDriver().findElement(By.id("email")).getAttribute("value");
		  String mobile = getDriver().findElement(By.id("mobile")).getAttribute("value");
		  String gender = getDriver().findElement(By.id("select2-gender-container")).getText();
		  String role = getDriver().findElement(By.xpath("//span/ul")).getText();
		  String NA = "EMPTY";

		  String[] toList = {"First Name:" + firstName, "Last Name:" + lastName, "Other Name:" + otherName, "Email:" + email,
				  "Mobile:" + mobile, "Gender:" + gender, "Role:" + role};
		  for (String field : toList) {
			  String name = "";
			  String val = NA;
			  if(field.endsWith(":")) {
				  field=field + val;
			  }
			  try {
				  String[] fields = field.split(":");
				  name = fields[0];
				  val = fields[1];
				  Assert.assertNotEquals(val, NA);
				  testInfo.get().log(Status.INFO, name + " : " + val);
			  } catch (Error e) {
				  testInfo.get().error(name + " : " + val);
			  }
		  }
	  }

}
