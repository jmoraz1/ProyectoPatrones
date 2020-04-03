package Entities;

public class Jugador {
    public String nombre;
    public Ficha ficha;
    public int ganes;
    public int perdidas;
    public int jugadas;

    public Jugador() {
        this.nombre = "";
        this.ficha = null;
        this.ganes = 0;
        this.perdidas = 0;
        this.jugadas = 0;
    }

    public Jugador(String nombre, Ficha ficha, int ganes, int perdidas, int jugadas) {
        this.nombre = nombre;
        this.ficha = ficha;
        this.ganes = ganes;
        this.perdidas = perdidas;
        this.jugadas = jugadas;
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

    public int getGanes() {
        return ganes;
    }

    public void setGanes(int ganes) {
        this.ganes = ganes;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getJugadas() {
        return jugadas;
    }

    public void setJugadas(int jugadas) {
        this.jugadas = jugadas;
    }
}
