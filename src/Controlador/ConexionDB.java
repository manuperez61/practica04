package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static Connection conn;

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                // Establece la conexión
                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/practica4", "practica4", "123");
                System.out.println("Conexión establecida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conn;
    }

    // Método para obtener un Statement detallado
    public static Statement getDetalleStatement() throws SQLException {
        return getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    
    // Método para obtener un Statement para Resumen
    public static Statement getResumenStatement() throws SQLException {
        return getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    // Cerrar la conexión
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
