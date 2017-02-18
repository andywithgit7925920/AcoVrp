package acs;

import localsearch._2Opt$Stretegy;
import org.junit.Test;
import util.ArrayUtil;
import vrp.Truck;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by ab792 on 2017/1/10.
 */
public class Main {
    public void _2OptSwap(Truck truck, int indexI, int indexJ) {
        if (truck == null || indexI <= indexJ || indexI < 0 || indexJ >= truck.getCustomers().size()) {
            return;
        }
        LinkedList<Integer> customers = truck.getCustomers();
        Integer[] cusArr = new Integer[customers.size()];
        customers.toArray(cusArr);
        int i1 = indexI;
        int i2 = indexJ;
        int span = (i2 - i1) / 2;
        for (int i = i1; i <= i1 + span; i++) {
            int temp = cusArr[i];
            cusArr[i] = cusArr[i2];
            cusArr[i2] = temp;
            --i2;
        }
        customers = new LinkedList<Integer>(Arrays.asList(cusArr));
    }

    public static void main(String[] args) {
        LinkedList<Character> list = new LinkedList<Character>();
        for (int i = 'A'; i <= 'H'; i++) {
            list.add((char) i);
        }
        list.add('A');
        //System.out.println(list);
        Character[] arr = new Character[list.size()];
        list.toArray(arr);
        ArrayUtil.printArr(arr);
        int i1 = 0;
        int i2 = 8;
        int span = (i2 - i1) / 2;
        System.out.println(span);
        for (int i = i1; i <= i1 + span; i++) {
            char temp = arr[i];
            arr[i] = arr[i2];
            arr[i2] = temp;
            --i2;
        }
        System.out.println(span);
        for (int i = i1; i <= i1 + span; i++) {
            char temp = arr[i];
            arr[i] = arr[i2];
            arr[i2] = temp;
            --i2;
        }
        ArrayUtil.printArr(arr);

    }

    @Test
    public void test() throws Exception {
        Truck truck1 = new Truck(1);
        Truck truck2 = new Truck(2);
        LinkedList<Integer> list1 = new LinkedList<Integer>();
        LinkedList<Integer> list2 = new LinkedList<Integer>();
        for (int i = 0; i < 6; i++) {
            list1.add(i);
        }
        for (int i = 6; i < 13; i++) {
            list2.add(i);
        }
        truck1.setCustomers(list1);
        truck2.setCustomers(list2);
        System.out.println(truck1);
        System.out.println(truck2);
        _2Opt$Stretegy._2Opt$Swap(truck1,truck2, 0, 6);
        _2Opt$Stretegy._2Opt$Swap(truck1,truck2, 0, 6);
        System.out.println(truck1);
        System.out.println(truck2);
    }

}
