package ConnectionManager;

import javax.swing.*;
import java.sql.*;

/**
 * Created by jls on 2/10/15.
 */
public class ConnectionManager {

    private static String URL = "jdbc:mysql://82.171.46.128:3306/Project";
    private static String username;
    private static char[] password;

    private static Connection connection = null;
    private static Statement stmt = null;
    public static boolean loggedIn;

    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(new JPanel(), "Error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JPanel(), "Error: " + e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, turnPasswordIntoString());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(new JPanel(), "Error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JPanel(), "Error: " + e.getMessage());
        }
        return connection;
    }

    public static void setLoginCredentials(String username, char[] password) {
        ConnectionManager.username = username;
        ConnectionManager.password = password;
    }

    private static String turnPasswordIntoString() {
        String s = "";
        for (char c : password) {
            s += c;
        }
        return s;
    }
}
