package Patterns.Visitor;

import Patterns.Adapter.CasillaStoneAdapter;

import java.util.List;

public interface IVisitor {

    public void visit( CasillaStoneAdapter stone , int amount);


}
