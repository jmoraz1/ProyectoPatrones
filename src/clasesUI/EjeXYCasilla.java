package clasesUI;

public class EjeXYCasilla {

    public int posicion;
    public double layoutX;
    public double layoutY;


    public EjeXYCasilla() {
        posicion = 0;
        layoutX = 0;
        layoutY = 0;

    }

    public EjeXYCasilla(int posicion, double layoutX, double layoutY) {
        this.posicion = posicion;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "EjeXYCasilla{" +
                "posicion=" + posicion +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY +
                '}';
    }
}
