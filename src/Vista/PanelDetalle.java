package vista;

import controlador.AsignaturaDetalleControlador;
import modelo.Asignatura;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PanelDetalle extends JPanel {
    private JLabel lblAsignatura, lblNota;
    private JTextField txtNota;
    private JButton btnAnterior, btnSiguiente, btnGuardar, btnPrimero, btnUltimo;
    private AsignaturaDetalleControlador asignaturaControlador;
    
    public PanelDetalle() {
        asignaturaControlador = new AsignaturaDetalleControlador();

        // Configuración del layout principal
        setLayout(new BorderLayout());

        // Panel para los campos de asignatura y nota
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        lblAsignatura = new JLabel("Asignatura: ");
        lblNota = new JLabel("Nota: ");
        txtNota = new JTextField(5);

        panelCampos.add(lblAsignatura);
        panelCampos.add(new JLabel());
        panelCampos.add(lblNota);
        panelCampos.add(txtNota);

        // Añadir el panel de campos al centro del layout principal
        add(panelCampos, BorderLayout.CENTER);

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        btnAnterior = new JButton("Anterior");
        btnGuardar = new JButton("Guardar");
        btnSiguiente = new JButton("Siguiente");
        btnPrimero = new JButton("Primero");
        btnUltimo = new JButton("Último");

        btnAnterior.addActionListener(e -> mostrarAsignaturaAnterior());
        btnSiguiente.addActionListener(e -> mostrarAsignaturaSiguiente());
        btnGuardar.addActionListener(e -> guardarNota());
        btnPrimero.addActionListener(e -> irPrimero());
        btnUltimo.addActionListener(e -> irUltimo());

        // Añadir botones al panel de botones
        panelBotones.add(btnAnterior);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnPrimero);
        panelBotones.add(new JLabel());
        panelBotones.add(btnUltimo);

        // Añadir el panel de botones en la parte inferior
        add(panelBotones, BorderLayout.SOUTH);

        // Inicializar mostrando la primera asignatura
        mostrarAsignatura();
    }

    private void mostrarAsignatura() {
        try {
            Asignatura asignatura = asignaturaControlador.obtenerAsignatura();
            if (asignatura != null) {
                lblAsignatura.setText("Asignatura: " + asignatura.getNombre());
                txtNota.setText(String.valueOf(asignatura.getNota()));
            } else {
                lblAsignatura.setText("No hay asignaturas");
                txtNota.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAsignaturaAnterior() {
        try {
            if (asignaturaControlador.anterior()) {
                mostrarAsignatura();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAsignaturaSiguiente() {
        try {
            if (asignaturaControlador.siguiente()) {
                mostrarAsignatura();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarNota() {
        try {
            double nuevaNota = Double.parseDouble(txtNota.getText());
            asignaturaControlador.actualizarNota(nuevaNota);
            JOptionPane.showMessageDialog(this, "Nota actualizada correctamente.");
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void irPrimero() {
        try {
            if (asignaturaControlador.irPrimero()) {
                mostrarAsignatura();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void irUltimo() {
        try {
            if (asignaturaControlador.irUltimo()) {
                mostrarAsignatura();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
