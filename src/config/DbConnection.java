package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static DbConnection instance = null;
    private Connection connection = null;


    private DbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BatiCuisine", "BatiCuisine", "");

            if (connection != null) {
                System.out.println("database connecter");
            } else {
                System.out.println("database pas connecter");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }


}
