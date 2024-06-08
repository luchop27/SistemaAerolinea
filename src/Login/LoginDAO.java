/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;


//import Datos.Conexion;
import Datos.ConexionAero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luis Vasquez
 */
public class LoginDAO {
    public boolean InicioSesion(String usuario, String pass){
      
        try {
            ConexionAero con = new ConexionAero();
            Connection conexion = con.ObtenerConexion();
            String consulta = "SELECT * FROM usuarios WHERE usuario = ? AND pass = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, pass);
            ResultSet resultado = preparedStatement.executeQuery();
            return resultado.next(); 
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
