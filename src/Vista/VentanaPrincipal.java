package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuValidar, menuVisualizar, menuAcercaDe;
    private JMenuItem itemEntrar, itemSalir, itemDetalle, itemResumen, itemInfo;

    private JPanel panelActual;
    private JPanel panelInicio; // Panel principal

    private String usuarioActual; // Para almacenar el nombre de usuario autenticado
    private int alumnoNumero; // Para almacenar el identificador del alumno autenticado

    public VentanaPrincipal() {
        setTitle("Gestión de Alumnos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        iniciarComponentes();
        mostrarPanelInicio(); // Mostrar el panel de inicio al abrir la aplicación
    }

    private void iniciarComponentes() {
        menuBar = new JMenuBar();
        menuValidar = new JMenu("Validar");
        menuVisualizar = new JMenu("Visualizar");
        menuAcercaDe = new JMenu("Acerca de");

        itemEntrar = new JMenuItem("Entrar");
        itemSalir = new JMenuItem("Salir");
        itemDetalle = new JMenuItem("Detalle");
        itemResumen = new JMenuItem("Resumen");
        itemInfo = new JMenuItem("Informacion");

        itemEntrar.addActionListener(e -> autenticarUsuario());

        itemSalir.addActionListener(e -> cerrarSesion());

        itemDetalle.addActionListener(e -> mostrarPanelDetalle());

        itemResumen.addActionListener(e -> mostrarPanelResumen());

        itemInfo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Autores: Alvaro Duarte y Manuel Pérez",
                    "Acerca de",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        menuValidar.add(itemEntrar);
        menuValidar.add(itemSalir);
        menuVisualizar.add(itemDetalle);
        menuVisualizar.add(itemResumen);
        menuAcercaDe.add(itemInfo);

        // Deshabilitar opciones
        itemDetalle.setEnabled(false);
        itemResumen.setEnabled(false);

        menuBar.add(menuValidar);
        menuBar.add(menuVisualizar);
        menuBar.add(menuAcercaDe);
        setJMenuBar(menuBar);
    }

    // Método para autenticar al usuario
    private void autenticarUsuario() {
        String usuario = JOptionPane.showInputDialog(this, "Usuario:");
        String contrasena = JOptionPane.showInputDialog(this, "Contraseña:");

        if ("admin".equals(usuario) && "1234".equals(contrasena)) {
            JOptionPane.showMessageDialog(this, "¡Bienvenido!");
            usuarioActual = usuario;
            alumnoNumero = 101;

            // Habilitar las opciones
            itemDetalle.setEnabled(true);
            itemResumen.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }

    // Método para cerrar sesión
    private void cerrarSesion() {
        usuarioActual = null;
        alumnoNumero = 0;

        // Deshabilitar las opciones
        itemDetalle.setEnabled(false);
        itemResumen.setEnabled(false);
        
        // Volver al panel de inicio
        mostrarPanelInicio();
    }

    private void mostrarPanelInicio() {
        cambiarPanel(crearPanelInicio());
    }

    private JPanel crearPanelInicio() {
        panelInicio = new JPanel(new BorderLayout());
        JLabel etiquetaInicio = new JLabel("Aplicación de gestión de alumnos y asignaturas", JLabel.CENTER);
        etiquetaInicio.setFont(new Font("Arial", Font.BOLD, 18));
        panelInicio.add(etiquetaInicio, BorderLayout.CENTER);
        return panelInicio;
    }

    private void mostrarPanelDetalle() {
        if (usuarioActual != null) {
            cambiarPanel(new PanelDetalle());
        }
    }

    private void mostrarPanelResumen() {
        if (usuarioActual != null) {
            cambiarPanel(new PanelResumen());
        }
    }

    // Cambio paneles
    private void cambiarPanel(JPanel nuevoPanel) {
        if (panelActual != null) {
            remove(panelActual);
        }
        panelActual = nuevoPanel;
        add(panelActual, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
