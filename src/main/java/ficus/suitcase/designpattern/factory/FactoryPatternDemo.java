package ficus.suitcase.designpattern.factory;

/**
 * @author DamonFicus 20190507
 *
 * 将创建过程对客户端隐藏，提供通用的接口来指向新创建的对象；
 *
 * 主要解决接口选择问题
 *
 * 使用场景：在我们明确计划不同条件下生成不同的实例时使用；
 *
 * 定义一个创建对象的接口，由子类来决定实例化哪一个工厂类，让创建的过程延迟到子类进行；
 *
 *
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();
        Shape rectangle = factory.getShape("rectangle");
        rectangle.draw();
        Shape circle= factory.getShape("circle");
        circle.draw();
        Shape square=factory.getShape("square");
        square.draw();
    }

}
