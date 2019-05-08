package ficus.suitcase.designpattern.facade;

public class FacadeDemo {

    public static void main(String[] args) {
        ShapeMaker sm = new ShapeMaker();
        sm.drawCircle();
        sm.drawRectangle();
        sm.drawSquare();
    }
}
