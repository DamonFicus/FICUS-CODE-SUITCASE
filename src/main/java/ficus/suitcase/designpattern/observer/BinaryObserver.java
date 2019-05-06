package ficus.suitcase.designpattern.observer;

import java.util.List;

public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject){
           this.subject=subject;
            List<Observer> obList= this.subject.getObserverList();
            //做遍历去重，或者直接使用去重数据结构set
           for(Observer ob:obList){
               if(!subject.getObserverList().contains(ob)){
                   this.subject.attatch(this);
               }else{
                   continue;
               }
           }
    }
    @Override
    public void update() {
        System.out.println( "Binary String: "
                + Integer.toBinaryString( subject.getState() ) );
    }
}
