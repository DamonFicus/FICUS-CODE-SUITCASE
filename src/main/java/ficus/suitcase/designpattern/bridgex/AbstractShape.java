package ficus.suitcase.designpattern.bridgex;

/**
 * @author DamonFicus
 * Created by DamonFicus on 2018/11/2.
 */
public abstract class AbstractShape {
    protected DrawApi drawApi;
    protected AbstractShape(DrawApi drawApi){
        this.drawApi = drawApi;
    }

    /**
     * draw methoed
     * @auth DamonFicus
     */
    public abstract void draw();
}
