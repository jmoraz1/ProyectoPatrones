package Patterns.Visitor;

import Entities.Stone;
import Patterns.Adapter.CasillaStoneAdapter;

import java.util.List;

public class RecibirAtaque implements IVisitor {

    @Override
    public void visit( CasillaStoneAdapter stone, int amount)
    {
        int vidaStone= stone.getStone().getVida();
        stone.getStone().setVida(vidaStone-amount);
    }

}
