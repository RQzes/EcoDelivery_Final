package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/ecodelivery?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CLAVE = "12345";

    public Connection getConexion() {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USUARIO, CLAVE);

            System.out.println("Conexión exitosa a MySQL");
            System.out.println("Base conectada: " + con.getCatalog());

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return con;
    }
}