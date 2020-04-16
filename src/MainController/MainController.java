package MainController;

import Entities.*;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Decorator.Ataque;
import Patterns.FactoryMethod.FabricaPersonajes;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaQuerubin;
import com.sun.xml.internal.ws.wsdl.writer.document.Part;
//import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

    public static Tablero partida;

   /* public static void main(String[] args) {

    }*/


    //El tablero en el atributo turno ya posee el jugador que tiene el turno uno
    public  Tablero NuevaPartida(ArrayList<Jugador> jugadores, String psw) throws IOException {
        if (psw== "admin"){
        Jugador[] arrJugadores= new Jugador[jugadores.size()];
        int cont = 0;
        for (Jugador dato: jugadores) {
            arrJugadores[cont]= dato;
            cont++;
        }
        asignarFichas(arrJugadores);
        partida = new Tablero(arrJugadores);
        for(int i = 0; i<arrJugadores.length; i++){
            partida.casillas.get(0).setFicha(arrJugadores[i].getFicha());
        }
        return partida;
        }else{
            Tablero tmp= null;
            return tmp;
        }

    }

    private void asignarFichas(Jugador[] arrJugadores) {
        for (int i = 0; i < arrJugadores.length; i++) { // esto lo hace por cada jugador
            FabricaPersonajes Personaje1; // esto representa
            FabricaPersonajes Personaje2; // esto representa
            FabricaPersonajes Personaje3; // esto representa
            Personaje [] arr_Personajes = new Personaje [3];
            int valor1 = 0; // Valor 1, 2 y 3 representa un elemento
            int valor2 = 0;
            int valor3 = 0;
            do {
                valor1 = (int) Math.floor(Math.random() * 6 + 1); // Valor 1, 2 y 3 representa un elemento.
                valor2 = (int) Math.floor(Math.random() * 6 + 1);
                valor3 = (int) Math.floor(Math.random() * 6 + 1);

            } while((valor1 == valor2) || ( valor2 == valor3) || ( valor3 == valor1)); // Busca 3 elementos diferentes

            arr_Personajes [0] = asignarElemento(valor1); // Asigna los personajes al array
            arr_Personajes [1] = asignarElemento(valor2);
            arr_Personajes [2] = asignarElemento(valor3);
            arrJugadores[i].setFicha(new Ficha(arr_Personajes));
        }
    }
    private Personaje asignarElemento(int tipo_elemento) {
        FabricaPersonajes personaje = new FabricaPersonajes();
        switch (tipo_elemento){
            case 1:
                return personaje.crearPersonaje("Fuego");
            case 2:
                return personaje.crearPersonaje("Agua");
            case 3:
                return personaje.crearPersonaje("Planta");
            case 4:
                return personaje.crearPersonaje("Electrico");
            case 5:
                return personaje.crearPersonaje("Roca");
            default:
                //en este caso sería Hielo
                return personaje.crearPersonaje("Hielo");
        }
    }

    //Arreglar la generación para asegurarnos que no se repita las casillas y que ninguna se posicione en la posición 0

    public  ArrayList<Integer> obtenerDiablillos(){
        ArrayList<Integer>posicioDiblillos = new ArrayList<>();
        int contador = 0;

        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaDiablillo) == true){
                posicioDiblillos.add(partida.casillas.indexOf(dato));
            }
            contador= contador++;
        }
        return posicioDiblillos;
    }

    public  ArrayList<Integer> obtenerStones(){
        ArrayList<Integer> posicionStone = new ArrayList<>();
        int contador = 0;
        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaStoneAdapter) == true){
                posicionStone.add(partida.casillas.indexOf(dato));
            }
            contador= contador++;
        }
        return posicionStone;
    }

    public  ArrayList<Integer> obtenerQuerubines(){
        ArrayList<Integer> posicioQueribunes = new ArrayList<>();
        int contador = 0;
        for (Casilla dato:partida.casillas) {
            if ((dato instanceof CasillaQuerubin) == true){
                posicioQueribunes.add(partida.casillas.indexOf(dato));
            }
            contador= contador++;
        }
        return posicioQueribunes;
    }

    public  int obtenerMovimiento(){
        return partida.obtenerNumeroMovimiento();
    }

    public int obtenerNuevaPosicionFicha(int resulDado) throws IOException {
        Ficha fichaActiva=partida.turno.getFicha();
        int posicionActual=obtenerPosicionJugador(fichaActiva);
        int nuevaPosicion=posicionActual+resulDado;
        int cantidadMoviemiento=0;
        if(nuevaPosicion>99){
            nuevaPosicion=99;
//            moverFicha(posicionActual,cantidadMoviemiento,fichaActiva);
        }else{
            if(nuevaPosicion<0){
                nuevaPosicion=0;
//                moverFicha(posicionActual,nuevaPosicion,fichaActiva);
            }
        }
        return nuevaPosicion;
    }



    public int movimientoQuerubin() throws IOException {
        Ficha fichaActiva=partida.turno.getFicha();
        int posicionActual=obtenerPosicionJugador(fichaActiva);
        int nuevaPosicion=posicionActual+11;
//        moverFicha(posicionActual,nuevaPosicion,fichaActiva);

        return nuevaPosicion;
    }

    public int movimientoDiablito() throws IOException {
        Ficha fichaActiva=partida.turno.getFicha();
        int posicionActual=obtenerPosicionJugador(fichaActiva);
        int nuevaPosicion = 0;
        if(posicionActual > 9){
            nuevaPosicion = posicionActual-10;
        }
//        moverFicha(posicionActual,nuevaPosicion,fichaActiva);

        return nuevaPosicion;
    }

    public int movimientoZorvan(int casillasExtra) throws IOException {
        Ficha fichaActiva=partida.turno.getFicha();
        int posicionActual=obtenerPosicionJugador(fichaActiva);

        int nuevaPosicion=99-casillasExtra;

        return nuevaPosicion;
    }


    public int obtenerPosicionJugador(Ficha fichaActiva) {
        int index=0;
        ArrayList<Casilla> casillas=partida.casillas;
        for (int i=0; i<casillas.size();i++){
            ArrayList<Ficha> fichasCasilla=casillas.get(i).getFichas();
            for (int j=0; j<fichasCasilla.size();j++){
                //iterando por todas las fichas de la casilla
                if(fichasCasilla.get(j)==fichaActiva){
                    //una vez q se encuentra donde esta la dicha del jugador, se guarda el numero de la casilla
                    return index=i;
                }
            }
        }
        return index;
    }

    public void moverFicha(int posicionActual, int nuevaPosicion, Ficha ficha) throws IOException {
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

    public Ficha obtenerFicha(){
        return partida.turno.getFicha();


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

    public void Ataque () {

    }
}
