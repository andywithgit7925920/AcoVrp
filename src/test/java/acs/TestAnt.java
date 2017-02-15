package acs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ab792 on 2017/1/5.
 */
public class TestAnt {
    @Test
    public void test(){
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(5);
        list1.add(9);
        list1.add(13);
        list1.add(18);
        list1.add(21);
        for (Integer i: list1){
            System.out.print(i+" ");
        }
        System.out.println();
        list1.remove(5);
        for (Integer i: list1){
            System.out.print(i+" ");
        }
    }

    @Test
    public void testMatrix(){
        //double[][] d1 = new
    }
}
