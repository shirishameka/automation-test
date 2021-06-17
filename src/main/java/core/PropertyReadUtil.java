package core;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertyReadUtil {
    /* Create basic object */
    ClassLoader objClassLoader = null;

    Properties properties = new Properties();

    /**
     * Read Property Util
     * @param propertiesFilename
     * @throws IOException
     */
    public PropertyReadUtil(String propertiesFilename) throws IOException {
        /* Initialize 'objClassLoader' once so same object used for multiple files. */
        objClassLoader = getClass().getClassLoader();
        try (FileInputStream objFileInputStream = new FileInputStream(objClassLoader.getResource(propertiesFilename).getFile());) {
            /* Load file into commonProperties */
            properties.load(objFileInputStream);
        }
    }

    /**
     * read key
     * @param key
     * @return
     */
    public String readKey(String key) {
        return String.valueOf(properties.get(key));
    }
}
