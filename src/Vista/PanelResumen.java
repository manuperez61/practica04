package vista;

import controlador.AsignaturaResumenControlador;
import modelo.Asignatura;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelResumen extends JPanel {
    private JLabel lblUsuario, lblNotaMedia, lblImagenAlumno;
    private JTable tablaAsignaturas;
    private AsignaturaResumenControlador asignaturaControlador;
    private String[] columnas = {"Nombre", "Nota"};
    private Object[][] datos;
    private JButton btnCalcular, btnAnterior, btnSiguiente, btnPrimero, btnUltimo;
    private int alumnoActualIndex = 0;
    private ArrayList<Integer> alumnos; // Lista de IDs de alumnos

    public PanelResumen() {
        // Configuración inicial del layout
        setLayout(new BorderLayout());

        // Inicializar controlador y obtener lista de alumnos
        asignaturaControlador = new AsignaturaResumenControlador();
        alumnos = asignaturaControlador.obtenerAlumnos();

        // Inicializar etiquetas
        lblUsuario = new JLabel("Usuario: ");
        lblNotaMedia = new JLabel("Nota Media: ");
        lblImagenAlumno = new JLabel(); // Para mostrar la imagen del alumno

        // Inicializar la tabla con datos vacíos
        tablaAsignaturas = new JTable(new Object[0][0], columnas);

        // Mostra si no hay alumnos
        if (alumnos == null || alumnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay alumnos disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear paneles
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(3, 1));
        panelSuperior.add(lblUsuario);
        panelSuperior.add(lblNotaMedia);
        panelSuperior.add(lblImagenAlumno); // Agregar el label de la imagen

        JPanel panelInferior = new JPanel(new FlowLayout());
        btnCalcular = new JButton("Calcular Nota Media");
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnPrimero = new JButton("Primero");
        btnUltimo = new JButton("Último");
        panelInferior.add(btnPrimero);
        panelInferior.add(btnAnterior);
        panelInferior.add(btnSiguiente);
        panelInferior.add(btnUltimo);
        panelInferior.add(btnCalcular);

        // Agregar componentes al panel principal
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaAsignaturas), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnCalcular.addActionListener(e -> calcularNotaMedia());
        btnAnterior.addActionListener(e -> cambiarAlumno(alumnoActualIndex - 1));
        btnSiguiente.addActionListener(e -> cambiarAlumno(alumnoActualIndex + 1));
        btnPrimero.addActionListener(e -> cambiarAlumno(0));
        btnUltimo.addActionListener(e -> cambiarAlumno(alumnos.size() - 1));

        // Cargar datos del primer alumno
        cargarAlumno(alumnoActualIndex);
    }

    private void cargarAlumno(int index) {
        if (index >= 0 && index < alumnos.size()) {
            alumnoActualIndex = index;
            int alumnoNumero = alumnos.get(index);

            // Actualizar etiqueta del usuario
            if (lblUsuario != null) {
                lblUsuario.setText("Usuario: Alumno #" + alumnoNumero);
            }

            try {
                // Obtener asignaturas del alumno
                ArrayList<Asignatura> asignaturas = asignaturaControlador.obtenerAsignaturas(alumnoNumero);

                if (asignaturas == null || asignaturas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay asignaturas para este alumno.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Convertir asignaturas a datos
                datos = obtenerDatosAsignaturas(asignaturas);
                if (datos == null || datos.length == 0) {
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar las asignaturas.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Actualizar el modelo de la tabla
                tablaAsignaturas.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
                calcularNotaMedia(); // Actualizar la nota media
                actualizarImagenAlumno(alumnoNumero); // Actualizar la imagen del alumno
                actualizarBotones(); // Actualizar el estado de los botones
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar las asignaturas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarImagenAlumno(int alumnoNumero) {
        // Calcular el nombre de archivo de la imagen
        String rutaImagen = ("C:\\Users\\Manuel\\Desktop\\Practica04\\Practica04\\fotos\\" + (alumnoNumero + 900) + ".jpg" );

        ImageIcon iconoImagen = new ImageIcon(rutaImagen);
        
        if (iconoImagen.getIconWidth() == -1) { // Si la imagen no existe o no se puede cargar
            rutaImagen = ("C:\\Users\\Manuel\\Desktop\\Practica04\\Practica04\\fotos\\default.jpg");
        }
        
        // Intentar cargar la imagen
        iconoImagen = new ImageIcon(rutaImagen);
        
            // Redimensionar la imagen para ajustarla
            Image imagenRedimensionada = iconoImagen.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagenAlumno.setIcon(new ImageIcon(imagenRedimensionada));
            lblImagenAlumno.setText(""); // Limpiar el texto

    }

    private Object[][] obtenerDatosAsignaturas(ArrayList<Asignatura> asignaturas) {
        Object[][] datos = new Object[asignaturas.size()][2];
        for (int i = 0; i < asignaturas.size(); i++) {
            datos[i][0] = asignaturas.get(i).getNombre();
            datos[i][1] = asignaturas.get(i).getNota();
        }
        return datos;
    }

    private void calcularNotaMedia() {
        if (alumnoActualIndex >= 0 && alumnoActualIndex < alumnos.size()) {
            int alumnoNumero = alumnos.get(alumnoActualIndex);
            double notaMedia = asignaturaControlador.calcularNotaMedia(alumnoNumero);
            lblNotaMedia.setText("Nota Media: " + String.format("%.2f", notaMedia));
        }
    }

    private void cambiarAlumno(int index) {
        if (index >= 0 && index < alumnos.size()) {
            cargarAlumno(index);
        }
    }

    private void actualizarBotones() {
        btnAnterior.setEnabled(alumnoActualIndex > 0);
        btnSiguiente.setEnabled(alumnoActualIndex < alumnos.size() - 1);
        btnPrimero.setEnabled(alumnoActualIndex > 0);
        btnUltimo.setEnabled(alumnoActualIndex < alumnos.size() - 1);
    }
}
