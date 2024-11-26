package modelo;

public class Asignatura {
    private int codigo;
    private String nombre;
    private double nota;
    private int aluNumero;

    // Constructor
    public Asignatura(int codigo, String nombre, double nota, int aluNumero) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nota = nota;
        this.aluNumero = aluNumero;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

    public int getAluNumero() { return aluNumero; }
    public void setAluNumero(int aluNumero) { this.aluNumero = aluNumero; }
}
