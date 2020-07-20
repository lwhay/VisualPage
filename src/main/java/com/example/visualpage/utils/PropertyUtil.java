package com.example.visualpage.utils;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {
    public static Properties loadProperties(String resources) throws FileNotFoundException {
		   // 使用InputStream得到一个资源文件
		   InputStream inputstream = new FileInputStream(resources);
		   // new 一个Properties
		   Properties properties = new Properties();
		   try {
		   // 加载配置文件
		      properties.load(inputstream);
		      return properties;
		   } catch (IOException e) {
		      throw new RuntimeException(e);
		   } finally {
		      try {
		         inputstream.close();
		      } catch (IOException e) {
		         throw new RuntimeException(e);
		      }
		   }
		}

		public static Properties readProperties(String propertiesFile) throws FileNotFoundException{
			String resources = PropertyUtil.class.getClassLoader().getResource(propertiesFile).getPath();
			Properties properties = loadProperties(resources);
			return properties;
		}


    public static String getValue(String key){
        Properties prop = new Properties();
        InputStream in = new PropertyUtil().getClass().getResourceAsStream("/application.properties");
        try {
            prop.load(new InputStreamReader(in, "UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
