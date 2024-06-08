/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import Datos.ConexionAero;
import Entidades.Avion;
import Vista.Inicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis Vasquez
 */
public class AvionDAO {
    private static final String Consultar = "SELECT * FROM avion";
    private static final String Insertar = "INSERT INTO avion VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String Modificar = "UPDATE avion SET Marca = ?, Modelo = ?, Matricula = ?, Capacidad = ?, Piloto = ?, Copiloto = ?, Azafata = ? WHERE idAvion = ?";
    private static final String Eliminar = "DELETE FROM avion WHERE idAvion = ?";

    public List<Avion> ListarAviones() {
        List<Avion> Lista = new ArrayList<>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            Statement statement = objConexion.createStatement();
            ResultSet resultado = statement.executeQuery(Consultar);
            while (resultado.next()) {
                Avion avion = new Avion();
                avion.setIdAvion(resultado.getInt(1));
                avion.setMarca(resultado.getString(2));
                avion.setModelo(resultado.getString(3));
                avion.setMatricula(resultado.getString(4));
                avion.setCapacidad(resultado.getInt(5));
                avion.setPiloto(resultado.getString(6));
                avion.setCopiloto(resultado.getString(7));
                avion.setAzafata(resultado.getString(8));
                Lista.add(avion);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return Lista;
    }

    public boolean InsertarAvion(Avion avion) {
    ConexionAero con = new ConexionAero();
    boolean op = false;

    try (Connection objConexion = con.ObtenerConexion()) {
        // Obtener la capacidad original antes de insertar el avión
        int capacidadOriginal = avion.getCapacidadOriginal();

        // Insertar el avión en la base de datos
        PreparedStatement ps = objConexion.prepareStatement(Insertar);
        ps.setInt(1, avion.getIdAvion());
        ps.setString(2, avion.getMarca());
        ps.setString(3, avion.getModelo());
        ps.setString(4, avion.getMatricula());
        ps.setInt(5, avion.getCapacidad());  // Aquí puedes usar la capacidad original si es necesario
        ps.setString(6, avion.getPiloto());
        ps.setString(7, avion.getCopiloto());
        ps.setString(8, avion.getAzafata());

        int n = ps.executeUpdate();
        if (n != 0) {
            op = true;
            JOptionPane.showMessageDialog(null, "El Avión se ha registrado correctamente.");

            // Obtener la capacidad original después de insertar el avión
            avion.setCapacidadOriginal(capacidadOriginal);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar el avión: " + e.getMessage());
    }
    return op;
}
    

    public boolean ModificarAvion(Avion avion) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Modificar);
            ps.setString(1, avion.getMarca());
            ps.setString(2, avion.getModelo());
            ps.setString(3, avion.getMatricula());
            ps.setInt(4, avion.getCapacidad());
            ps.setString(5, avion.getPiloto());
            ps.setString(6, avion.getCopiloto());
            ps.setString(7, avion.getAzafata());
            ps.setInt(8, avion.getIdAvion());

            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public boolean EliminarAvion(Avion avion) {
        boolean op = false;
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            PreparedStatement ps = objConexion.prepareStatement(Eliminar);
            ps.setInt(1, avion.getIdAvion());
            int n = ps.executeUpdate();
            if (n != 0) {
                op = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return op;
    }

    public DefaultTableModel MostrarAviones(List<Avion> Lista) {
        String[] titulos = {"IdAvion", "Marca", "Modelo", "Matricula", "Capacidad", "Piloto", "Copiloto", "Azafata"};
        String[] registro = new String[8];
        DefaultTableModel modelo;
        modelo = new DefaultTableModel(null, titulos);
        for (Avion avion : Lista) {
            registro[0] = String.valueOf(avion.getIdAvion());
            registro[1] = avion.getMarca();
            registro[2] = avion.getModelo();
            registro[3] = avion.getMatricula();
            registro[4] = String.valueOf(avion.getCapacidad());
            registro[5] = avion.getPiloto();
            registro[6] = avion.getCopiloto();
            registro[7] = avion.getAzafata();

            modelo.addRow(registro);
        }
        return modelo;
    }
    public boolean verificarTripulantesAsignados(int idAvion, String piloto, String copiloto, String azafata) {
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            String query = "SELECT * FROM avion WHERE (Piloto = ? OR Copiloto = ? OR Azafata = ?) AND idAvion != ?";
            try (PreparedStatement ps = objConexion.prepareStatement(query)) {
                ps.setString(1, piloto);
                ps.setString(2, copiloto);
                ps.setString(3, azafata);
                ps.setInt(4, idAvion);

                ResultSet resultado = ps.executeQuery();
                return resultado.next();  // Devuelve true si ya está asignado a otro avión, false en caso contrario
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }
    public List<Avion> BuscarAvionPorMatricula(String marca) {
        List<Avion> avionesEncontrados = new ArrayList<>();
        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();
            String consulta = "SELECT * FROM avion WHERE idAvion = ?";
            try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
                ps.setString(1, marca);
                ResultSet resultado = ps.executeQuery();
                while (resultado.next()) {
                    Avion avionEncontrado = new Avion();
                    avionEncontrado.setIdAvion(resultado.getInt(1));
                    avionEncontrado.setMarca(resultado.getString(2));
                    avionEncontrado.setModelo(resultado.getString(3));
                    avionEncontrado.setMatricula(resultado.getString(4));
                    avionEncontrado.setCapacidad(resultado.getInt(5));
                    avionEncontrado.setPiloto(resultado.getString(6));
                    avionEncontrado.setCopiloto(resultado.getString(7));
                    avionEncontrado.setAzafata(resultado.getString(8));

                    avionesEncontrados.add(avionEncontrado);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return avionesEncontrados;
    }
    public Avion BuscarAvionPorId(int idAvion) {
    Avion avionEncontrado = null;
    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();
        String consulta = "SELECT * FROM avion WHERE idAvion = ?";
        try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
            ps.setInt(1, idAvion);
            ResultSet resultado = ps.executeQuery();
            if (resultado.next()) {
                avionEncontrado = new Avion();
                avionEncontrado.setIdAvion(resultado.getInt(1));
                avionEncontrado.setMarca(resultado.getString(2));
                avionEncontrado.setModelo(resultado.getString(3));
                avionEncontrado.setMatricula(resultado.getString(4));
                avionEncontrado.setCapacidad(resultado.getInt(5));
                avionEncontrado.setPiloto(resultado.getString(6));
                avionEncontrado.setCopiloto(resultado.getString(7));
                avionEncontrado.setAzafata(resultado.getString(8));
            } else {
                System.out.println("No se encontró ningún avión con el idAvion: " + idAvion);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar avión por idAvion: " + e.getMessage());
        e.printStackTrace();
    }
    return avionEncontrado;
}

    public Avion obtenerAvionPorIdVuelo(int idVuelo) {
    Avion avion = null;
    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        // Consulta SQL para obtener el idAvion asociado al idVuelo
        String consulta = "SELECT idAvion FROM vuelo WHERE idVuelo = ?";
        try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
            ps.setInt(1, idVuelo);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                // Si se encuentra el idAvion, se busca el Avion por ese id
                int idAvion = resultado.getInt("idAvion");
                avion = BuscarAvionPorId(idAvion);
            } else {
                System.out.println("No se encontró ningún idAvion para el idVuelo: " + idVuelo);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener avión por idVuelo: " + e.getMessage());
        e.printStackTrace();
    }
    return avion;
}
    public int obtenerIdVueloDesdeAvion(int idAvion) {
    int idVuelo = -1;  // Valor por defecto si no se encuentra el idVuelo

    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        // Consulta SQL para obtener el idVuelo desde la tabla Avion
        String consulta = "SELECT idVuelo FROM avion WHERE idAvion = ?";
        try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
            ps.setInt(1, idAvion);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                // Si se encuentra, se asigna el valor correspondiente
                idVuelo = resultado.getInt("idVuelo");
            } else {
                System.out.println("No se encontró ningún idVuelo para el idAvion: " + idAvion);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al obtener idVuelo desde Avion: " + e.getMessage());
        e.printStackTrace();
    }

    return idVuelo;
}
public int obtenerCapacidadAvionSegunVuelo(int idVuelo) {
    int capacidadAvion = 0;

    try {
        ConexionAero con = new ConexionAero();
        Connection objConexion = con.ObtenerConexion();

        String consulta = "SELECT capacidad FROM avion WHERE idAvion IN " +
                          "(SELECT idAvion FROM vuelo WHERE idVuelo = ?)";

        try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
            ps.setInt(1, idVuelo);
            ResultSet resultado = ps.executeQuery();

            if (resultado.next()) {
                capacidadAvion = resultado.getInt("capacidad");
            } else {
                System.out.println("No se encontró la capacidad del avión para el vuelo con ID: " + idVuelo);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return capacidadAvion;
}
public boolean actualizarCapacidadAvion(int idAvion, int nuevaCapacidad) {
        boolean actualizacionExitosa = false;

        try {
            ConexionAero con = new ConexionAero();
            Connection objConexion = con.ObtenerConexion();

            // Consulta SQL para actualizar la capacidad del avión
            String consulta = "UPDATE avion SET capacidad = ? WHERE idAvion = ?";

            try (PreparedStatement ps = objConexion.prepareStatement(consulta)) {
                ps.setInt(1, nuevaCapacidad);
                ps.setInt(2, idAvion);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    // La actualización fue exitosa
                    actualizacionExitosa = true;
                } else {
                    System.out.println("No se pudo actualizar la capacidad del avión. Verifica el idAvion.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la capacidad del avión: " + e.getMessage());
            e.printStackTrace();
        }

        return actualizacionExitosa;
    }


}
