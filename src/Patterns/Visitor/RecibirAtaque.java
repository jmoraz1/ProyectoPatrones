package Patterns.Visitor;

import Entities.Casilla;
import Entities.Stone;
import Patterns.Adapter.CasillaStoneAdapter;

import java.util.List;

public class RecibirAtaque implements IVisitor {

    @Override
    public void visit(Casilla casilla, int amount)
    {
        if(casilla instanceof CasillaStoneAdapter){
            CasillaStoneAdapter casillaStone= (CasillaStoneAdapter)casilla;
            int vidaStone= casillaStone.getStone().getVida();
            casillaStone.getStone().setVida(vidaStone-amount);

        }
        //este es el metodo que se tiene q llama cuando se ataca un stone

    }

}
