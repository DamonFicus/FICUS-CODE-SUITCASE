package ficus.suitcase.designPattern.bridge;

/**
 * Created by DamonFicus on 2018/11/2.
 */
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }
    public abstract void draw();
}
