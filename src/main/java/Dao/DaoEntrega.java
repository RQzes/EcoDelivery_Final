package Dao;

import Conexion.ConexionBD;
import Modelo.Entrega;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoEntrega {

    ConexionBD cn = new ConexionBD();

    private String mensajeProceso = "";

    public String getMensajeProceso() {
        return mensajeProceso;
    }

    public boolean insertar(Entrega e) {

        String sql = "INSERT INTO entrega(codigo, fecha, tiempo, co2, codigo_vehiculo, codigo_pedido, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, e.getCodigo());
            ps.setString(2, e.getFecha());
            ps.setDouble(3, e.getTiempo());
            ps.setDouble(4, e.getCo2());
            ps.setInt(5, e.getCodigoVehiculo());
            ps.setInt(6, e.getCodigoPedido());
            ps.setString(7, e.getEstado());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas entrega: " + filas);

            return filas > 0;

        } catch (Exception ex) {
            System.out.println("Error insertar entrega: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Entrega> listar() {

        ArrayList<Entrega> lista = new ArrayList<>();

        String sql = "SELECT codigo, fecha, tiempo, co2, codigo_vehiculo, codigo_pedido, estado FROM entrega ORDER BY codigo";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Entrega e = new Entrega();

                e.setCodigo(rs.getInt("codigo"));
                e.setFecha(rs.getString("fecha"));
                e.setTiempo(rs.getDouble("tiempo"));
                e.setCo2(rs.getDouble("co2"));
                e.setCodigoVehiculo(rs.getInt("codigo_vehiculo"));
                e.setCodigoPedido(rs.getInt("codigo_pedido"));
                e.setEstado(rs.getString("estado"));

                lista.add(e);
            }

            System.out.println("Entregas listadas: " + lista.size());

        } catch (Exception ex) {
            System.out.println("Error listar entrega: " + ex.getMessage());
        }

        return lista;
    }

    public boolean modificar(Entrega e) {

        String sql = "UPDATE entrega SET fecha=?, tiempo=?, co2=?, codigo_vehiculo=?, codigo_pedido=?, estado=? WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, e.getFecha());
            ps.setDouble(2, e.getTiempo());
            ps.setDouble(3, e.getCo2());
            ps.setInt(4, e.getCodigoVehiculo());
            ps.setInt(5, e.getCodigoPedido());
            ps.setString(6, e.getEstado());
            ps.setInt(7, e.getCodigo());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas entrega: " + filas);

            return filas > 0;

        } catch (Exception ex) {
            System.out.println("Error modificar entrega: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM entrega WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas entrega: " + filas);

            return filas > 0;

        } catch (Exception ex) {
            System.out.println("Error eliminar entrega: " + ex.getMessage());
            return false;
        }
    }

    public boolean procesarEntrega(Entrega e) {

        String sql = "{CALL sp_procesar_entrega(?, ?, ?, ?, ?, ?)}";

        try (
            Connection con = cn.getConexion();
            CallableStatement cs = con.prepareCall(sql)
        ) {

            cs.setInt(1, e.getCodigo());
            cs.setString(2, e.getFecha());
            cs.setInt(3, e.getCodigoPedido());
            cs.setInt(4, e.getCodigoVehiculo());

            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);

            cs.execute();

            int ok = cs.getInt(5);
            mensajeProceso = cs.getString(6);

            if (mensajeProceso == null || mensajeProceso.trim().isEmpty()) {
                mensajeProceso = "El procedimiento almacenado no devolvió mensaje.";
            }

            System.out.println("Resultado SP: " + ok);
            System.out.println("Mensaje SP: " + mensajeProceso);

            return ok == 1;

        } catch (Exception ex) {
            mensajeProceso = "Error procesar entrega con SP: " + ex.getMessage();
            System.out.println(mensajeProceso);
            return false;
        }
    }
}