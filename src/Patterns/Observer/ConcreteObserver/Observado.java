package Patterns.Observer.ConcreteObserver;


import java.util.Observable;

public class Observado {
    private String gNombre;

    public Observado(String pN) {
        gNombre=pN;
    }

    public void update(Observable o, Object arg) {
        Integer value = (Integer) arg;
        showValue(value); // Mostramos el valor
    }

    private void showValue(int temp){
        System.out.println("Temperatura = " + temp + " por " + gNombre);
    }
}
