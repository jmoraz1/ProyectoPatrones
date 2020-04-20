package MainController;

import Entities.*;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Decorator.Ataque;
import Patterns.FactoryMethod.FabricaElementos;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaNormal;
import Patterns.Prototype.CasillaQuerubin;
import Patterns.Prototype.Manager;
import Patterns.Proxy.IMainController;
import Patterns.Strategy.*;
import Patterns.Visitor.RecibirAtaque;
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
        if (updated.contains(partida.obtenerJugador(jugador))){
            for (Jugador j :updated) {
                if (j.getNombre()==jugador){
                    if (j.contadorUpdates==2){
                        j.contadorUpdates=0;
                        updated.remove(j);
                        break;
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


    public boolean stoneVencido (String jugador) {
        Ficha f = partida.obtenerJugador(jugador).ficha;
        ArrayList<Ficha> af = new ArrayList<>();

        for (Casilla c: partida.casillas) {
            af = c.getFichas();
            for (Ficha ficha:af) {
                if ((ficha.equals(f)==true) && (c instanceof CasillaStoneAdapter)){

                    if (((CasillaStoneAdapter) c).getStone().getVida() > 0){
                        return false;
                    } else {
//                        ((CasillaStoneAdapter) c).getStone().setVida(100);
                        return true;
                    }
                }

            }
        }

        return  false;
    }

    public boolean stoneVencidoParaAgua(String jugador) {
        Ficha f = partida.obtenerJugador(jugador).ficha;
        ArrayList<Ficha> af = new ArrayList<>();

        for (Casilla c: partida.casillas) {
            af = c.getFichas();
            for (Ficha ficha:af) {
                if ((ficha.equals(f)==true) && (c instanceof CasillaStoneAdapter)){

                    if (((CasillaStoneAdapter) c).getStone().getVida() > 0){
                        return false;
                    } else {

                        return true;
                    }
                }

            }
        }

        return  false;
    }


    public String Ataque (String jugador, ArrayList<Elemento> arr_elementos) {
        Ficha f = partida.obtenerJugador(jugador).ficha;
        ArrayList<Ficha> af = new ArrayList<>();
        Casilla casilla = new CasillaStoneAdapter(0);
        int ataque = 0;
        ArrayList<Elemento> arr_elementos_Stone= new ArrayList<>();
        for (Casilla c: partida.casillas) {
            af = c.getFichas();

            for (Ficha ficha:af) {
                if ((ficha.equals(f)==true) && (c instanceof CasillaStoneAdapter)){
                    casilla = c;
                    arr_elementos_Stone = ((CasillaStoneAdapter) c).getStone().getElementos();
                    break;
                }
            }

        }

        AtaqueElemento ae;
        for (Elemento e : arr_elementos) {
            switch (e.getTipo()){
                case "Agua":
                    ae = new Estrategia_Agua("Agua", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
                case "Electrico":
                    ae = new Estrategia_Electrico("Electrico", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
                case "Fuego":
                    ae = new Estrategia_Fuego("Fuego", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
                case "Hielo":
                    ae = new Estrategia_Hielo("Hielo", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
                case "Planta":
                    ae = new Estrategia_Planta("Planta", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
                case "Roca":
                    ae = new Estrategia_Roca("Roca", arr_elementos_Stone);
                    ae.Evaluar_Ventaja();
                    ataque = ataque + ae.getAtaque();
                    break;
            }
        }
        RecibirAtaque ra = new RecibirAtaque();

        for (Jugador j:updated) {
            if (j.getNombre()==jugador){
                ra.visit(casilla, ataque+5);

                if (((CasillaStoneAdapter) casilla).getStone().getVida() > 0){
                    return "La vida actual del stone es: "+((CasillaStoneAdapter) casilla).getStone().getVida()+", su ataque ha sido de: "+(ataque+5);
                } else {
                    return "Usted ha vencido el stone"+", su ataque ha sido de: "+(ataque+5);
                }
            }

        }
        ra.visit(casilla, ataque);

        if (((CasillaStoneAdapter) casilla).getStone().getVida() > 0){
            return "La vida actual del stone es: "+((CasillaStoneAdapter) casilla).getStone().getVida()+", su ataque ha sido de: "+ataque;
        } else {
            return "Usted ha vencido el stone"+", su ataque ha sido de: "+ataque;
        }

    }

    @Override
    public MainController obtenerMainController(String psw) throws IOException {
        return this;
    }

    public boolean casillaStone(String nombre){
        Ficha f = partida.obtenerJugador(nombre).getFicha();

        for (Casilla c: partida.casillas) {
            ArrayList<Ficha> af = c.getFichas();
            for (Ficha ficha:af) {
                if ((ficha.equals(f)==true) && (c instanceof CasillaStoneAdapter)){
                    return true;
                }
            }
        }
        return false;
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
            String s = jugador+ " le otorga cinco puntos extra al personaje de "+e.getTipo()+" en la triada por dos turnos";
            Jugador je = partida.obtenerJugador(jugador);
            return s;
    }


    public String poderAgua(){
        return "Su poder especial le permite lanzar de nuevo el dado de ataque";
    }

    public int obtenerIndice(String jugador){
        int indice=0;
        Ficha f = partida.obtenerJugador(jugador).getFicha();
        for (Casilla c:partida.casillas) {
            ArrayList<Ficha> fichas = c.getFichas();
            for (Ficha fi:fichas) {
                if (fi.equals(f)){
                    return indice;
                }
            }
            indice++;
        }
        return 0;
    }

    public String poderRoca(String jugador) throws IOException {
        //cambiar string
        String s = "Un jugador ha colocado un stone en la casilla de "+jugador;
            Jugador j = partida.obtenerJugador(jugador);
            CasillaStoneAdapter casillaStoneAdapter= generarStone(j.ficha);
            partida.casillas.set((obtenerIndice(jugador)),casillaStoneAdapter);
            
        return s;
    }

    private CasillaStoneAdapter generarStone(Ficha ficha) throws IOException {
        Personaje[] p = ficha.getPersonajes();
        Elemento uno= p[0].getElemento();
        Elemento dos=p[1].getElemento();
        Elemento tres=p[2].getElemento();
        ArrayList<Elemento> elementoNuevoStone=new ArrayList<>();
        FabricaElementos fe= new FabricaElementos();

        switch (uno.getTipo()){
            case "Fuego":
                Elemento a =fe.obtenerElemento("Roca");
                elementoNuevoStone.add(a);
                break;
            case "Agua":
                Elemento b =fe.obtenerElemento("Hielo");
                elementoNuevoStone.add(b);
                break;
            case "Planta":
                Elemento c =fe.obtenerElemento("Electrico");
                elementoNuevoStone.add(c);
                break;
            case "Electrico":
                Elemento d =fe.obtenerElemento("Planta");
                elementoNuevoStone.add(d);
                break;
            case "Roca":
                Elemento e =fe.obtenerElemento("Agua");
                elementoNuevoStone.add(e);
                break;
            default:
                Elemento f =fe.obtenerElemento("Fuego");
                elementoNuevoStone.add(f);
                break;
        }
        switch (dos.getTipo()){
            case "Fuego":
                Elemento a =fe.obtenerElemento("Roca");
                elementoNuevoStone.add(a);
                break;
            case "Agua":
                Elemento b =fe.obtenerElemento("Hielo");
                elementoNuevoStone.add(b);
                break;
            case "Planta":
                Elemento c =fe.obtenerElemento("Electrico");
                elementoNuevoStone.add(c);
                break;
            case "Electrico":
                Elemento d =fe.obtenerElemento("Planta");
                elementoNuevoStone.add(d);
                break;
            case "Roca":
                Elemento e =fe.obtenerElemento("Agua");
                elementoNuevoStone.add(e);
                break;
            default:
                Elemento f =fe.obtenerElemento("Fuego");
                elementoNuevoStone.add(f);
                break;
        }
        switch (tres.getTipo()){
            case "Fuego":
                Elemento a =fe.obtenerElemento("Roca");
                elementoNuevoStone.add(a);
                break;
            case "Agua":
                Elemento b =fe.obtenerElemento("Hielo");
                elementoNuevoStone.add(b);
                break;
            case "Planta":
                Elemento c =fe.obtenerElemento("Electrico");
                elementoNuevoStone.add(c);
                break;
            case "Electrico":
                Elemento d =fe.obtenerElemento("Planta");
                elementoNuevoStone.add(d);
                break;
            case "Roca":
                Elemento e =fe.obtenerElemento("Agua");
                elementoNuevoStone.add(e);
                break;
            default:
                Elemento f =fe.obtenerElemento("Fuego");
                elementoNuevoStone.add(f);
                break;
        }
        Manager mng=new Manager();
        CasillaStoneAdapter csa= mng.generarCasillaStone();
        csa.setElementosStone(elementoNuevoStone);
        csa.setVidaStone(60);
        csa.setFicha(ficha);
        return csa;
    }
}
