package Interfaces;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public abstract class IGirable {
    public int girar() {
        int valor = (int) floor(random()*6+1);
        return valor;
    }
}
