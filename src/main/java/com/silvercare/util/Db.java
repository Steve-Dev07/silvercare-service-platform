package com.silvercare.util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class Db {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    // statically load configs into Db private static properties
    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = Db.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new Exception("Unable to find config.properties file.");
            }

            Properties prop = new Properties();
            prop.load(input);

            driver = prop.getProperty("db.driver");
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            
            if(driver == null || url == null || user == null || password == null) {
            	throw new Exception("Mssing required properties in config.properties.");
            }

        } catch (Exception e) {
            System.out.println("Error loading DB config: " + e.getMessage());
        }
    }

    // to be used by DAOs to get the JDBC Connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Error connecting DB: " + e.getMessage());
        }

        return conn;
    }
}