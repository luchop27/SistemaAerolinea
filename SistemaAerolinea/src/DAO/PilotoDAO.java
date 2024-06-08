/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Datos.ConexionAero;
import Entidades.Piloto;
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
public class PilotoDAO {
    private static final String Consultar = "Select * from piloto";
    private static final String Insertar = "insert into piloto values(?,?,?,?)";
    private static final String Modificar = "update piloto set Nombre = ?, Cedula = ?, Edad = ? where idPiloto = ?";
    private static final String Eliminar = "delete from piloto where idPiloto = ?";
    
    
    public List<Piloto> ListarPiloto() {
        List<Piloto> Lista = new ArrayList<Piloto>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Piloto ob = new Piloto();
                ob.setIdPiloto(resultado.getInt(1));
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
    public boolean InsertaPiloto(Piloto ob) {
        ConexionAero con = new ConexionAero();
        boolean op = false;
        try (Connection objConexion = con.ObtenerConexion()) {
            PreparedStatement ps = objConexion.prepareStatement(Insertar);
            ps.setInt(1, ob.getIdPiloto());
            ps.setString(2, ob.getNombre());
            ps.setString(3, ob.getCedula());
            ps.setString(4, ob.getEdad());

            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
                JOptionPane.showMessageDialog(null, "El piloto se ha registrado correctamente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el piloto: " + e.getMessage());
        }
        return op;
    }
    
    public boolean ModificarPiloto(Piloto ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Modificar);
            ps.setString(1, ob.getNombre());
            ps.setString(2, ob.getCedula());
            ps.setString(3, ob.getEdad());
            ps.setInt(4, ob.getIdPiloto());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    
    public boolean EliminarPiloto(Piloto ob) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, ob.getIdPiloto());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }
    public DefaultTableModel MostrarPilotos(List<Piloto> Lista) {
        String[] titulos = {"IdPiloto", "Nombre","Cedula","Edad"};
        String[] registro = new String[4];
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Piloto op: Lista) {
            registro[0] = String.valueOf(op.getIdPiloto());
            registro[1] = op.getNombre();
            registro[2] = op.getCedula();
            registro[3] = op.getEdad();
     
            modelo.addRow(registro);
        }
        return modelo;
    }
    public List<Piloto> obtenerPilotosDisponibles() {
        List<Piloto> listaPilotos = ListarPiloto();
        List<Piloto> pilotosDisponibles = new ArrayList<>();

        for (Piloto piloto : listaPilotos) {
            if (!piloto.estaAsignado()) {
                pilotosDisponibles.add(piloto);
            }
        }

        return pilotosDisponibles;
    }

    // Método para actualizar el JComboBox de pilotos en un formulario
    public void actualizarComboBoxPiloto(JComboBox<String> comboBox) {
        DefaultComboBoxModel<String> modeloPiloto = new DefaultComboBoxModel<>();

        List<Piloto> pilotosDisponibles = obtenerPilotosDisponibles();

        for (Piloto piloto : pilotosDisponibles) {
            modeloPiloto.addElement(piloto.getNombre());
        }

        comboBox.setModel(modeloPiloto);
    }

}
