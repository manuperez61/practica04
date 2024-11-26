package modelo;

import java.util.Date;

public class Alumno {
    private int numero;
    private String usuario;
    private String contrasena;
    private Date fechaNacimiento;
    private double notaMedia;
    private int foto;

    // Constructor
    public Alumno(int numero, String usuario, String contrasena, Date fechaNacimiento, double notaMedia, int foto) {
        this.numero = numero;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
        this.notaMedia = notaMedia;
        this.foto = foto;
    }

    // Getters y Setters
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public double getNotaMedia() { return notaMedia; }
    public void setNotaMedia(double notaMedia) { this.notaMedia = notaMedia; }

    public int getFoto() { return foto; }
    public void setFoto(int foto) { this.foto = foto; }
}
