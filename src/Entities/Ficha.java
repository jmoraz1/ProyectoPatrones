package Entities;

public class Ficha {
    Personaje[] personajes = new Personaje[3];
    String color;

    public Ficha(Personaje[] personajes, String color) {
        this.personajes = personajes;
        this.color = color;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Personaje[] personajes) {
        this.personajes = personajes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}



