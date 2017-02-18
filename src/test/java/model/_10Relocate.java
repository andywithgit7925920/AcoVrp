package model;

import localsearch.BaseStretegy;
import org.junit.Test;
import vrp.Solution;
import vrp.Truck;

import java.util.LinkedList;

import static vrp.VRP.distance;

/**
 * Created by ab792 on 2017/2/18.
 * 路径内点的剥离和插入
 */
public class _10Relocate{

    public static void insert(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception {
        if (truck1 == null || truck2 == null || truck1.getCustomers().size() <= indexI || truck2.getCustomers().size() <= indexJ)
            throw new Exception("input invalid!");
        LinkedList<Integer> customers = truck1.getCustomers();
        int temp = customers.get(indexI);
        for (int i = indexI+1;i <=indexJ;i++){
            customers.set(i-1,customers.get(i));
        }
        customers.set(indexJ,temp);
    }

    @Test
    public void test() throws Exception {
        LinkedList<Integer> customers = new LinkedList<Integer>();
        for (int i=0;i<8;i++){
            customers.add(i);
        }
        Truck truck = new Truck(1);
        truck.setCustomers(customers);
        System.out.println(truck.getCustomers());
        insert(truck,truck,2,3);
        System.out.println(truck.getCustomers());
    }

}
