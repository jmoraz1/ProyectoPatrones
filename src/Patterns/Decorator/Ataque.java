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
        ataques.add(0, "Tu girada ha resultado en ataca un personaje de la triada");
        ataques.add(1, "Tu girada ha resultado en atacan dos personajes de la triada");
        ataques.add(2, "Tu girada ha resultado en ataca tres personaje de la triada");
        ataques.add(3, "Tu girada ha resultado en ataca solo un personaje y puede activar un poder especial de cualquiera");
        ataques.add(4, "Tu girada ha resultado en atacan dos personajes y se activa un poder especial");
        ataques.add(5, "Tu girada ha resultado en atacan todos los personajes y se activan dos poderes especiales");

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
