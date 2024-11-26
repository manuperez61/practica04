package controlador;

import modelo.Asignatura;
import java.sql.*;
import java.util.ArrayList;

public class AsignaturaResumenControlador {
    private Statement stmt;
    private ResultSet rs;

    public AsignaturaResumenControlador() {
        try {
            stmt = ConexionDB.getResumenStatement();
            rs = stmt.executeQuery("SELECT * FROM ASIGNATURA");

            if (rs != null && rs.isBeforeFirst()) {
                System.out.println("Datos cargados correctamente desde la base de datos.");
            } else {
                System.out.println("No se encontraron registros en la tabla ASIGNATURA.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Asignatura> obtenerAsignaturas(int aluNumero) {
    ArrayList<Asignatura> asignaturas = new ArrayList<>();
    try {
        rs.beforeFirst(); // Volver al inicio
        while (rs.next()) {
            if (rs.getInt("aluNumero") == aluNumero) {
                asignaturas.add(new Asignatura(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("nota"), rs.getInt("aluNumero")));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return asignaturas;  // Si no hay asignaturas, devuelve una lista vacía, no null.
}


    public double calcularNotaMedia(int aluNumero) {
        double suma = 0;
        int count = 0;
        try {
            if (rs != null) {
                rs.beforeFirst();
                while (rs.next()) {
                    if (rs.getInt("aluNumero") == aluNumero) {
                        suma += rs.getDouble("nota");
                        count++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0 ? suma / count : 0;
    }

    public ArrayList<Integer> obtenerAlumnos() {
        ArrayList<Integer> alumnos = new ArrayList<>();
        try {
            if (rs != null) {
                rs.beforeFirst();
                while (rs.next()) {
                    int aluNumero = rs.getInt("aluNumero");
                    if (!alumnos.contains(aluNumero)) {
                        alumnos.add(aluNumero);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }
}
