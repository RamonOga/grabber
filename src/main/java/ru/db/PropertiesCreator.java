package ru.db;

import ru.Runner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesCreator {

    public Properties getProperties(String propertiesFile) {
        Properties properties = null;
        try(InputStream is = Runner.class.getClassLoader().getResourceAsStream(propertiesFile)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException ioe) {
            ioe.fillInStackTrace();
        }
        return properties;
    }
}
