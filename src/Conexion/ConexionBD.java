/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author mcore
 */
import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConexionBD {

    public static String db = "gateway";
    public static String user = "root";
    public static String pass = "root";
    public static String url = "jdbc:mysql://127.0.0.1:3306/gateway";
    //public static String url = "jdbc:mysql://127.0.0.1:3306/gateway?autoReconnect=true&amp;failOverReadOnly=false&amp;maxReconnects=10&amp;removeAbandonedTimeout=60&amp;testWhileIdle=true&amp;timeBetweenEvictionRunsMillis=300000";
    public static Connection con;

    public static Connection getcon() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException  |SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de Conexión"
                    + ": " + e.getMessage());
        }
        return con;
    }
}
