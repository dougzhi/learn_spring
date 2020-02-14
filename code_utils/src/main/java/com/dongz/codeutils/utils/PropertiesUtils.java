package com.dongz.codeutils.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    public static Map<String,String> customMap = new HashMap<>();

    static {
        InputStream file = Object.class.getResourceAsStream("/properties/typeConverter.properties");
        try {
            Properties prop = new Properties();
            prop.load(file);
            customMap.putAll((Map) prop);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
