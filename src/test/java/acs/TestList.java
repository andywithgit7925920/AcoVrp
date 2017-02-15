package acs;

import java.util.LinkedList;

/**
 * Created by ab792 on 2017/2/15.
 */
public class TestList {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        System.out.println(list);
        list.remove(1);
        list.add(1,5);
        System.out.println(list);
    }
}
