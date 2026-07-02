package Dao;

import Conexion.ConexionBD;
import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoCliente {

    ConexionBD cn = new ConexionBD();

    public boolean insertar(Cliente c) {

        String sql = "INSERT INTO cliente(codigo, nombre, edad, telefono, direccion) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, c.getCodigo());
            ps.setString(2, c.getNombre());
            ps.setInt(3, c.getEdad());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getDireccion());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas cliente: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Cliente> listar() {

        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "SELECT codigo, nombre, edad, telefono, direccion FROM cliente ORDER BY codigo";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cliente c = new Cliente();

                c.setCodigo(rs.getInt("codigo"));
                c.setNombre(rs.getString("nombre"));
                c.setEdad(rs.getInt("edad"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));

                lista.add(c);
            }

            System.out.println("Clientes listados: " + lista.size());

        } catch (Exception e) {
            System.out.println("Error listar cliente: " + e.getMessage());
        }

        return lista;
    }

    public boolean modificar(Cliente c) {

        String sql = "UPDATE cliente SET nombre=?, edad=?, telefono=?, direccion=? WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getEdad());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getDireccion());
            ps.setInt(5, c.getCodigo());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas cliente: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error modificar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM cliente WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas cliente: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}