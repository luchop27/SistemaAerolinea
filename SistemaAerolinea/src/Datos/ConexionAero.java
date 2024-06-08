/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis Vasquez
 */
public class ConexionAero {
    private Connection conexion=null;
    private static final String usr= "root";
    private static final String pswd= "admin";
    private static final String url= "jdbc:mysql://localhost:3306/sistemaaerolinea";

    public ConexionAero() {
    }
    public Connection ObtenerConexion()throws SQLException{
        try{
        Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection(url, usr, pswd); 
        System.out.println("Conexion exitosa");
        } catch(ClassNotFoundException|SQLException e){
            throw new RuntimeException(e);
        }
        return conexion;
    }
}
