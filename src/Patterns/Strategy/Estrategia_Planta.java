package Patterns.Strategy;

public class Estrategia_Planta extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Planta(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Agua":
            case "Electrico":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos de fuego, hielo, planta y roca.
                this.validacion = false;
                break;
        }

        return validacion;
    }

    @Override
    public boolean Evaluar_Ventaja() {
        return validacionDeElemento(getNombreElemento());
    }
}
