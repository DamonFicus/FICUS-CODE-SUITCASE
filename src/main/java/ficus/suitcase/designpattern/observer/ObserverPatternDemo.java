package ficus.suitcase.designpattern.observer;

/**
 * @author DamonFicus on 20190505
 * 观察者模式：
 * 对多个对象状态的监控，当有变化时进行通知，并执行动作；
 *
 *
 *
 *
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        Subject subject= new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        new HexaObserver(subject);

        subject.setState(17);
        System.out.println("*************");
        subject.setState(18);

    }



}
