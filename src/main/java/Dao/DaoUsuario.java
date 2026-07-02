package Dao;

import Conexion.ConexionBD;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoUsuario {

    ConexionBD cn = new ConexionBD();

    public boolean insertar(Usuario u) {

        String sql = "INSERT INTO usuario(nombre, usuario, clave, rol) VALUES (?, ?, ?, ?)";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getClave());
            ps.setString(4, u.getRol());

            int filas = ps.executeUpdate();
            System.out.println("Filas insertadas usuario: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error insertar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario validarLogin(String usuario, String clave) {

        String sql = "SELECT codigo, nombre, usuario, clave, rol FROM usuario WHERE usuario=? AND clave=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();

                u.setCodigo(rs.getInt("codigo"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setRol(rs.getString("rol"));

                rs.close();
                return u;
            }

            rs.close();

        } catch (Exception e) {
            System.out.println("Error validar login: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Usuario> listar() {

        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "SELECT codigo, nombre, usuario, clave, rol FROM usuario ORDER BY codigo";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Usuario u = new Usuario();

                u.setCodigo(rs.getInt("codigo"));
                u.setNombre(rs.getString("nombre"));
                u.setUsuario(rs.getString("usuario"));
                u.setClave(rs.getString("clave"));
                u.setRol(rs.getString("rol"));

                lista.add(u);
            }

            System.out.println("Usuarios listados: " + lista.size());

        } catch (Exception e) {
            System.out.println("Error listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public boolean modificar(Usuario u) {

        String sql = "UPDATE usuario SET nombre=?, usuario=?, clave=?, rol=? WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsuario());
            ps.setString(3, u.getClave());
            ps.setString(4, u.getRol());
            ps.setInt(5, u.getCodigo());

            int filas = ps.executeUpdate();
            System.out.println("Filas modificadas usuario: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error modificar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int codigo) {

        String sql = "DELETE FROM usuario WHERE codigo=?";

        try (
            Connection con = cn.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, codigo);

            int filas = ps.executeUpdate();
            System.out.println("Filas eliminadas usuario: " + filas);

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar usuario: " + e.getMessage());
            return false;
        }
    }
}