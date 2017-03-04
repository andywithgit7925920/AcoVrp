package localsearch;

import util.DataUtil;
import vrp.Solution;
import vrp.Truck;

import static vrp.VRP.*;

import java.util.LinkedList;

/**
 * Created by ab792 on 2017/2/24.
 */
public class _10Relocate$Stretegy implements BaseStretegy {
    public void updateSolution(Solution preSolution) throws Exception {
        //System.out.println("preSolution pre--->"+preSolution);
        LinkedList<Truck> truckSols = preSolution.getTruckSols();
        //对于每一组
        for (int i = 0, len1 = truckSols.size(); i < len1; i++) {
            Truck nowTruck = truckSols.get(i);
            //保存一组Truck互换的点
            Integer[] nodesMap = new Integer[2];
            //互换的Truck
            Truck[] truckMap = new Truck[2];
            double maxSpan = Double.MIN_VALUE;
            //与其余组中进行比较
            for (int j = 0; j < len1; j++) {
                if (i != j) {
                    Truck compareTruck = truckSols.get(j);
                    //当前组中的每一个点
                    for (int k = 0; k < nowTruck.size(); k++) {
                        //System.out.println("solution--->" + preSolution);
                        //其余组中的每一个插入点
                        for (int m = 0; m <= compareTruck.size(); m++) {
                            //double tempCost = calCost(nowTruck,compareTruck,k,m);
                            /*System.out.println("i--->"+i);
                            System.out.println("j--->"+j);
                            System.out.println("k--->"+k);
                            System.out.println("m--->"+m);
                            System.out.println("nowTruck--->"+nowTruck);
                            System.out.println("compareTruck--->"+compareTruck);*/
                            double preCost = nowTruck.calCost() + compareTruck.calCost();
                            insert(nowTruck, compareTruck, k, m);
                            double postCost = nowTruck.calCost() + compareTruck.calCost();
                            double tempCost = preCost - postCost;
                            //System.out.println("tempCost--->"+tempCost);
                            if (DataUtil.more((tempCost), 0.0)) {
                                //System.out.println("tempCost 大于0");
                                if (DataUtil.more(tempCost, maxSpan)) {
                                    //System.out.println("DataUtil.more(tempCost, maxSpan)");
                                    //System.out.println(nowTruck);
                                    //System.out.println(compareTruck);
                                    if (nowTruck.isGoodTruck() && compareTruck.isGoodTruck()) {
                                        //System.out.println("mark1");
                                        nodesMap[0] = k;
                                        nodesMap[1] = m;
                                        truckMap[0] = nowTruck;
                                        truckMap[1] = compareTruck;
                                        maxSpan = tempCost;
                                    }
                                }
                            }
                            insert(compareTruck, nowTruck, m, k);
                        }
                    }
                }


            }
//当前组交换的最大值

            if (truckMap[0] != null && truckMap[1] != null && nodesMap[0] != null && nodesMap[1] != null) {
                /*System.out.println("action1");
                System.out.println("truck1--->"+truckMap[0]);
                System.out.println("truck2--->"+truckMap[1]);
                System.out.println("k--->"+nodesMap[0]);
                System.out.println("m--->"+nodesMap[1]);*/
                insert(truckMap[0], truckMap[1], nodesMap[0], nodesMap[1]);
            }
        }
        preSolution.refresh();
        //System.out.println("preSolution post--->"+preSolution);
    }

    /*public double calCost(Truck truck1, Truck truck2, int customerPos, int insertIndex) throws Exception {
        if (truck1 == null || truck2 == null || customerPos < 0 || insertIndex < 0)
            throw new Exception("input invalid!");
        LinkedList<Integer> customers1 = truck1.getCustomers();
        LinkedList<Integer> customers2 = truck2.getCustomers();
        int point = customers1.get(customerPos);
        int prePoint = customers1.get((customerPos - 1) < 0 ? 0 : (customerPos - 1));
        int postPoint = customers1.get((customerPos + 1) >= customers1.size() ? (customers1.size() - 1) : (customerPos + 1));
        int preInsertPoint = customers2.get((insertIndex - 1) < 0 ? 0 : (insertIndex - 1));
        int postInsertPoint = customers2.get(insertIndex >= customers2.size() ? customers2.size() - 1 : insertIndex);
        double cost1;
        double cost2;
        cost1 = distance[prePoint][point]//
                + distance[point][postPoint]//
                + distance[preInsertPoint][postInsertPoint];

        cost2 = distance[prePoint][postPoint]//
                + distance[preInsertPoint][point]//
                + distance[point][postInsertPoint];
        if (customerPos == 0) {
            cost2 -= distance[prePoint][postPoint];
        } else if (customerPos == customers1.size() - 1) {
            cost2 -= distance[prePoint][postPoint];
        }
        if (insertIndex == 0) {
            cost2 -= distance[preInsertPoint][point];
        } else if (insertIndex == customers2.size()) {
            cost2 -= distance[point][postInsertPoint];
        }
        return cost1 - cost2;
    }*/


    public static void insert(Truck truck1, Truck truck2, int customerPos, int insertIndex) throws Exception {
        //System.out.println("_10Relocate$Stretegy.insert");
        //System.out.println("truck1--->" + truck1);
        //System.out.println("truck2--->" + truck2);
        if (truck1 == null || truck2 == null || customerPos < 0 || insertIndex < 0)
            throw new Exception("input invalid!");
        customerPos = (customerPos >= truck1.getCustomers().size()) ? truck1.getCustomers().size() - 1 : customerPos;
        insertIndex = (insertIndex > truck2.getCustomers().size()) ? truck2.getCustomers().size() : insertIndex;
        LinkedList<Integer> customers1 = truck1.getCustomers();
        LinkedList<Integer> customers2 = truck2.getCustomers();
        //System.out.println("customerPos--->" + customerPos);
        //System.out.println("insertIndex--->" + insertIndex);
        if (customers1.isEmpty())
            return;
        int temp = customers1.remove(customerPos);
        customers2.add(insertIndex, temp);
        //System.out.println("truck1- after-->" + truck1);
        //System.out.println("truck2 -after-->" + truck2);
    }
}
