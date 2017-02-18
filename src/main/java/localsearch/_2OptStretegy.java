package localsearch;

import vrp.Solution;
import vrp.Truck;

import java.util.LinkedList;

/**
 * Created by ab792 on 2017/2/14.
 */
public class _2OptStretegy implements BaseStretegy {

    /**
     * 对每条路径上的解进行局部搜索
     * @param preSolution
     * @return
     */
    public void updateSolution(Solution preSolution) {
        //System.out.println("_2OptStretegy.updateSolution begin");
        if (preSolution == null || preSolution.getTruckSols() == null)
            return ;
        LinkedList<Truck> truckSols = preSolution.getTruckSols();
        for (int i = 0; i < truckSols.size(); i++) {
            Truck truck = truckSols.get(i);
            LinkedList<Integer> customers = truck.getCustomers();
            double bestCost = truck.calCost();
            Truck newTruck;
            double newCost;
            for (int j = 0; j < customers.size() - 1; j++) {
                for (int k = j + 1; k < customers.size(); k++) {
                    newTruck = _2OptSwap(truck, j, k);
                    newCost = newTruck.calCost();
                    if (newCost < bestCost){
                        truckSols.remove(i);
                        newTruck.refreshNowCap();
                        truckSols.add(i,newTruck);
                    }
                }
            }
        }
        //System.out.println("_2OptStretegy.updateSolution end");
    }

    public double calCost(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception {
        return 0.0;
    }

    /**
     * 对一条路径在indexI、indexJ上进行2-opt交换
     *
     * @param truck
     * @param indexI
     * @param indexJ
     */
    public Truck _2OptSwap(Truck truck, int indexI, int indexJ) {
        if (truck == null || indexI >= indexJ || indexI < 0 || indexJ >= truck.getCustomers().size()) {
            return null;
        }
        Truck copyTruck = new Truck(truck.getId());
        LinkedList<Integer> newCustomers = new LinkedList<Integer>();
        LinkedList<Integer> customers = truck.getCustomers();
        for (int i = 0; i < indexI; i++) {
            newCustomers.add(i, customers.get(i));
        }
        for (int j = indexJ; j >= indexI; j--) {
            newCustomers.add(customers.get(j));
        }
        for (int k = indexJ + 1; k < customers.size(); k++) {
            newCustomers.add(k, customers.get(k));
        }
        copyTruck.setCustomers(newCustomers);
        return copyTruck;
    }
    /*public void _2OptSwap(Truck truck, int indexI, int indexJ) {
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
    }*/
}
