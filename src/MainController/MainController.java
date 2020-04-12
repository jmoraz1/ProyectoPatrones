package MainController;

import Entities.Casilla;
import Entities.Ficha;
import Entities.Jugador;
import Entities.Tablero;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Decorator.Ataque;
import Patterns.Decorator.Decorador;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaQuerubin;
//import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        asignarFichas(arrJugadores);
        return partida;
    }

    private void asignarFichas(Jugador[] arrJugadores) {
        for (int i = 0; i < arrJugadores.length; i++) {
            Ficha tmpFicha;
            String tipo;
            int valor = (int) Math.floor(Math.random()*6+1);
            switch (valor){
                case 1:
                  //Por terminar

            }
        }
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
        int resulDado=partida.obtenerNumeroMovimiento();
        Ficha fichaActiva=partida.turno.getFicha();
        int posicionActual=obtenerPosicionJugador(fichaActiva);
        int nuevaPosicion=posicionActual+resulDado;
        moverFicha(posicionActual,nuevaPosicion,fichaActiva);

        return nuevaPosicion;
    }

    private int obtenerPosicionJugador(Ficha fichaActiva) {
        int index=0;
        ArrayList<Casilla> casillas=partida.casillas;
        for (int i=0; i>casillas.size();i++){
            ArrayList<Ficha> fichasCasilla=casillas.get(i).getFichas();
            for (int j=0; j>fichasCasilla.size();j++){
                //iterando por todas las fichas de la casilla
                if(fichasCasilla.get(j)==fichaActiva){
                    //una vez q se encuentra donde esta la dicha del jugador, se guarda el numero de la casilla
                    index=i;
                }
            }
        }
        return index;
    }

    private void moverFicha(int posicionActual, int nuevaPosicion, Ficha ficha){
        ArrayList<Casilla> casillas=partida.casillas;
        Casilla casillaActual=casillas.get(posicionActual);
        //inicia el ciclo para buscar en las fichas de la casilla en la que esta el jugador
        for (int i=0;i<casillaActual.getFichas().size();i++){
            if(casillaActual.getFichas().get(i)==ficha){
                //quitando ficha de su posicion en la casilla actual
                casillaActual.getFichas().remove(i);
            }
        }
        //agregando ficha en el nuevo arreglo de su nueva casilla
        casillas.get(nuevaPosicion).setFicha(ficha);
    }

    public  String obtenerAtaque(){
        int res =  partida.obtenerTipoAtaque();
        Ataque tmpAtaque = new Ataque(res);
        return tmpAtaque.gatInfoDecorada();
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
