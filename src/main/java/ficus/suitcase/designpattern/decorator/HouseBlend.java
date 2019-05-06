package ficus.suitcase.designpattern.decorator;


/**
 * 混合咖啡
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description="House Blend coffee";
    }

    @Override

    public double cost() {
        return 0.89;
    }
}
