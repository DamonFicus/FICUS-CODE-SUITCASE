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
 * *******************************************************************************
 *
 *
 * 工厂方法模式：
 * 定义了一个创建对象接口，但由子类决定要实例化的是哪一个类，工厂方法让类把实例化推迟到
 * 子类
 *
 * 与简单的工厂的区别，是简单工厂把所有的事情在一个地方处理了，这样会产生耦合，如果发生改变就需要
 * 改到代码，不利于扩展，而工厂方法是把实例化推迟到了子类，这样会更加灵活；简单工厂可以把创建封装起来
 * 但是简单工厂不能变更正在创建的产品；
 *
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        //简单工厂模式：所有具体对象的生产，在一个工厂里面；如果涉及到具体对象的扩展，添加或删除，就会动到工厂代码
        //ShapeFactory factory = new ShapeFactory();
        //Shape rectangle = factory.getShape("rectangle");
        //rectangle.draw();
        //Shape circle= factory.getShape("circle");
        //circle.draw();
        //Shape square=factory.getShape("square");
        //square.draw();

        //工厂方法模式：新增产品，需要增加一个对应产口的工厂，然后由这个对应的工厂去负责生产，避免修改到原来的代码逻辑
        ShapeFactoryMethod sfm=new SquareFactory();
        sfm.createShape();
        sfm=new RectangleFactory();
        sfm.createShape();
    }

}
