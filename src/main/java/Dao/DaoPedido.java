package Dao;

import Conexion.ConexionBD;
import Modelo.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoPedido {

    ConexionBD cn = new ConexionBD();

    public boolean insertar(Pedido p) {

        String sql = "INSERT INTO pedido(codigo, descripcion, distancia, codigo_cliente, estado) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, p.getCodigo());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getDistancia());
            ps.setInt(4, p.getCodigoCliente());
            ps.setString(5, p.getEstado());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas pedido: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error insertar pedido: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Pedido> listar() {

        ArrayList<Pedido> lista = new ArrayList<>();

        String sql = "SELECT codigo, descripcion, distancia, codigo_cliente, estado FROM pedido ORDER BY codigo";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Pedido p = new Pedido();

                p.setCodigo(rs.getInt("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setDistancia(rs.getDouble("distancia"));
                p.setCodigoCliente(rs.getInt("codigo_cliente"));
                p.setEstado(rs.getString("estado"));

                lista.add(p);
            }

            System.out.println("Pedidos listados: " + lista.size());

        } catch (Exception e) {
            System.out.println("Error listar pedido: " + e.getMessage());
        }

        return lista;
    }

    public boolean modificar(Pedido p) {

        String sql = "UPDATE pedido SET descripcion=?, distancia=?, codigo_cliente=?, estado=? WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, p.getDescripcion());
            ps.setDouble(2, p.getDistancia());
            ps.setInt(3, p.getCodigoCliente());
            ps.setString(4, p.getEstado());
            ps.setInt(5, p.getCodigo());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas pedido: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error modificar pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM pedido WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas pedido: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar pedido: " + e.getMessage());
            return false;
        }
    }
    public Pedido buscarPorCodigo(int codigo) {

    Pedido p = null;

    String sql = "SELECT codigo, descripcion, distancia, codigo_cliente, estado FROM pedido WHERE codigo=?";

    try (
        java.sql.Connection con = cn.getConexion();
        java.sql.PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, codigo);

        try (java.sql.ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                p = new Pedido();

                p.setCodigo(rs.getInt("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setDistancia(rs.getDouble("distancia"));
                p.setCodigoCliente(rs.getInt("codigo_cliente"));
                p.setEstado(rs.getString("estado"));
            }
        }

    } catch (Exception e) {
        System.out.println("Error buscar pedido: " + e.getMessage());
    }

    return p;
}
}

