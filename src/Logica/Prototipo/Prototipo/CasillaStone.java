package Logica.Prototipo.Prototipo;

import Logica.Prototipo.iPrototipo.Casilla;
import Logica.Stone;

public class CasillaStone extends Casilla {
    Stone stone;

    public CasillaStone(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Stone");
        this.setStone(generarStone());
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    private Stone generarStone(){
        return new Stone();

    }
    @Override
    public CasillaStone clone() {
        return new CasillaStone(this.getNumero(), this.getFicha());

    }

}
