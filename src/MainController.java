import Entities.Casilla;
import Entities.Jugador;
import Entities.Tablero;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaQuerubin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainController {

    public static Tablero partida;

    public static void main(String[] args) {

    }


    //El tablero en el atributo turno ya posee el jugador que tiene el turno uno
    public  Tablero NuevaPartida(ArrayList<Jugador> jugadores){
        Jugador[] arrJugadores= new Jugador[jugadores.size()];
        int cont = 0;
        for (Jugador dato: jugadores) {
            arrJugadores[cont]= dato;
            cont++;
        }
        partida = new Tablero(arrJugadores);
        return partida;
    }

    //Arreglar la generación para asegurarnos que no se repita las casillas y que ninguna se posicione en la posición 0

    public  int[] obtenerDiablillos(){
        int[] posicioDiblillos = new int[15];
        int contador = 0;
        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaDiablillo) == true){
                posicioDiblillos[contador]=partida.casillas.indexOf(dato);
            }
            contador= contador++;
        }
        return posicioDiblillos;
    }

    public  int[] obtenerStones(){
        int[] posicionStone = new int[10];
        int contador = 0;
        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaStoneAdapter) == true){
                posicionStone[contador]=partida.casillas.indexOf(dato);
            }
            contador= contador++;
        }
        return posicionStone;
    }

    public  int[] obtenerQuerubines(){
        int[] posicioQueribunes = new int[15];
        int contador = 0;
        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaQuerubin) == true){
                posicioQueribunes[contador]=partida.casillas.indexOf(dato);
            }
            contador= contador++;
        }
        return posicioQueribunes;
    }

    public  int obtenerMovimiento(){
        return partida.obtenerNumeroMovimiento();
    }

    public  int obtenerAtaque(){
        return partida.obtenerTipoAtaque();
    }

    /*Por hacer En caso de que el jugador este bajo un ataque de poder especial y no pueda tirar necesito que me indique
     cuál es para mostrar un mensaje en pantalla de cuántos turnos le quedan por cumplir el castigo y que luego me mande
     el siguiente jugador en turno.
     */
    public boolean validarMovimiento(Jugador jugador){
        return true;
        //hacer la logica aca
    }

    public Jugador obtenerTurno(){
        return partida.siguienteTurno();
    }



}