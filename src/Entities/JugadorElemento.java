package Entities;

public class JugadorElemento {
    public Jugador jugador;
    public Elemento elemento;

    public JugadorElemento(Jugador jugador, Elemento elemento) {
        this.jugador = jugador;
        this.elemento = elemento;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }
}
