package ficus.suitcase.designPattern.bridge;

/**
 * @author  DamonFicus on 2018/11/2.
 * 桥接模式：
 * 将抽象与实现之间通过桥接结构(桥接接口)进行解耦
 * 主要用来解决的问题：在有多种可能会变化的情况下（多维度变化，比如画图，有形状和颜色两个维度都会变化，就不适合每次增加
 * 一种不同形状和不同颜色的对象，都要去添加新的颜色的形状的具体对象），用继承会造成类爆炸，扩展起来不灵活
 * 何时使用：实现系统可能有多个角度分类，每一种角度都可能变化
 * 优点：抽象与实现分离，扩展能力强，实现细节对客户（调用方）透明
 * 缺点：聚合关联关系建立在抽象层，增加系统理解和设计的复杂度，并需要对抽象进行编程设计
 * 使用场景：
 * 1.对于不想使用继承，因为继承体系引入过多的强关联造成系统急剧增加的臃肿，可以使用桥接模式增加灵活性
 * 2.对于类有多个不同的维度会发生变化，且变化部分之间要独立不影响，并且维度可以做自己的扩展这时可以使用桥接模式来处理；
 */
public class BridgePatternDemo {

    public static void main(String[] args) {

        Shape redCircle = new Circle(100,100, 10, new RedCircle());
        Shape greenCircle = new Circle(100,100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }
}
