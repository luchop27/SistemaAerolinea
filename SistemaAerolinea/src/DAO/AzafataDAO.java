/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Datos.ConexionAero;
import Entidades.Azafata;
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
public class AzafataDAO {
    private static final String Consultar = "Select * from azafata";
    private static final String Insertar = "insert into azafata values(?,?,?,?)";
    private static final String Modificar = "update azafata set Nombre = ?, Cedula = ?, Edad = ? where idAzafata = ?";
    private static final String Eliminar = "delete from azafata where idAzafata = ?";
    
    
    public List<Azafata> ListarAzafata() {
        List<Azafata> Lista = new ArrayList<Azafata>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Azafata ob = new Azafata();
                ob.setIdAzafata(resultado.getInt(1));
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
    public boolean InsertarAzafata(Azafata ob) {
        ConexionAero con = new ConexionAero();
        boolean op = false;
        try (Connection objConexion = con.ObtenerConexion()) {
            PreparedStatement ps = objConexion.prepareStatement(Insertar);
            ps.setInt(1, ob.getIdAzafata());
            ps.setString(2, ob.getNombre());
            ps.setString(3, ob.getCedula());
            ps.setString(4, ob.getEdad());

            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
                JOptionPane.showMessageDialog(null, "La Azafata se ha registrado correctamente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la azafata: " + e.getMessage());
        }
        return op;
    }
    
    public boolean ModificarAzafata(Azafata ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Modificar);
            ps.setString(1, ob.getNombre());
            ps.setString(2, ob.getCedula());
            ps.setString(3, ob.getEdad());
            ps.setInt(4, ob.getIdAzafata());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    
    public boolean EliminarAzafata(Azafata ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, ob.getIdAzafata());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    public DefaultTableModel MostrarAzafatas(List<Azafata> Lista) {
        String[] titulos = {"IdAzafata", "Nombre","Cedula","Edad"};
        String[] registro = new String[4];
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Azafata op: Lista) {
            registro[0] = String.valueOf(op.getIdAzafata());
            registro[1] = op.getNombre();
            registro[2] = op.getCedula();
            registro[3] = op.getEdad();
     
            modelo.addRow(registro);
        }
        return modelo;
    }
    public List<Azafata> obtenerAzafatasDisponibles() {
        List<Azafata> listaAzafatas = ListarAzafata();
        List<Azafata> azafatasDisponibles = new ArrayList<>();

        for (Azafata azafata : listaAzafatas) {
            if (!azafata.estaAsignado()) {
                azafatasDisponibles.add(azafata);
            }
        }

        return azafatasDisponibles;
    }

    public void actualizarComboBoxAzafata(JComboBox<String> comboBox) {
        DefaultComboBoxModel<String> modeloAzafata = new DefaultComboBoxModel<>();

        List<Azafata> azafatasDisponibles = obtenerAzafatasDisponibles();

        for (Azafata azafata : azafatasDisponibles) {
            modeloAzafata.addElement(azafata.getNombre());
        }

        comboBox.setModel(modeloAzafata);
    }

}
