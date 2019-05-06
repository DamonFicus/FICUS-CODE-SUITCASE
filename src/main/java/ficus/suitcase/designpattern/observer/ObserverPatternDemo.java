package ficus.suitcase.designpattern.observer;

/**
 * @author DamonFicus on 20190505
 * 观察者模式：
 * 多个观察者对主题对象状态的监控，
 * 当有主题状态变化时通知所有观察者，观察者进而执行动作；
 *
 * 一对多依赖
 *
 * 除了可以增加观察者，也可以移除观察者
 *
 * 主题-订阅模式
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
