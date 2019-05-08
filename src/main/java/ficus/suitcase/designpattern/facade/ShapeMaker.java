package ficus.suitcase.designpattern.facade;

import ficus.suitcase.designpattern.factory.Circle;
import ficus.suitcase.designpattern.factory.Rectangle;
import ficus.suitcase.designpattern.factory.Shape;
import ficus.suitcase.designpattern.factory.Square;

/**
 * facdde 模式
 * 统一提供门面服务;
 * 兼容处理场景，但是对于扩展不友好，需要改动到代码；
 *
 * 与装饰器和适配器的区别；
 * 这里facade模式只是通过组合，作简单的功能扩展，统一提供更多的服务
 * 而并非在原来的对象和类上做功能增强和接口的改变，所以是有区别的；
 *
 * @author DamonFicus
 */
public class ShapeMaker {

    private Shape circle;
    private Shape rectangle;
    private Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawSquare() {
        square.draw();
    }

}
