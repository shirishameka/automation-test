import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

@Slf4j
public class BaseTest {

    public static String INFLUXDB_PROPERTYFILE = "config/influxdb.properties";

    @BeforeSuite
    public void beforeSuite() {

        log.info("Test Base : Before Suite");
        ExtentTestManager.extent = ExtentManager.getReporter();
    }


    /**
     * @param method
     */
    @BeforeMethod
    public void beforeMethod(Method method, Object[] testData) {
        ExtentTestManager.startTest(method.getAnnotation(Test.class).description());
    }

    /**
     * @param result
     */
    @AfterMethod
    protected void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }

        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());
        ExtentManager.getReporter().flush();
    }
    /**
     * @param
     * @return
     */
    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    @AfterSuite
    public void afterSuite() {
        log.info("Test Base : After Suite");

    }

    /**
     * Validates given status code with response status code
     *
     * @param statusCode - the status code to be checked against
     * @param response   - required reponse with which the comparison is supposed to be done
     * @return true if both the status code match false otherwise
     */
    public boolean validateStatusCode(int statusCode, Response response) {
        if (response.statusCode() == statusCode) {
            log.info("Success!!!! Expected status code " + statusCode + " matches with actual status code " + response.statusCode());
            return true;
        } else {
            log.info("Failure!!! Expected status code " + statusCode + " does not match with actual status code " + response.statusCode());
            return false;
        }

    }
}
