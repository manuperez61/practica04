package controlador;

import modelo.Asignatura;
import java.sql.*;
import java.util.ArrayList;

public class AsignaturaControlador {
    private Connection conn;

    public AsignaturaControlador() {
        try {
            conn = ConexionDB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todas las asignaturas de un alumno
    public ArrayList<Asignatura> obtenerAsignaturas(int aluNumero) {
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        String query = "SELECT * FROM ASIGNATURA WHERE aluNumero = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, aluNumero);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Asignatura asignatura = new Asignatura(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("nota"),
                        rs.getInt("aluNumero"));
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaturas;
    }

    // Método para obtener todos los alumnos (IDs de los alumnos)
    public ArrayList<Integer> obtenerAlumnos() {
        ArrayList<Integer> alumnos = new ArrayList<>();
        String query = "SELECT DISTINCT aluNumero FROM ASIGNATURA";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                alumnos.add(rs.getInt("aluNumero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    // Método para obtener una asignatura específica según el índice
    public Asignatura obtenerAsignaturaPorIndice(int aluNumero, int indice) {
        String query = "SELECT * FROM ASIGNATURA WHERE aluNumero = ? LIMIT 1 OFFSET ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, aluNumero);
            stmt.setInt(2, indice);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Asignatura(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getDouble("nota"),
                        rs.getInt("aluNumero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar la nota de una asignatura
    public void actualizarNota(int codigo, double nuevaNota) {
        String query = "UPDATE ASIGNATURA SET nota = ? WHERE codigo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, nuevaNota);
            stmt.setInt(2, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para calcular la nota media de un alumno
    public double calcularNotaMedia(int aluNumero) {
        double totalNotas = 0;
        int numAsignaturas = 0;
        String query = "SELECT nota FROM ASIGNATURA WHERE aluNumero = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, aluNumero);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalNotas += rs.getDouble("nota");
                numAsignaturas++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (numAsignaturas > 0) ? totalNotas / numAsignaturas : 0.0;
    }
}
