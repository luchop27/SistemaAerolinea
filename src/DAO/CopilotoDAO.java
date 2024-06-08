/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Datos.ConexionAero;
import Entidades.Copiloto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Luis Vasquez
 */
public class CopilotoDAO {
    private static final String Consultar = "Select * from copiloto";
    private static final String Insertar = "insert into copiloto values(?,?,?,?)";
    private static final String Modificar = "update copiloto set Nombre = ?, Cedula = ?, Edad = ? where idCopiloto = ?";
    private static final String Eliminar = "delete from copiloto where idCopiloto = ?";
    
    
    public List<Copiloto> ListarCopiloto() {
        List<Copiloto> Lista = new ArrayList<Copiloto>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Copiloto ob = new Copiloto();
                ob.setIdCopiloto(resultado.getInt(1));
                ob.setNombre(resultado.getString(2));
                ob.setCedula(resultado.getString(3));
                ob.setEdad(resultado.getString(4));
                Lista.add(ob);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return Lista;
    }
    public boolean InsertarCopiloto(Copiloto ob) {
        ConexionAero con = new ConexionAero();
        boolean op = false;
        try (Connection objConexion = con.ObtenerConexion()) {
            PreparedStatement ps = objConexion.prepareStatement(Insertar);
            ps.setInt(1, ob.getIdCopiloto());
            ps.setString(2, ob.getNombre());
            ps.setString(3, ob.getCedula());
            ps.setString(4, ob.getEdad());

            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
                JOptionPane.showMessageDialog(null, "El copiloto se ha registrado correctamente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el copiloto: " + e.getMessage());
        }
        return op;
    }
    
    public boolean ModificarCopiloto(Copiloto ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Modificar);
            ps.setString(1, ob.getNombre());
            ps.setString(2, ob.getCedula());
            ps.setString(3, ob.getEdad());
            ps.setInt(4, ob.getIdCopiloto());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    
    public boolean EliminarCopiloto(Copiloto ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, ob.getIdCopiloto());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    public DefaultTableModel MostrarCopiloto(List<Copiloto> Lista) {
        String[] titulos = {"IdAzafata", "Nombre","Cedula","Edad"};
        String[] registro = new String[4];
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Copiloto op: Lista) {
            registro[0] = String.valueOf(op.getIdCopiloto());
            registro[1] = op.getNombre();
            registro[2] = op.getCedula();
            registro[3] = op.getEdad();
     
            modelo.addRow(registro);
        }
        return modelo;
    }
     public List<Copiloto> obtenerCopilotosDisponibles() {
        List<Copiloto> listaCopilotos = ListarCopiloto();
        List<Copiloto> copilotosDisponibles = new ArrayList<>();

        for (Copiloto copiloto : listaCopilotos) {
            if (!copiloto.estaAsignado()) {
                copilotosDisponibles.add(copiloto);
            }
        }

        return copilotosDisponibles;
    }

    public void actualizarComboBoxCopiloto(JComboBox<String> comboBox) {
        DefaultComboBoxModel<String> modeloCopiloto = new DefaultComboBoxModel<>();

        List<Copiloto> copilotosDisponibles = obtenerCopilotosDisponibles();

        for (Copiloto copiloto : copilotosDisponibles) {
            modeloCopiloto.addElement(copiloto.getNombre());
        }

        comboBox.setModel(modeloCopiloto);
    }

}
