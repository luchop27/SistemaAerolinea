/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Datos.ConexionAero;
import Entidades.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis Vasquez
 */
public class PasajeroDAO {
    private static final String Consultar = "SELECT * FROM pasajero";
    private static final String Insertar = "INSERT INTO pasajero VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String Modificar = "UPDATE pasajero SET idVuelo = ?, Avion = ?, Nombre = ?, Cedula = ?, Edad = ?, Asientos = ?, Fecha = ?, Hora = ?, Origen = ?, Destino = ?, Precio = ?, Categoria = ?, Total = ?  WHERE idPasajero = ?";
    private static final String Eliminar = "DELETE FROM pasajero WHERE idPasajero = ?";
    private static final String ActualizarCantidad = "UPDATE avion set Capacidad = Capacidad - ? where idAvion = ?";

    public List<Pasajero> ListarPasajeros() {
        List<Pasajero> Lista = new ArrayList<>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Pasajero pasajero = new Pasajero();
                pasajero.setIdPasajero(resultado.getInt(1));
                pasajero.setIdVuelo(resultado.getInt(2));
                pasajero.setAvion(resultado.getInt(3));
                pasajero.setNombre(resultado.getString(4));
                pasajero.setCedula(resultado.getString(5));
                pasajero.setEdad(resultado.getString(6));
                pasajero.setAsientos(resultado.getInt(7));
                pasajero.setFecha(resultado.getString(8));
                pasajero.setHora(resultado.getString(9));
                pasajero.setOrigen(resultado.getString(10));  
                pasajero.setDestino(resultado.getString(11));
                pasajero.setPrecio(resultado.getInt(12));
                pasajero.setCategoria(resultado.getString(13));
                pasajero.setTotal(resultado.getInt(14));
                Lista.add(pasajero);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return Lista;
    }

    public boolean InsertarPasajero(Pasajero pasajero) {
        ConexionAero con = new ConexionAero();
        boolean op = false;
        try (Connection objConexion = con.ObtenerConexion()) {
            PreparedStatement ps = objConexion.prepareStatement(Insertar);
            ps.setInt(1, pasajero.getIdPasajero());
            ps.setInt(2, pasajero.getIdVuelo());
            ps.setInt(3, pasajero.getAvion());
            ps.setString(4, pasajero.getNombre());
            ps.setString(5, pasajero.getCedula());
            ps.setString(6, pasajero.getEdad());
            ps.setInt(7, pasajero.getAsientos());
            ps.setString(8, pasajero.getFecha());
            ps.setString(9, pasajero.getHora());
            ps.setString(10, pasajero.getOrigen());  
            ps.setString(11, pasajero.getDestino()); 
            ps.setInt(12, pasajero.getPrecio());
            ps.setString(13, pasajero.getCategoria());
            ps.setInt(14, pasajero.getTotal());

            int n = ps.executeUpdate();
            if (n != 0) {
                PreparedStatement ps2 = objConexion.prepareStatement(ActualizarCantidad);
                ps2.setInt(1, pasajero.getAsientos());
                ps2.setString(2, String.valueOf(pasajero.getAvion()));
                ps2.executeUpdate();
                op = true;
                JOptionPane.showMessageDialog(null, "El Pasajero se ha registrado correctamente.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el pasajero: " + e.getMessage());
        }
        return op;
    }

    public boolean ModificarPasajero(Pasajero pasajero) {
    boolean op = false;
    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        Pasajero pasajeroAnterior = obtenerPasajeroPorId(pasajero.getIdPasajero());

        PreparedStatement ps = objConexion.prepareStatement(Modificar);
        ps.setInt(1, pasajero.getIdVuelo());
        ps.setInt(2, pasajero.getAvion());
        ps.setString(3, pasajero.getNombre());
        ps.setString(4, pasajero.getCedula());
        ps.setString(5, pasajero.getEdad());
        ps.setInt(6, pasajero.getAsientos());
        ps.setString(7, pasajero.getFecha());
        ps.setString(8, pasajero.getHora());
        ps.setString(9, pasajero.getOrigen());
        ps.setString(10, pasajero.getDestino());
        ps.setInt(11, pasajero.getPrecio());
        ps.setString(12, pasajero.getCategoria());
        ps.setInt(13, pasajero.getTotal());
        ps.setInt(14, pasajero.getIdPasajero());

        int n = ps.executeUpdate();
        if (n != 0) {

            int asientosModificados = pasajero.getAsientos() - pasajeroAnterior.getAsientos();
            restarAsientos(pasajero.getAvion(), asientosModificados);

            op = true;
            JOptionPane.showMessageDialog(null, "El Pasajero se ha modificado correctamente.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al modificar el pasajero: " + e.getMessage());
    }
    return op;
}
    public Pasajero obtenerPasajeroPorId(int idPasajero) {
    Pasajero pasajero = null;

    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        String consulta = "SELECT * FROM pasajero WHERE idPasajero = ?";
        try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
            ps.setInt(1, idPasajero);

            try (ResultSet resultado = ps.executeQuery()) {
                if (resultado.next()) {
                    pasajero = new Pasajero();
                    pasajero.setIdPasajero(resultado.getInt("idPasajero"));
                    pasajero.setIdVuelo(resultado.getInt("idVuelo"));
                    pasajero.setAvion(resultado.getInt("avion"));
                    pasajero.setNombre(resultado.getString("nombre"));
                    pasajero.setCedula(resultado.getString("cedula"));
                    pasajero.setEdad(resultado.getString("edad"));
                    pasajero.setAsientos(resultado.getInt("asientos"));
                    pasajero.setFecha(resultado.getString("fecha"));
                    pasajero.setHora(resultado.getString("hora"));
                    pasajero.setOrigen(resultado.getString("origen"));
                    pasajero.setDestino(resultado.getString("destino"));
                    pasajero.setPrecio(resultado.getInt("precio"));
                    pasajero.setCategoria(resultado.getString("categoria"));
                    pasajero.setTotal(resultado.getInt("total"));
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener el pasajero por ID: " + e.getMessage());
    }

    return pasajero;
}
    public boolean restarAsientos(int idAvion, int cantidadAsientos) {
    boolean exito = false;

    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        int capacidadActual = obtenerCapacidadAvion(idAvion);

        if (capacidadActual >= cantidadAsientos) {
            int nuevaCapacidad = capacidadActual - cantidadAsientos;
            PreparedStatement ps = objConexion.prepareStatement("UPDATE avion SET capacidad = ? WHERE idAvion = ?");
            ps.setInt(1, nuevaCapacidad);
            ps.setInt(2, idAvion);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                exito = true;
            } else {
                System.out.println("No se pudo actualizar la capacidad del avión.");
            }
        } else {
            System.out.println("No hay suficientes asientos disponibles en el avión.");
        }
    } catch (SQLException e) {
        System.out.println("Error al restar asientos: " + e.getMessage());
    }

    return exito;
}

private int obtenerCapacidadAvion(int idAvion) throws SQLException {
    ConexionAero con = new ConexionAero();
    Connection objConexion = con.ObtenerConexion();

    int capacidad = 0;

    try (PreparedStatement ps = objConexion.prepareStatement("SELECT capacidad FROM avion WHERE idAvion = ?")) {
        ps.setInt(1, idAvion);

        try (ResultSet resultado = ps.executeQuery()) {
            if (resultado.next()) {
                capacidad = resultado.getInt("capacidad");
            } else {
                System.out.println("No se encontró la capacidad del avión con ID: " + idAvion);
            }
        }
    }

    return capacidad;
}
    
    public boolean EliminarPasajero(Pasajero pasajero) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, pasajero.getIdPasajero());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public DefaultTableModel MostrarPasajeros(List<Pasajero> Lista) {
        String[] titulos = {"IdPasajero", "IdVuelo", "Avion", "Nombre", "Cedula", "Edad", "Asientos", "Fecha", "Hora", "Origen", "Destino", "Precio", "Categoria", "Total"};
        String[] registro = new String[14]; 
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Pasajero pasajero : Lista) {
            registro[0] = String.valueOf(pasajero.getIdPasajero());
            registro[1] = String.valueOf(pasajero.getIdVuelo());
            registro[2] = String.valueOf(pasajero.getAvion());
            registro[3] = pasajero.getNombre();
            registro[4] = pasajero.getCedula();
            registro[5] = pasajero.getEdad();
            registro[6] = String.valueOf(pasajero.getAsientos());
            registro[7] = pasajero.getFecha();
            registro[8] = pasajero.getHora();
            registro[9] = pasajero.getOrigen();
            registro[10] = pasajero.getDestino();
            registro[11] = String.valueOf(pasajero.getPrecio());
            registro[12] = pasajero.getCategoria();
            registro[13] = String.valueOf(pasajero.getTotal());

            modelo.addRow(registro);
        }
        return modelo;
    }
    public boolean eliminarPasajerosPorIdVuelo(String idVuelo) {
        boolean success = false;
        String eliminarPasajeros = "DELETE FROM pasajero WHERE idVuelo = ?";

        try (Connection objConexion = new ConexionAero().ObtenerConexion();
             PreparedStatement ps = objConexion.prepareStatement(eliminarPasajeros)) {

            ps.setString(1, idVuelo);
            int filasAfectadas = ps.executeUpdate();

            success = (filasAfectadas > 0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return success;
    }
    public int obtenerSumaAsientosPorVuelo(int idVuelo) {
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();

            String consulta = "SELECT SUM(asientos) FROM pasajero WHERE idVuelo = ?";
            try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
                ps.setInt(1, idVuelo);

                ResultSet resultado = ps.executeQuery();
                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // En caso de error
    }

}
