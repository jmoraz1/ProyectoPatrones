package Patterns.Visitor;

import Entities.Casilla;


public interface IVisitor {

    public void visit(Casilla casilla , int amount);


}
