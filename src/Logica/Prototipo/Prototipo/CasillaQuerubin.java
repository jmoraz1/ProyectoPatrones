package Logica.Prototipo.Prototipo;

import Logica.Prototipo.iPrototipo.Casilla;


public class CasillaQuerubin extends Casilla {
    public CasillaQuerubin(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Querubin");

    }


    @Override
    public CasillaStone clone() {
        return new CasillaStone(this.getNumero(), this.getFicha());

    }
}
