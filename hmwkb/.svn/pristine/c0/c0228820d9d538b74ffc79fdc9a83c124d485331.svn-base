package com.chinadrtv.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 获得配置项的值
 * @author Jing
 * @version $Id: ApplicationContextConfig.java, v 0.1 2013-7-23 上午9:01:32 Jing Exp $
 */
public class ApplicationContextConfig {

    private static Map<String, String> configMap = new HashMap<String, String>();

    public static String get(String key) {
        return configMap.get(key);
    }

    public static synchronized void put(String key, String value) {
        configMap.put(key, value);
    }
    
    public static synchronized void remove(String key) {
        configMap.remove(key);
    }
}
