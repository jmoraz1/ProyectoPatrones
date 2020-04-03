package Patterns.Visitor;

public interface IPersonaje  {
    public void accept(IVisitor visitor, int amount);
}
