package Patterns.Adapter;

import Entities.Casilla;
import Entities.Stone;
import Patterns.Observer.Interfaces.Observador;
import Patterns.Observer.Interfaces.Sujeto;
import Patterns.Visitor.IPersonaje;
import Patterns.Visitor.IVisitor;
import Patterns.Visitor.RecibirAtaque;

import java.util.ArrayList;
import java.util.List;

public class CasillaStoneAdapter extends Casilla implements IPersonaje {
    public Stone stone;

    public CasillaStoneAdapter(int numero) {
        this.setNumero(numero);
        this.setTipo("Stone");
        this.setStone();
    }

    public Stone getStone() {
        return stone;
    }

    private void setStone() {
        this.stone = generarStone();
    }

    private Stone generarStone(){
        return new Stone();

    }

    @Override
    public Casilla clone() {
        return new CasillaStoneAdapter(this.getNumero());
    }

    @Override
    public String accion() {
        return stone.mostrarElementosYVida();
    }

    @Override
    public void accept( IVisitor visitor, int amount){
        if( visitor.getClass().equals( RecibirAtaque.class ) )

            visitor.visit(this, amount);
    }


}
