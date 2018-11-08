package ficus.suitcase.designPattern.bridge;

/**
 * Created by DamonFicus on 2018/11/2.
 */
public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}