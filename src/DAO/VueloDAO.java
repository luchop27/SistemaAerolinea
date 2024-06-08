/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Datos.ConexionAero;
import Entidades.Vuelo;
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
public class VueloDAO {
    private static final String Consultar = "SELECT * FROM vuelo";
    private static final String Insertar = "INSERT INTO vuelo VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String Modificar = "UPDATE vuelo SET Avion = ?, Origen = ?, Destino = ?, Fecha = ?, Hora = ?, Precio = ? WHERE idVuelo = ?";
    private static final String Eliminar = "DELETE FROM vuelo WHERE idVuelo = ?";
    

    public List<Vuelo> ListarVuelos() {
        List<Vuelo> Lista = new ArrayList<>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Vuelo vuelo = new Vuelo();
                vuelo.setIdVuelo(resultado.getInt(1));
                vuelo.setAvion(resultado.getInt(2));
                vuelo.setOrigen(resultado.getString(3));
                vuelo.setDestino(resultado.getString(4));
                vuelo.setFecha(resultado.getDate(5));
                vuelo.setHora(resultado.getString(6));
                vuelo.setPrecio(resultado.getInt(7));
                Lista.add(vuelo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return Lista;
    }

    public boolean InsertarVuelo(Vuelo vuelo) {
    ConexionAero con = new ConexionAero();
    boolean op = false;

    if (verificarAvionAsignado(String.valueOf(vuelo.getAvion()))) {
        JOptionPane.showMessageDialog(null, "Error: El avión ya está asignado a otro vuelo.");
        return false;
    }

    try (Connection objConexion = con.ObtenerConexion()) {
        PreparedStatement ps = objConexion.prepareStatement(Insertar);
        ps.setInt(1, vuelo.getIdVuelo());
        ps.setInt(2, vuelo.getAvion());
        ps.setString(3, vuelo.getOrigen());
        ps.setString(4, vuelo.getDestino());
        ps.setDate(5, new java.sql.Date(vuelo.getFecha().getTime()));
        ps.setString(6, vuelo.getHora());
        ps.setInt(7, vuelo.getPrecio());

        int n = ps.executeUpdate();
        if (n != 0) {
            op = true;
            JOptionPane.showMessageDialog(null, "El Vuelo se ha registrado correctamente.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar el vuelo: " + e.getMessage());
    }
    return op;
}

    public boolean ModificarVuelo(Vuelo vuelo) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Modificar);
            ps.setInt(1, vuelo.getAvion());
            ps.setString(2, vuelo.getOrigen());
            ps.setString(3, vuelo.getDestino());
            ps.setDate(4, new java.sql.Date(vuelo.getFecha().getTime()));
            ps.setString(5, vuelo.getHora());
            ps.setInt(6, vuelo.getPrecio());
            ps.setInt(7, vuelo.getIdVuelo());

            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean EliminarVuelo(Vuelo vuelo) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, vuelo.getIdVuelo());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public DefaultTableModel MostrarVuelos(List<Vuelo> Lista) {
        String[] titulos = {"IdVuelo", "Avion", "Origen", "Destino", "Fecha", "Hora", "Precio"};
        String[] registro = new String[7];
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Vuelo vuelo : Lista) {
            registro[0] = String.valueOf(vuelo.getIdVuelo());
            registro[1] = String.valueOf(vuelo.getAvion());
            registro[2] = vuelo.getOrigen();
            registro[3] = vuelo.getDestino();
            registro[4] = vuelo.getFecha().toString(); 
            registro[5] = vuelo.getHora();
            registro[6] = String.valueOf(vuelo.getPrecio());

            modelo.addRow(registro);
        }
        return modelo;
    }
    public boolean verificarAvionAsignado(String idAvion) {
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            String query = "SELECT * FROM vuelo WHERE Avion = ?";
            try (PreparedStatement ps = objConexion.prepareStatement(query)) {
                ps.setString(1, idAvion);
                ResultSet resultado = ps.executeQuery();
                return resultado.next(); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
    public List<Vuelo> BuscarVuelosPorId(String idVuelo) {
    List<Vuelo> vuelos = new ArrayList<>();
    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();
        String query = "SELECT * FROM vuelo WHERE idVuelo = ?";
        
        try (PreparedStatement ps = objConexion.prepareStatement(query)) {
            ps.setString(1, idVuelo);
            
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                Vuelo vuelo = new Vuelo();
                vuelo.setIdVuelo(resultado.getInt(1));
                vuelo.setAvion(resultado.getInt(2));
                vuelo.setOrigen(resultado.getString(3));
                vuelo.setDestino(resultado.getString(4));
                vuelo.setFecha(resultado.getDate(5));
                vuelo.setHora(resultado.getString(6));
                vuelo.setPrecio(resultado.getInt(7));
                vuelos.add(vuelo);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    return vuelos;
}
    public boolean eliminarVueloPorId(String idVuelo) {
        boolean success = false;
        String eliminarVuelo = "DELETE FROM vuelo WHERE idVuelo = ?";

        try (Connection objConexion = new ConexionAero().ObtenerConexion();
             PreparedStatement ps = objConexion.prepareStatement(eliminarVuelo)) {

            ps.setString(1, idVuelo);
            int filasAfectadas = ps.executeUpdate();

            success = (filasAfectadas > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return success;
    }
}
