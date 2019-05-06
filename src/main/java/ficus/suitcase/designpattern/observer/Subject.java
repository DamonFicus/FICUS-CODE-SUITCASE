package ficus.suitcase.designpattern.observer;

import java.util.ArrayList;
import java.util.List;


/**
 * 主题提供的接口为：
 * 注册，移除，通知
 * 这里可以扩展移除观察者逻辑
 *
 * 主题是有状态的对象，并且可以改变它的状态
 * @author DamonFicus on 20190506
 */
public class Subject {

    private  List<Observer> observerList=new ArrayList<>();

    private  int state;

    public int getState(){
        return state;
    }

    public void setState(int state){
        this.state=state;
        notifyAllObservers();
    }

    public void attatch(Observer observer){
        observerList.add(observer);
    }

    public void remove(Observer observer){
        if(observerList.contains(observer)){
            observerList.remove(observer);
        }
    }

    public void remove2(Observer observer){
       int i=observerList.indexOf(observer);
       //如果不存在，则会返回-1
       if(i>0){
           observerList.remove(i);
       }
    }


    public void notifyAllObservers(){
        for(Observer observer:observerList){
            observer.update();
        }
    }

    public List<Observer> getObserverList(){
        return observerList;
    }


}
