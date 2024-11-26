package controlador;

import modelo.Alumno;
import java.sql.*;

public class AlumnoControlador {
    private Statement stmt;
    private ResultSet rs;

    public AlumnoControlador() {
        try {
            stmt = ConexionDB.getResumenStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Alumno validarAlumno(String usuario, String contrasena) {
        try {
            rs = stmt.executeQuery("SELECT * FROM ALUMNO WHERE usuario = '" + usuario + "' AND contrasena = '" + contrasena + "'");
            if (rs.next()) {
                return new Alumno(rs.getInt("numero"), rs.getString("usuario"), rs.getString("contrasena"),
                        rs.getDate("fecha_nacimiento"), rs.getDouble("nota_media"), rs.getInt("foto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
