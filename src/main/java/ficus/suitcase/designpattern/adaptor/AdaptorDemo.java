package ficus.suitcase.designpattern.adaptor;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 适配器模式：
 * 类适配器（继承），对象适配器（组合）
 * @author DamonFicus
 */
public class AdaptorDemo {

    /**
     * 将Enumeration 适配 iterator  对于remove，进行异常处理
     * @param args
     */

    public static void main(String[] args) {
        Hashtable hs = new Hashtable();
        hs.put("a",1);
        hs.put("b",2);
        hs.put("c",3);
        Enumeration enumeration=hs.elements();
        EnumerationIterator enumerationIterator = new EnumerationIterator(enumeration);
        while(enumerationIterator.hasNext()){
            System.out.println(enumerationIterator.next());
        }
    }
}
