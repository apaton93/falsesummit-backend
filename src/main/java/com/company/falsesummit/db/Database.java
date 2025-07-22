package com.company.falsesummit.db;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Database {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            props.load(input);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load False Summit Database configuration", e);
        }
    }

    public static Connection get() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
}
