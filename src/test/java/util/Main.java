package util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ab792 on 2017/1/20.
 */
public class Main {
    public static void main(String[] args){
        LinkedList list = new LinkedList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        //System.out.println(list);
        list.addFirst("first");
        //System.out.println(list);
        list.addLast("last");
        //System.out.println(list);
        //LinkedList list2 = (LinkedList) list.clone();
        //System.out.println(list2);
        list.addAll(2,Arrays.asList("i1","i2","i3"));
        //list.add(1,);
        //System.out.println(list.indexOf("first"));
        System.out.println(list);
    }
}
