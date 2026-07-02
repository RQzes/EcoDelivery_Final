package Dao;

import Conexion.ConexionBD;
import Modelo.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoVehiculo {

    ConexionBD cn = new ConexionBD();

    public boolean insertar(Vehiculo v) {

        String sql = "INSERT INTO vehiculo(codigo, tipo, capacidad, velocidad, estado) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, v.getCodigo());
            ps.setString(2, v.getTipo());
            ps.setDouble(3, v.getCapacidad());
            ps.setDouble(4, v.getVelocidad());
            ps.setString(5, v.getEstado());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas vehículo: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error insertar vehículo: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Vehiculo> listar() {

        ArrayList<Vehiculo> lista = new ArrayList<>();

        String sql = "SELECT codigo, tipo, capacidad, velocidad, estado FROM vehiculo ORDER BY codigo";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Vehiculo v = new Vehiculo();

                v.setCodigo(rs.getInt("codigo"));
                v.setTipo(rs.getString("tipo"));
                v.setCapacidad(rs.getDouble("capacidad"));
                v.setVelocidad(rs.getDouble("velocidad"));
                v.setEstado(rs.getString("estado"));

                lista.add(v);
            }

            System.out.println("Vehículos listados: " + lista.size());

        } catch (Exception e) {
            System.out.println("Error listar vehículo: " + e.getMessage());
        }

        return lista;
    }

    public boolean modificar(Vehiculo v) {

        String sql = "UPDATE vehiculo SET tipo=?, capacidad=?, velocidad=?, estado=? WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, v.getTipo());
            ps.setDouble(2, v.getCapacidad());
            ps.setDouble(3, v.getVelocidad());
            ps.setString(4, v.getEstado());
            ps.setInt(5, v.getCodigo());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas vehículo: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error modificar vehículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM vehiculo WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas vehículo: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar vehículo: " + e.getMessage());
            return false;
        }
    }
    
    public Vehiculo buscarPorCodigo(int codigo) {

    Vehiculo v = null;

    String sql = "SELECT codigo, tipo, capacidad, velocidad, estado FROM vehiculo WHERE codigo=?";

    try (
        java.sql.Connection con = cn.getConexion();
        java.sql.PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, codigo);

        try (java.sql.ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                v = new Vehiculo();

                v.setCodigo(rs.getInt("codigo"));
                v.setTipo(rs.getString("tipo"));
                v.setCapacidad(rs.getDouble("capacidad"));
                v.setVelocidad(rs.getDouble("velocidad"));
                v.setEstado(rs.getString("estado"));
            }
        }

    } catch (Exception e) {
        System.out.println("Error buscar vehículo: " + e.getMessage());
    }

    return v;
}
}