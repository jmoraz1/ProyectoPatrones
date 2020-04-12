package Patterns.Observer;

public interface Sujeto {
    void addObserver(Observador o);
    void notifyObservers();
}
