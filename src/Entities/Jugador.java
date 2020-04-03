package Entities;

public class Jugador {
    public String nombre;
    public Ficha ficha;


    public Jugador() {
        this.nombre = "";
        this.ficha = null;

    }

    public Jugador(String nombre, Ficha ficha) {
        this.nombre = nombre;
        this.ficha = ficha;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

}
