package Patterns.Observer.ConcreteObserver;

import Patterns.Observer.Interfaces.Observador;
import Patterns.Observer.Interfaces.Sujeto;

import java.util.ArrayList;
import java.util.List;

public class SujetoConcreto implements Sujeto {

    private List<Observador> observers = new ArrayList<Observador>();
    private Integer value;
    public SujetoConcreto(){	}

    @Override
    public void addObserver(Observador o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observador o) {

    }

    @Override
    public void notifyObservers() {
        for(Observador o : observers){
            o.update(this.value);
        }
    }
    public void setState(Integer value) {
        this.value = value;
        notifyObservers();
    }
}
