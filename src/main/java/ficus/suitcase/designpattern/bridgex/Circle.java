package ficus.suitcase.designpattern.bridgex;

/**
 * Created by DamonFicus on 2018/11/2.
 * @author DamonFicus
 */
public class Circle extends AbstractShape {
    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawApi drawApi) {
        super(drawApi);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawApi.drawCircle(radius,x,y);
    }
}
