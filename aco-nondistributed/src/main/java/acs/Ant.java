package acs;

import java.io.Serializable;
import util.ArrayUtil;
import util.DataUtil;
import vrp.Solution;
import vrp.Truck;
import vrp.VRP;
import java.util.*;
import parameter.Parameter;

/**
 * Created by ab792 on 2016/12/30.
 */
public class Ant implements Serializable {
    private static final long serialVersionUID = -6878808733419080363L;
    private int id;
    private Solution solution;
    private int[] allowedClient;  //允许访问的城市
    private int[] visitedClient;    //取值0或1，1表示已经访问过，0表示未访问过
    private double[][] delta;   //信息素变化矩阵

    public Ant() {
        allowedClient = new int[VRP.clientNum];
        delta = new double[VRP.clientNum][VRP.clientNum];
        solution = new Solution();
    }

    public Ant(int id){
        this();
        this.id = id;
    }
    /**
     * 蚂蚁初始化
     *
     * @param
     */
    public void init() {
        //将蚂蚁初始化在出发站
        visitedClient = new int[VRP.clientNum];
        //默认开始从起始点出发
        visitedClient[0] = 1;
        ArrayUtil.initIntegerArray2One(allowedClient);
    }

    /**
     * 蚂蚁搜寻一条路径
     * @param pheromone
     */
    /*public void traceRoad(double[][] pheromone){
        while (!visitFinish()) {
            selectNextClient(pheromone);
        }
    }*/
    public Ant traceRoad(double[][] pheromone){
        while (!visitFinish()) {
            selectNextClient(pheromone);
        }
        return this;
    }
    /**
     * 选择下一个城市
     *
     * @param pheromone
     */
    public void selectNextClient(double[][] pheromone) {
        double[] p = new double[VRP.clientNum];
        double sum = 0.0;
        Truck currTruck = solution.getCurrentTruck();
        int currentCus = currTruck.getCurrentCus();
        Map<Integer,Double> map = new HashMap<>(VRP.clientNum);
        //计算分母部分
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                double waitTime = VRP.time[i][0] - (currTruck.calNowServiceTime() + VRP.distance[currentCus][i]);
                waitTime = (DataUtil.le(waitTime, 0.0)) ? 0.1 : waitTime;
                double saved = VRP.savedQnuantity[currentCus][i];
                saved = (DataUtil.le(saved, 0.0)) ? 0.1 : saved;
                double temp = Math.pow(pheromone[currentCus][i], Parameter.ALPHA)
                        * Math.pow(1.0 / VRP.distance[currentCus][i], Parameter.BETA)
                        * Math.pow(1.0 / VRP.time[i][2], Parameter.GAMMA)
                        * Math.pow(1.0 / waitTime, Parameter.DELTA)
                        * Math.pow(saved, Parameter.MU);
                map.put(i,temp);
                sum += temp;
            }
        }
        //计算概率矩阵
        for (int i = 0; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                p[i] = map.get(i)/sum;
            } else {
                p[i] = 0.0;
            }
        }
        //轮盘赌选择下一个城市
        double R = Math.random();
        int selectedClient;
        if (R <= Parameter.R0) {
            selectedClient = stateTransferRule1(p);
        } else {
            selectedClient = stateTransferRule2(p, R);
        }
        //从允许选择的城市中去除selectClient
        visitedClient[selectedClient] = 1;
        allowedClient[selectedClient] = 0;
        //将当前城市加入solution中
        solution.addCus(selectedClient);
        allowedClientFilter();
        //如果当前已经走完一个循环,如果allowedClient只包含0点，则进入下一循环
        if ((OnlyContainsDeposit(allowedClient) && !visitFinish())) {
            solution.increaseLoop();
            ArrayUtil.initIntegerArray2Zero(allowedClient);
            //重新计算允许访问的客户
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient[i] = 1;
                }
            }
        }
    }

    /**
     * 筛选allowedClient
     */
    private void allowedClientFilter() {
        for (int i = 0; i < allowedClient.length; i++) {
            //solution检查各项约束条件是否允许
            if (allowedClient[i] == 1 && !solution.getCurrentTruck().checkNowCus(i)) {
                allowedClient[i] = 0;
            }
        }
    }


    /**
     * 状态转移规则2
     * 轮盘赌选择客户
     *
     * @param p
     * @param r
     * @return
     */
    private int stateTransferRule2(double[] p, double r) {
        int selectedClient = 0;
        double sum = 0.0;
        for (int i = 0; i < p.length; i++) {
            sum += p[i];
            if (sum >= r) {
                selectedClient = i;
                break;
            }
        }
        return selectedClient;
    }


    /**
     * 状态转移规则1
     * 寻找p中最大值对应的index
     *
     * @param p
     * @return
     */
    private int stateTransferRule1(double[] p) {
        double maxVal = Double.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < p.length; i++) {
            if (p[i] > maxVal) {
                maxVal = p[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * 允许访问的城市是否只包含0点
     *
     * @param allowedClient
     * @return
     */
    private static boolean OnlyContainsDeposit(int[] allowedClient) {
        if (allowedClient == null || allowedClient.length == 0)
            return false;
        for (int i = 1; i < allowedClient.length; i++) {
            if (allowedClient[i] == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 蚂蚁是否结束访问
     *
     * @return
     */
    public boolean visitFinish() {
        for (int i = 1; i < visitedClient.length; i++) {
            if (visitedClient[i] == 0)
                return false;
        }
        return true;
    }

    /**
     * 通过自身的解更新信息素
     */
    public void updatePheromone() {
        for (int k1 = 0; k1 < getSolution().size(); k1++) {
            getDelta()[0][getSolution().getTruckSols().get(k1).getCustomers().get(0).intValue()] = (Parameter.O / getLength());
            for (int k2 = 0, len2 = getSolution().getTruckSols().get(k1).size(); k2 + 1 < len2; k2++) {
                getDelta()[getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()][getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()] = (Parameter.O / getLength());
                getDelta()[getSolution().getTruckSols().get(k1).getCustomers().get(k2 + 1).intValue()][getSolution().getTruckSols().get(k1).getCustomers().get(k2).intValue()] = (Parameter.O / getLength());
            }
            getDelta()[getSolution().getTruckSols().get(k1).size() - 1][0] = (Parameter.O / getLength());
        }
    }

    /**
     * 获得路径长度
     *
     * @return
     */
    public double getLength() {
        return solution.calCost();
    }

    /**
     * 计算总花费
     *
     * @return
     */
    public double getLengthWithTWPunish() {
        return solution.calCostWithTWPunish();
    }


    /**
     * 计算惩罚代价
     *
     * @return
     */
    public double getTWPunishLength() {
        return solution.calTWPunishCost();
    }

    public int[] getVisitedClient() {
        return visitedClient;
    }


    public Integer getCapacity() {
        return VRP.capacity;
    }

    public double[][] getDelta() {
        return delta;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ant{" +
                "solution=" + solution +
                ", allowedClient=" + Arrays.toString(allowedClient) +
                ", visitedClient=" + Arrays.toString(visitedClient) +
                ", delta=" + Arrays.toString(delta) +
                '}';
    }
}