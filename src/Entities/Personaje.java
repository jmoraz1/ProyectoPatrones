package Entities;

public class Personaje {
    String nombre;
    Elemento elemento;

    public Personaje(String nombre, Elemento elemento) {
        this.nombre = nombre;
        this.elemento = elemento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }
}
