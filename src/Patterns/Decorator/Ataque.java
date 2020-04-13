package Patterns.Decorator;

public class Ataque extends Decorador {
    public Ataque(int num) {
        this.res= num;
    }

    public String gatInfoDecorada(){
        switch (this.res){
            case 1:
                return "Tu girada ha resultado en ataca un personaje de la triada";
            case 2:
                return "Tu girada ha resultado en atacan dos personajes de la triada";
            case 3:
                return "Tu girada ha resultado en ataca tres personaje de la triada";
            case 4:
                return "Tu girada ha resultado en ataca solo un personaje y puede activar un poder especial de cualquiera";
            case 5:
                return "Tu girada ha resultado en atacan dos personajes y se activa un poder especial";
            default:
                return "Tu girada ha resultado en atacan todos los personajes y se activan dos poderes especiales";
        }
    }
}
