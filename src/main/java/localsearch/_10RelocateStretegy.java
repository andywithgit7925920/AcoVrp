package localsearch;

import vrp.Solution;
import vrp.Truck;

import static vrp.VRP.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ab792 on 2017/2/18.
 * 路径内点的剥离和插入
 */
public class _10RelocateStretegy implements BaseStretegy {
    public void updateSolution(Solution preSolution) throws Exception {
        if (preSolution == null || preSolution.getTruckSols() == null)
            return;
        LinkedList<Truck> truckSols = preSolution.getTruckSols();
        for (int i = 0; i < truckSols.size(); i++) {
            Truck truck = truckSols.get(i);
            LinkedList<Integer> customers = truck.getCustomers();
            double bestCost = truck.calCost();
            double cost;
            Integer[] point = new Integer[2];
            Truck newTruck;
            double newCost;
            for (int j = 0; j < customers.size() - 1; j++) {
                for (int k = j + 1; k < customers.size(); k++) {
                    /*System.out.println("truck---"+truck);
                    System.out.println("j---"+j);
                    System.out.println("k---"+k);*/
                    newTruck = insert(truck,j,k);
                    if (newTruck.isGoodTruck()){
                        newCost = newTruck.calCost();
                        if (newCost<bestCost){
                            truckSols.remove(i);
                            newTruck.refreshNowCap();
                            truckSols.add(i,newTruck);
                        }
                    }
                    /*cost = calCost(truck, truck, j, k);
                    if (cost > bestCost) {
                        if (insert(truck,j,k).isGoodTruck()){
                            point[0] = j;
                            point[1] = k;
                            bestCost = cost;
                        }
                    }*/
                }
            }
            /*if (bestCost > 0) {
                truck = insert(truck, point[0], point[1]);
            }*/
        }
    }

    public double calCost(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception {
        if (truck1 == null || truck2 == null || truck1.getCustomers().size() <= indexI || truck2.getCustomers().size() <= indexJ)
            throw new Exception("input invalid!");
        LinkedList<Integer> customers = truck1.getCustomers();
        int strippedPoint = customers.get(indexI);  //剥离点
        //寻找被剥离点的前一点和后一点
        int strippedPointPre = (indexI == 0) ? 0 : customers.get(--indexI);
        int strippedPointPost = (indexI + 1 == customers.size()) ? 0 : customers.get(++indexI);
        int insertPoint = customers.get(indexJ);    //插入点
        //插入点的前一点和后一点
        int insertPointPost = (indexJ + 1 == customers.size()) ? 0 : customers.get(++indexJ);
        return distance[strippedPointPre][strippedPoint] + distance[strippedPoint][strippedPointPost] + distance[insertPoint][insertPointPost]
                - distance[strippedPointPre][strippedPointPost] - distance[insertPoint][strippedPoint] - distance[strippedPoint][insertPointPost];
    }

    public Truck  insert(Truck truck, int indexI, int indexJ) throws Exception {
        if (truck == null || truck.getCustomers().size() <= indexI)
            throw new Exception("input invalid!");
        Truck copyTruck = new Truck(truck.getId());
        LinkedList<Integer> customers = truck.getCustomers();
        LinkedList<Integer> newCustomers = new LinkedList<Integer>(customers);
        int temp = newCustomers.get(indexI);
        for (int i = indexI + 1; i <= indexJ; i++) {
            newCustomers.set(i - 1, newCustomers.get(i));
        }
        newCustomers.set(indexJ, temp);
        copyTruck.setCustomers(newCustomers);
        return copyTruck;
    }

}
