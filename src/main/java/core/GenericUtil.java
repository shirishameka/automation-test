package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import static io.dropwizard.testing.FixtureHelpers.fixture;

@Slf4j
public class GenericUtil {
    /**
     * Generate random number
     * @param max
     * @return
     */
    public static int generateRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * Generate random number with the length parameter
     * @param len
     * @return
     */
    public static String getRandomNumber(long len) {
        if (len > 18) {
            throw new IllegalStateException("To many digits");
        }
        long tLen = (long) Math.pow(10, len - 1) * 9;

        long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

        String tVal = number + "";
        if (tVal.length() != len) {
            throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
        }
        return tVal;
    }

    /**
     * Get file from location
     * @param folderName
     * @param fileName
     * @return
     */
    public static String getRequestFixture(String folderName, String fileName) {
        return fixture(folderName + "/" + fileName);
    }

    /**
     * object to string
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        String objectAsString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            objectAsString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectAsString;
    }

    /**
     * to json object
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        return new JSONObject(objectToString(object));
    }

    /**
     *
     * @param timestamp
     * @return
     */
    public static String epochConverter(long timestamp) {
        Date date = new Date(timestamp);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        String java_date = jdf.format(date);
        return java_date;
    }
}
