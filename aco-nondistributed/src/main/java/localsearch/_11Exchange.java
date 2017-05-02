package localsearch;

import vrp.Solution;
import vrp.Truck;
import vrp.VRP;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import static util.FileUtil.separator;
import static vrp.VRP.importDataFromSolomon;

/**
 * Created by ab792 on 2017/5/2.
 */
public class _11Exchange implements BaseStretegy {
    @Override
    public void updateSolution(Solution preSolution) throws Exception {
        if (preSolution == null || preSolution.getTruckSols() == null)
            return;
        LinkedList<Truck> truckSols = preSolution.getTruckSols();
        for (int i = 0; i < truckSols.size(); i++) {
            Truck truck = truckSols.get(i);
            LinkedList<Integer> customers = truck.getCustomers();
            double bestCost = truck.calCost();
            Truck newTruck;
            double newCost;
            for (int j = 0; j < customers.size() - 1; j++) {
                for (int k = j + 1; k < customers.size(); k++) {
                    newTruck = _11exchange(truck, j, k);
                    if (newTruck.isGoodTruck()) {
                        newCost = newTruck.calCost();
                        if (newCost < bestCost) {
                            truckSols.remove(i);
                            newTruck.refreshNowCap();
                            truckSols.add(i, newTruck);
                        }
                    }
                }
            }
        }
    }

    /**
     * 在一条路径内互换两个客户点
     *
     * @param truck
     * @param indexI
     * @param indexJ
     * @return
     */
    public Truck _11exchange(Truck truck, int indexI, int indexJ) {
        if (truck == null || indexI >= indexJ || indexI < 0 || indexJ >= truck.getCustomers().size()) {
            return null;
        }
        Truck copyTruck = new Truck(truck.getId());
        LinkedList<Integer> newCustomers = new LinkedList<>(truck.getCustomers());
        int temp1 = newCustomers.get(indexI);
        int temp2 = newCustomers.get(indexJ);
        newCustomers.remove(indexI);
        newCustomers.add(indexI, temp2);
        newCustomers.remove(indexJ);
        newCustomers.add(indexJ, temp1);
        copyTruck.setCustomers(newCustomers);
        return copyTruck;
    }
}
