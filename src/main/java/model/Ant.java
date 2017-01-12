package model;

import static util.ConstUtil.*;

import static util.DataUtil.*;

import java.util.*;

/**
 * Created by ab792 on 2016/12/30.
 */
public class Ant {
    private List<List<Integer>> tour; //访问的城市列表
    private Set<Integer> allowedClient;  //允许访问的城市
    private int[] visitedClient;    //取值0或1，1表示已经访问过，0表示未访问过
    private Integer capacity;   //蚂蚁的载重量
    private Double totalLen;
    private double[][] delta;   //信息素变化矩阵
    private Integer firstClient;    //起始客户
    private Integer currentClient;  //当前客户
    private Integer currentCicycle;    //当前属于第几个循环
    private Integer currentCapacity;    //当前载重量

    public Ant(Integer capacity) {
        this.capacity = capacity;
        tour = new ArrayList<List<Integer>>();
        List<Integer> cicycle = new ArrayList<Integer>();
        cicycle.add(0);
        tour.add(cicycle);
        allowedClient = new HashSet<Integer>();
        delta = new double[clientNum][clientNum];
        for (int i = 0; i < clientNum; i++) {
            allowedClient.add(i);
        }
        currentCicycle = 0;
        currentCapacity = capacity;
    }

    /**
     * 蚂蚁初始化
     *
     * @param visitedCity
     */
    public void init(int[] visitedCity) {
        this.visitedClient = visitedCity;
        //将蚂蚁分散在客户端之间
        Random random = new Random(System.currentTimeMillis());
        firstClient = random.nextInt(clientNum);
        while (firstClient != 0 && visitedCity[firstClient] == 1) {
            firstClient = random.nextInt(clientNum);
        }
        if (firstClient != 0) {
            tour.get(0).add(0);
            //刪除该元素
            allowedClient.remove(firstClient);
            //当前载重量更新
            currentCapacity -= clientDemandArr[firstClient];
        }
        tour.get(0).add(firstClient);
        visitedCity[firstClient] = 1;
        currentClient = firstClient;
        System.out.println("firstClient->"+firstClient);
    }

    /**
     * 选择下一个城市
     *
     * @param pheromone
     */
    public void selectNextClient(double[][] pheromone) {
        //根据载重量约束，计算下一步允许访问的城市
        for (Integer i : allowedClient) {
            if (currentCapacity < clientDemandArr[i]) {
                allowedClient.remove(i);
            }
        }
        //如果allowedClient只包含0点，则进入下一循环
        if (allowedClient.size()==1){
            ++currentCicycle;
            List<Integer> cicycle = new ArrayList<Integer>();
            cicycle.add(0);
            tour.add(cicycle);
            //重新计算允许访问的城市
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient.add(i);
                }
            }
        }
        //如果当前处在起始点，则下一步不能选择起始点
        if (currentClient == 0) {
            allowedClient.remove(0);
        }
        double[] p = new double[clientNum];
        double sum = 0.0;
        //计算分母部分
        for (Integer i : allowedClient) {
            sum += Math.pow(pheromone[currentClient][i.intValue()], ALPHA) * Math.pow(1.0 / distance[currentClient][i.intValue()], BETA);
        }
        //计算概率矩阵
        for (int i = 0; i < clientNum; i++) {
            if (allowedClient.contains(i)) {
                p[i] = Math.pow(pheromone[currentClient][i], ALPHA) * Math.pow(1.0 / distance[currentClient][i], BETA) / sum;
            } else {
                p[i] = 0.0;
            }
        }
        //轮盘赌选择下一个城市
        Random random = new Random(System.currentTimeMillis());
        double sleectP = random.nextDouble();
        int selectClient = 0;
        double sum1 = 0.f;
        for (int i = 0; i < clientNum; i++) {
            sum1 += p[i];
            if (sum1 >= sleectP) {
                selectClient = i;
                break;
            }
        }
        //从允许选择的城市中去除selectClient
        visitedClient[selectClient] = 1;
        allowedClient.remove(selectClient);
        //将当前城市加入tour中
        tour.get(currentCicycle).add(selectClient);
        //如果当前已经走完一个循环
        if (selectClient == 0) {
            ++currentCicycle;
            List<Integer> cicycle = new ArrayList<Integer>();
            cicycle.add(0);
            tour.add(cicycle);
            //重新计算允许访问的城市
            for (int i = 1; i < visitedClient.length; i++) {
                if (visitedClient[i] == 0) {
                    allowedClient.add(i);
                }
            }
        }
        allowedClient.add(0);
        //将当前城市改为选择的城市
        currentClient = selectClient;
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
     * 计算路径总长度
     *
     * @return
     */
    private double calculateTourLength() {
        double len = 0;
        if (tour != null && tour.size() > 0) {
            for (int i = 0; i < tour.size(); i++) {
                for (int j = 0; j + 1 < tour.get(i).size(); j++) {
                    len += distance[tour.get(i).get(j).intValue()][tour.get(i).get(j + 1).intValue()];
                }
            }
            return len;
        }
        return 0.0;
    }

    /**
     * get total length
     *
     * @return
     */
    public double getLength() {
        totalLen = calculateTourLength();
        return totalLen;
    }


    public List<List<Integer>> getTour() {
        return tour;
    }

    public int[] getVisitedClient() {
        return visitedClient;
    }

    public Integer getCapacity() {
        return capacity;
    }


    public double[][] getDelta() {
        return delta;
    }

    public Integer getFirstClient() {
        return firstClient;
    }

    public Integer getCurrentClient() {
        return currentClient;
    }
}
