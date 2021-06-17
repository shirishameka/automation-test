import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

    static ExtentReports extent;

    final static String filePath = "test-output/html/Extent.html";

    /**
     * get Reporter
     * @return
     */
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true, DisplayOrder.NEWEST_FIRST);
        }
        return extent;
    }
}
