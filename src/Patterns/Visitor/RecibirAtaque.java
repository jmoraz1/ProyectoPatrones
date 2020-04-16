package Patterns.Visitor;

import Entities.Casilla;
import Interfaces.IVisitor;
import Patterns.Adapter.CasillaStoneAdapter;

public class RecibirAtaque implements IVisitor {

    @Override
    public void visit(Casilla casilla, int amount)
    {
        if(casilla instanceof CasillaStoneAdapter){
            CasillaStoneAdapter casillaStone= (CasillaStoneAdapter)casilla;
            int vidaStone= casillaStone.getStone().getVida();
            casillaStone.getStone().setVida(vidaStone-amount);

        }
        //este es el metodo que se tiene q llamar cuando se ataca un stone

    }

}
