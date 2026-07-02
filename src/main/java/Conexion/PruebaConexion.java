package Conexion;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        ConexionBD cn = new ConexionBD();
        Connection con = cn.getConexion();

        if (con != null) {
            System.out.println("Sistema conectado correctamente a ecodelivery");
        } else {
            System.out.println("No se pudo conectar a la base de datos");
        }
    }
}