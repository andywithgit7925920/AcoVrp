package localsearch;

import vrp.Parameter;
import vrp.Solution;
import vrp.Truck;
import vrp.VRP;

import java.util.LinkedList;

/**
 * Created by ab792 on 2017/2/24.
 */
public class Test {
    @org.junit.Test
    public void test() throws Exception {
        VRP.importDataFromSolomon(Parameter.filePathPage);

        LinkedList<Truck> truckSols = new LinkedList<Truck>();
        LinkedList<Integer> customers1 = new LinkedList<Integer>();
        LinkedList<Integer> customers2 = new LinkedList<Integer>();
        LinkedList<Integer> customers3 = new LinkedList<Integer>();
        customers1.add(9);
        customers1.add(5);
        customers1.add(6);
        customers1.add(1);
        customers2.add(8);
        customers2.add(3);
        customers3.add(7);
        customers3.add(2);
        customers3.add(4);
        Truck truck1 = new Truck(1);
        Truck truck2 = new Truck(2);
        Truck truck3 = new Truck(3);
        truck1.setCustomers(customers1);
        truck2.setCustomers(customers2);
        truck3.setCustomers(customers3);
        truckSols.add(truck1);
        truckSols.add(truck2);
        truckSols.add(truck3);
        Solution solution = new Solution(truckSols);
        _10Relocate$Stretegy stretegy = new _10Relocate$Stretegy();
        stretegy.updateSolution(solution);
    }
}
