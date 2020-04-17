package Patterns.Decorator;

import Interfaces.IGirable;

import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class Ataque extends Decorador {
    ArrayList<String> ataques= new ArrayList<>();

    public Ataque(IGirable dado) {
        super(dado);
    }

    public void setAtaques(){
        ataques.set(0, "Tu girada ha resultado en ataca un personaje de la triada");
        ataques.set(1, "Tu girada ha resultado en atacan dos personajes de la triada");
        ataques.set(2, "Tu girada ha resultado en ataca tres personaje de la triada");
        ataques.set(3, "Tu girada ha resultado en ataca solo un personaje y puede activar un poder especial de cualquiera");
        ataques.set(4, "Tu girada ha resultado en atacan dos personajes y se activa un poder especial");
        ataques.set(5, "Tu girada ha resultado en atacan todos los personajes y se activan dos poderes especiales");

    }
    @Override
    public int girar() {
        setAtaques();
        int valor = (int) floor(random()*6+1);
        return valor;
    }

    public ArrayList<String> getAtaques() {
        return ataques;
    }


}
