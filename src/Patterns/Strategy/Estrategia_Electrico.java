package Patterns.Strategy;

public class Estrategia_Electrico extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Electrico(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Agua":
            case "Hielo":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos electricos, planta y de roca.
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
