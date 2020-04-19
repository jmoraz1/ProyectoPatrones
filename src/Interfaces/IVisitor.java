package Interfaces;

import Entities.Casilla;


public interface IVisitor {

    public void visit(Casilla casilla , int amount);

}
