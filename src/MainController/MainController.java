package MainController;

import Entities.*;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Decorator.Ataque;
import Patterns.FactoryMethod.FabricaElementos;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaQuerubin;
import Patterns.Proxy.IMainController;
//import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class MainController implements IMainController{

    public static Tablero partida;
    public ArrayList<Jugador> congelados = new ArrayList<>();
    public ArrayList<Jugador> dadoLimitado = new ArrayList<>();
    public ArrayList<Jugador> paralizados = new ArrayList<>();
    public ArrayList<Jugador> updated = new ArrayList<>();

   /* public static void main(String[] args) {

    }*/


    //El tablero en el atributo turno ya posee el jugador que tiene el turno uno
    public  Tablero NuevaPartida(ArrayList<Jugador> jugadores)  throws IOException {
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

    }

    private void asignarFichas(Jugador[] arrJugadores) {
        for (int i = 0; i < arrJugadores.length; i++) { // esto lo hace por cada jugador
            FabricaElementos Personaje1; // esto representa
            FabricaElementos Personaje2; // esto representa
            FabricaElementos Personaje3; // esto representa
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
        FabricaElementos personaje = new FabricaElementos();
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

    public  int obtenerMovimiento(String jugador){
        Jugador tmp = partida.obtenerJugador(jugador);
        if (dadoLimitado.contains(tmp)){
            for (Jugador j:dadoLimitado) {
                if (j.equals(tmp)){
                    if (j.contadorPlanta== 2){
                        j.setContadorPlanta(0);
                        dadoLimitado.remove(j);
                        break;
                    }else{
                        j.setContadorPlanta(j.getContadorPlanta()+1);
                        return partida.dadoMovimiento.girarLimitado();
                    }
                }
            }
            return partida.dadoMovimiento.girarLimitado();
        }
        if (paralizados.contains(tmp)){
            for (Jugador j:paralizados) {
                if (j.equals(tmp)){
                    if (j.contadorElectrico== 3){
                        j.setContadorElectrico(0);
                        paralizados.remove(j);
                        break;
                    }else{
                        j.setContadorElectrico(j.getContadorElectrico()+1);
                        return 0;
                    }
                }
            }
        }
        if (congelados.contains(tmp)){
            for (Jugador j:congelados) {
                if (j.equals(tmp)) {
                    if (j.contadorHielo == 1) {
                        j.setContadorElectrico(0);
                        paralizados.remove(j);
                        break;
                    } else {
                        j.setContadorHielo(j.getContadorElectrico() + 1);
                        return 0;
                    }
                }
            }
        }
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
        String ataque="";
        partida.dadoAtaque=new Ataque(partida.dadoAtaque);
        int res =  partida.obtenerTipoAtaque();
        ArrayList<String>ataques=new ArrayList<>();
        if(partida.dadoAtaque instanceof Ataque){
            ataques=((Ataque) partida.dadoAtaque).getAtaques();

        }
        ataque=ataques.get(res-1);
        return ataque;
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

    @Override
    public MainController obtenerMainController(String psw) throws IOException {
        return this;
    }


    public String poderPlanta(String jugador){
        String s ="Por dos turnos no deja que "+jugador+"  saque mas de tres en su dado de movimientos";
        dadoLimitado.add(partida.obtenerJugador(jugador));
        return s;
    }

    public String poderElectrico(String jugador){
        String s = "Causa una parálisis que  evita que "+jugador+" tire el dado de movimiento, este efecto dura tres turnos";
        Jugador tmp = partida.obtenerJugador(jugador);
        paralizados.add(tmp);
        return s;
    }

    public String poderHielo(String jugador){
        String s = "Congela a "+jugador+" por un turno";
        Jugador tmp = partida.obtenerJugador(jugador);
        congelados.add(tmp);
        return s;
    }

    public String poderFuego(String jugador, Elemento e){
        String s = jugador+ "le otorga cinco puntos extra al personaje de "+e+" en la triada por dos turnos";
        updated.add(partida.obtenerJugador(jugador));
        return s;
    }
}
