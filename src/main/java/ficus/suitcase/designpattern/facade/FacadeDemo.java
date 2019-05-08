package ficus.suitcase.designpattern.facade;

/**
 * 外观模式facade定义：
 * 提供了一个统一接口，用来访问子系统中的一群接口，
 * 外观提供了一个高层接口，让子系统可以统一被访问；
 *
 * 相关设计思想：
 * 最少知识原则，即迪米特法则，
 * 不和陌生人交谈；
 *
 * 最少知识原则操作层面为：
 * 就任何对象而言，在该对象的方法内，我们只调用下列东西；
 * 1.该对象本身
 * 2.被当作方法的参数而传进来的对象
 * 3.此方法创建或实例化的对象
 * 4.对象的组件（即类实例变量所引用的对象，HAS-A关系）
 *
 */
public class FacadeDemo {

    public static void main(String[] args) {
        ShapeMaker sm = new ShapeMaker();
        sm.drawCircle();
        sm.drawRectangle();
        sm.drawSquare();
    }
}
