package Patterns.Strategy;

public class Estrategia_Roca extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Roca(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Electrico":
            case "Planta":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos de agua, hielo y roca.
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