package Logica.Prototipo.Prototipo;

import Logica.Prototipo.iPrototipo.Casilla;

public class CasillaZorvan extends Casilla {
    public CasillaZorvan(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Zorvan");

    }

    @Override
    public CasillaZorvan clone() {
        return new CasillaZorvan(this.getNumero(), this.getFicha());

    }

    @Override
    public String accion() {
        return "Has ganado el juego! :v";
    }
}
