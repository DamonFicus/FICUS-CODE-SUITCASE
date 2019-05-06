package ficus.suitcase.designpattern.observer;

/**
 * @author DamonFicus on 20190505
 * 观察者模式：
 * 多个观察者对主题对象状态的监控，
 * 当有主题状态变化时通知所有观察者，观察者进而执行动作；
 *
 * 1.观察者多对一依赖主题
 * 2.主题在变化时，可以统一通知观察者
 * 3.数据结构有一个存放观察者列表的集合
 *
 * 除了可以增加观察者，也可以移除观察者
 *
 * 主题-订阅模式
 *
 * 因为主题是真正拥有数据的人，观察者是主题的依赖者
 * 在数据变化时更新，这样，不用每个观察者独立控制一份数据，这样
 * 可以得到更干净的OO设计；
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
       Subject subject= new Subject();
       BinaryObserver bs= new BinaryObserver(subject);
       OctalObserver  os= new OctalObserver(subject);
       HexaObserver   hs= new HexaObserver(subject);

        subject.setState(17);
        System.out.println("*************");
        subject.remove(bs);
        subject.remove(os);
        subject.setState(18);

    }
}
