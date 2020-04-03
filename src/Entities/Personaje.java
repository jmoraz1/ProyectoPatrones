package Entities;

public class Personaje {
    Elemento elemento;

    public Personaje(Elemento elemento) {
        this.elemento = elemento;
    }


    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }
}
