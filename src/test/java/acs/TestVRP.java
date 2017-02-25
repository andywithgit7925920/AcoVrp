package acs;

import org.junit.Test;
import vrp.Parameter;
import vrp.Solution;
import vrp.Truck;
import vrp.VRP;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by ab792 on 2017/2/20.
 */
public class TestVRP {
    @Test
    public void test() throws IOException {
        VRP.importDataFromSolomon(Parameter.filePathPage);
        Truck truck1 = new Truck(1);
        LinkedList<Integer> customers1 = new LinkedList<Integer>();
        customers1.add(3);
        customers1.add(9);
        customers1.add(5);
        customers1.add(6);
        customers1.add(1);
        truck1.setCustomers(customers1);
        Truck truck2 = new Truck(1);
        LinkedList<Integer> customers2 = new LinkedList<Integer>();
        customers2.add(8);
        truck2.setCustomers(customers2);
        Truck truck3 = new Truck(1);
        LinkedList<Integer> customers3 = new LinkedList<Integer>();
        customers3.add(7);
        customers3.add(2);
        customers3.add(4);
        truck3.setCustomers(customers3);
        Solution solution = new Solution();
        solution.getTruckSols().add(truck1);
        solution.getTruckSols().add(truck2);
        solution.getTruckSols().add(truck3);
        System.out.println("cost--->"+solution.calCost());
        System.out.println("solution--->"+solution);

    }
}
