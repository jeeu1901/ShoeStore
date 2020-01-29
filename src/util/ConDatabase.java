package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public  class ConDatabase {
    private static Connection con = null;
    public static Connection getConnection() {
        if(con != null) {
            return con;
        }
        else {
            try {
                Properties p = new Properties();
                p.load(new FileInputStream("src/Settings.properties"));
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(p.getProperty("connectionString"),
                        p.getProperty("name"), p.getProperty("password"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return con;
        }
    }

}
