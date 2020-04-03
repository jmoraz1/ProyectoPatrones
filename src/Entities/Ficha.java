package Entities;

public class Ficha {
    Personaje[] personajes = new Personaje[3];

    public Ficha(Personaje[] personajes, String color) {
        this.personajes = personajes;
    }

    public Personaje[] getPersonajes() {
        return personajes;
    }

    public void setPersonajes(Personaje[] personajes) {
        this.personajes = personajes;
    }


}



