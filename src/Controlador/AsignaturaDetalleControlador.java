package controlador;

import modelo.Asignatura;
import java.sql.*;

public class AsignaturaDetalleControlador {
    private ResultSet rs;
    private Statement stmt;

    public AsignaturaDetalleControlador() {
        try {
            stmt = ConexionDB.getDetalleStatement();
            rs = stmt.executeQuery("SELECT * FROM ASIGNATURA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Asignatura obtenerAsignatura() throws SQLException {
        return new Asignatura(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("nota"), rs.getInt("aluNumero"));
    }

    public boolean irPrimero() throws SQLException {
        return rs.first();
    }

    public boolean irUltimo() throws SQLException {
        return rs.last();
    }

    public boolean siguiente() throws SQLException {
        return rs.next();
    }

    public boolean anterior() throws SQLException {
        return rs.previous();
    }

    public void actualizarNota(double nuevaNota) throws SQLException {
        rs.updateDouble("nota", nuevaNota);
        rs.updateRow();
    }

    public boolean esPrimero() throws SQLException {
        return rs.isFirst();
    }

    public boolean esUltimo() throws SQLException {
        return rs.isLast();
    }
}
