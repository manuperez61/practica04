package controlador;

import java.sql.*;

public class UsuarioControlador {
    private Connection conn;

    public UsuarioControlador() {
        try {
            conn = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para validar el usuario y la contraseña
    public boolean validarUsuario(String usuario, String contrasena) {
        String query = "SELECT * FROM ALUMNO WHERE usuario = ? AND contrasena = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            // Si hay un resultado, el usuario y la contraseña son correctos
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener el número de alumno según el usuario
    public int obtenerNumeroAlumno(String usuario) {
        String query = "SELECT numero FROM ALUMNO WHERE usuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("numero");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retornar -1 si no se encuentra el usuario
    }
}
