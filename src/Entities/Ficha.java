package Entities;

import java.util.Arrays;

public class Ficha {
    Personaje[] personajes = new Personaje[3];
    int contador = 0;

    public Ficha(Personaje[] personajes) {
        this.personajes = personajes;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Personaje[] personajes) {
        this.personajes = personajes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ficha)) return false;
        Ficha ficha = (Ficha) o;
        return Arrays.equals(getPersonajes(), ficha.getPersonajes());
    }
}



