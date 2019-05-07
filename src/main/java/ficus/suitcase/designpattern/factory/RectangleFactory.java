package ficus.suitcase.designpattern.factory;

public class RectangleFactory extends ShapeFactoryMethod {
    @Override
    public Shape createShape() {
        reserveOptions();
        return new Rectangle();
    }
    @Override
    public void reserveOptions(){
        System.out.println("RectangleFactory will create Rectange");
    }
}
