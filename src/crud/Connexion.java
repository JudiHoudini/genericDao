/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.InputStream;
import java.util.Properties;
import java.sql.SQLException;

/**
 *
 * @author °°JUDICAEL°°
 */
public class Connexion {
    private String port;
    private String database;
    private String userName;
    private String password;

    public Connexion() {
        loadFromPropertiesFile();
        if (port == null || database == null || userName == null || password == null) {
            loadFromEnvironmentVariables();
        }
        if (port == null || database == null || userName == null || password == null) {
            throw new RuntimeException("Database connection details are not properly configured.");
        }
    }

    private void loadFromPropertiesFile() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db-config.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                this.port = prop.getProperty("db.port");
                this.database = prop.getProperty("db.database");
                this.userName = prop.getProperty("db.user");
                this.password = prop.getProperty("db.password");
            }
        } catch (Exception e) {
            System.err.println("Failed to load configuration from properties file: " + e.getMessage());
        }
    }

    private void loadFromEnvironmentVariables() {
        this.port = System.getenv("DB_PORT");
        this.database = System.getenv("DB_NAME");
        this.userName = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASSWORD");
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connexion(String port, String database, String userName, String password) {
        this.setPort(port);
        this.setDatabase(database);
        this.setUserName(userName);
        this.setPassword(password);
    }
    

    public Connection connectMysql() throws SQLException {
        String url = "jdbc:mysql://localhost:" + this.port + "/" + this.database;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, this.userName, this.password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new SQLException("Connection to database failed: " + e.getMessage(), e);
        }
    }
}