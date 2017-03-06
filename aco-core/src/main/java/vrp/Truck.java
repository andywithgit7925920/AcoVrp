package vrp;

import java.io.Serializable;
import util.DataUtil;
import parameter.Parameter;
import java.util.LinkedList;

import static vrp.VRP.*;


/**
 * Created by ab792 on 2017/1/18.
 * 卡车类，代表了一条路径
 */
public class Truck implements Serializable{
    private static final long serialVersionUID = -1755642829659075970L;
    private int id;
    //private double capacity;    //载重量
    private double nowCapacity; //当前载重量
    private LinkedList<Integer> customers = new LinkedList<Integer>();   //该路径的所有客户
    private int currentCus; ////当前客户
    private int cusNum; //客户数量
    private int firstCus;   //第一个客户
    private int lastCus;    //最后一个客户
    private double penalty;     //惩罚
    public static double serviceTime;     //服务时间
    private boolean isOverLoad;  //是否超载
    private boolean isOverTime; //是否超出时间窗约束
    private double nowServiceTime;  //当前服务时间
    private double oriCost; //没有计算惩罚前
    private double totalCost;   //总的花费
    /**************暂未使用****************/
    private double height;  //高
    private double width;   //长
    private double length;  //宽
    private double maxDistance;     //限制的最长距离
    public static double capacity;

    /**************暂未使用****************/


    public Truck(int id) {
        this.id = id;
        nowCapacity = 0;
        currentCus = 0;
        nowServiceTime = 0;
    }

    /**
     * 是否超载
     *
     * @return
     */
    public boolean isOverLoad() {
        refreshNowCap();
        return DataUtil.more(nowCapacity, capacity);
    }

    /**
     * 软时间窗约束
     * 是否超出时间窗
     * 1.单个客户是否超出
     * 2.总体是否超出
     *
     * @return
     */
    public boolean isOverTimeForSoft() {
        double nowTime = 0.0;
        if (customers.size() > 0) {
            nowTime += distance[0][getFirstCus()];
            for (int i = 0; i < customers.size() - 1; i++) {
                int curCustomer = customers.get(i);
                if (nowTime > VRP.time[curCustomer][1]) {
                    return true;
                }
                //如果当前时间没达到ET，则需要等到ET才可以开始服务
                nowTime = (nowTime < VRP.time[curCustomer][0]) ? VRP.time[curCustomer][0] : nowTime;
                nowTime += VRP.serviceTime[curCustomer];
                int nextCustomer = customers.get(i + 1);
                nowTime += distance[curCustomer][nextCustomer];
            }
            if (nowTime > VRP.time[getLastCus()][1]) {
                return true;
            }
            //如果当前时间没达到ET，则需要等到ET才可以开始服务
            nowTime = (nowTime < VRP.time[getLastCus()][0]) ? VRP.time[getLastCus()][0] : nowTime;
            nowTime += VRP.serviceTime[getLastCus()];
            nowTime += distance[getLastCus()][0];
            if (nowTime > VRP.time[0][1]) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 硬时间窗
     *
     * @return
     */
    public boolean isOverTimeForHard() {
        double nowTime = 0.0;
        if (customers.size() > 0) {
            nowTime += distance[0][getFirstCus()];
            for (int i = 0; i < customers.size() - 1; i++) {
                int curCustomer = customers.get(i);
                if (nowTime > VRP.time[curCustomer][1] || nowTime < VRP.time[curCustomer][0]) {
                    return true;
                }
                //如果当前时间没达到ET，则需要等到ET才可以开始服务
                nowTime = (nowTime < VRP.time[curCustomer][0]) ? VRP.time[curCustomer][0] : nowTime;
                nowTime += VRP.serviceTime[curCustomer];
                int nextCustomer = customers.get(i + 1);
                nowTime += distance[curCustomer][nextCustomer];
            }
            if (nowTime > VRP.time[getLastCus()][1] || nowTime < VRP.time[getLastCus()][0]) {
                return true;
            }
            //如果当前时间没达到ET，则需要等到ET才可以开始服务
            nowTime = (nowTime < VRP.time[getLastCus()][0]) ? VRP.time[getLastCus()][0] : nowTime;
            nowTime += VRP.serviceTime[getLastCus()];
            nowTime += distance[getLastCus()][0];
            if (nowTime > VRP.time[0][1]) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 硬时间窗与软时间窗之间
     * 对于后一半约束，前一半不约束
     *
     * @return
     */
    public boolean isOverTime() {
        double nowTime = 0.0;
        if (customers.size() > 0) {
            nowTime += distance[0][getFirstCus()];
            for (int i = 0; i < customers.size() - 1; i++) {
                int curCustomer = customers.get(i);
                if (nowTime > VRP.time[curCustomer][1]) {
                    return true;
                }
                //如果当前时间没达到ET，则需要等到ET才可以开始服务
                nowTime = (nowTime < VRP.time[curCustomer][0]) ? VRP.time[curCustomer][0] : nowTime;
                nowTime += VRP.serviceTime[curCustomer];
                int nextCustomer = customers.get(i + 1);
                nowTime += distance[curCustomer][nextCustomer];
            }
            if (nowTime > VRP.time[getLastCus()][1]) {
                return true;
            }
            //如果当前时间没达到ET，则需要等到ET才可以开始服务
            nowTime = (nowTime < VRP.time[getLastCus()][0]) ? VRP.time[getLastCus()][0] : nowTime;
            nowTime += VRP.serviceTime[getLastCus()];
            nowTime += distance[getLastCus()][0];
            if (nowTime > VRP.time[0][1]) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 硬时间窗
     * 是否是一条好的路径
     *
     * @return
     */
    public boolean isGoodTruckForHard() {
        return !isOverTimeForHard() && !isOverLoad();
    }

    /**
     * 软时间窗
     *
     * @return
     */
    public boolean isGoodTruckForSoft() {
        return !isOverLoad();
    }

    /**
     * 介于软硬之间
     * @return
     */
    public boolean isGoodTruck() {
        return !isOverTime() && !isOverLoad();
    }

    /**
     * 卡车是否出发
     *
     * @return
     */
    public boolean isEmpty() {
        return customers.isEmpty();
    }

    /**
     * 在当前路径中添加客户
     *
     * @param cus
     */
    public void addCus(int cus) {
        customers.add(cus);
        currentCus = cus;   //更新当前城市
        //更新当前载重量
        adddNowCapacity(clientDemandArr[cus]);
    }

    /**
     * 删除路径中最后一个客户
     */
    public void removeLastCus() {
        if (customers.size() > 0) {
            removeNowCapacity(clientDemandArr[customers.getLast()]);
            customers.removeLast();
        }

        currentCus = (customers.size() > 0) ? customers.getLast() : 0;

    }

    /**
     * 增加当前载重量
     *
     * @param demand
     * @return
     */
    public void adddNowCapacity(double demand) {
        nowCapacity += demand;
    }

    /**
     * 减少当前载重量
     *
     * @param demand
     */
    public void removeNowCapacity(double demand) {
        nowCapacity -= demand;
    }

    /**
     * 硬时间窗
     * 判断是否能够加入新的客户
     * 可以：true 不可以：false
     *
     * @param nowCus
     * @return
     */
    public boolean checkNowCusForHard(int nowCus) {
        //System.out.println("====Truck.checkNowCusForHard begin====");
        refreshNowCap();
        boolean flag4Capacity = capacity >= nowCapacity + clientDemandArr[nowCus];
        addCus(nowCus);
        boolean flag4Time = !isOverTimeForHard();
        if (flag4Capacity && flag4Time) {
            removeLastCus();
            //System.out.println("====Truck.checkNowCusForHard end====");
            return true;
        } else {
            removeLastCus();
            //System.out.println("====Truck.checkNowCusForHard end====");
            return false;
        }
    }

    /**
     * 软时间窗
     * 判断是否能够加入新的客户
     * 可以：true 不可以：false
     *
     * @param nowCus
     * @return
     */
    public boolean checkNowCusForSoft(int nowCus) {
        refreshNowCap();
        return capacity >= nowCapacity + clientDemandArr[nowCus];
    }

    /**
     * @param nowCus
     * @return
     */
    public boolean checkNowCus(int nowCus) {
        refreshNowCap();
        boolean flag4Capacity = capacity >= nowCapacity + clientDemandArr[nowCus];
        addCus(nowCus);
        boolean flag4Time = !isOverTime();
        if (flag4Capacity && flag4Time) {
            removeLastCus();
            return true;
        } else {
            removeLastCus();
            return false;
        }
    }

    /**
     * 计算一辆车的路径长度
     *
     * @return
     */
    public double calCost() {
        double len = 0.0;
        if (customers.size() > 0) {
            len += distance[0][customers.getFirst()];
            for (int i = 0; i + 1 < customers.size(); i++) {
                len += distance[customers.get(i).intValue()][customers.get(i + 1).intValue()];
            }
            len += distance[customers.getLast().intValue()][0];
        }
        return len;
    }

    /**
     * 计算带有惩罚值的花费
     *
     * @return
     */
    public double calTWPunishCost() {
        double punishCost = 0.0;
        double nowTime = 0.0;
        if (customers.size() > 0) {
            nowTime += distance[0][getFirstCus()];
            for (int i = 0; i < customers.size() - 1; i++) {
                int curCustomer = customers.get(i);
                if (nowTime > VRP.time[curCustomer][1]) {
                    punishCost += Parameter.PUNISH_RIGHT * (nowTime - VRP.time[curCustomer][1]);
                } else if (nowTime < VRP.time[curCustomer][0]) {
                    punishCost += Parameter.PUNISH_LEFT * (VRP.time[curCustomer][0] - nowTime);
                }
                //如果当前时间没达到ET，则需要等到ET才可以开始服务
                nowTime = (nowTime < VRP.time[curCustomer][0]) ? VRP.time[curCustomer][0] : nowTime;
                nowTime += VRP.serviceTime[curCustomer];
                int nextCustomer = customers.get(i + 1);
                nowTime += distance[curCustomer][nextCustomer];
            }
            if (nowTime > VRP.time[getLastCus()][1]) {
                punishCost += Parameter.PUNISH_RIGHT * (nowTime - VRP.time[getLastCus()][1]);
            } else if (nowTime < VRP.time[getLastCus()][0]) {
                punishCost += Parameter.PUNISH_LEFT * (VRP.time[getLastCus()][0] - nowTime);
            }
            //如果当前时间没达到ET，则需要等到ET才可以开始服务
            nowTime = (nowTime < VRP.time[getLastCus()][0]) ? VRP.time[getLastCus()][0] : nowTime;
            nowTime += VRP.serviceTime[getLastCus()];
            nowTime += distance[getLastCus()][0];
            if (nowTime > VRP.time[0][1]) {
                punishCost += Parameter.PUNISH_RIGHT * (nowTime - VRP.time[0][1]);
            }
        }
        return punishCost;

    }

    /**
     * 计算总的花费
     *
     * @return
     */
    public double calCostWithTWPunish() {
        return calCost() + calTWPunishCost();
    }

    /**
     * 返回路径中客户的数量
     *
     * @return
     */
    public int size() {
        return customers.size();
    }

    @Override
    public String toString() {
        isOverLoad = isOverLoad();
        isOverTime = isOverTimeForHard();
        cusNum = customers.size();
        penalty = calCostWithTWPunish();
        oriCost = calCost();
        totalCost = penalty + oriCost;
        return "Truck{" +
                "id=" + id +
                ", oriCost=" + oriCost +
                ", totalCost=" + totalCost +
                ", capacity=" + capacity +
                ", nowCapacity=" + nowCapacity +
                ", customers=" + customers +
                ", cusNum=" + cusNum +
                ", penalty=" + penalty +
                ", isOverLoad=" + isOverLoad +
                ", isOverTime=" + isOverTime +
                '}';
    }


    /*@Override
    public String toString() {
        refreshNowCap();
        isOverLoad = isOverLoad();
        isOverTime = isOverTimeForHard();
        cusNum = customers.size();
        penalty = calCostWithTWPunish();
        return "Truck{" +
                "id=" + id +
                ", nowCapacity=" + nowCapacity +
                ", customers=" + customers +
                ", cusNum=" + cusNum +
                ", penalty=" + penalty +
                ", isOverLoad=" + isOverLoad +
                ", isOverTime=" + isOverTime +
                '}';
    }*/

    /*********getters and setters**********/
    public int getId() {
        return id;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getNowCapacity() {
        return nowCapacity;
    }

    public int getCurrentCus() {
        return currentCus;
    }

    public void setCurrentCus(int currentCus) {
        this.currentCus = currentCus;
    }

    public LinkedList<Integer> getCustomers() {
        return customers;
    }

    public int getCusNum() {
        cusNum = customers.size();
        return cusNum;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public void setCapacity(double capacity) {
        this.capacity = capacity;
    }*/

    public void setNowCapacity(double nowCapacity) {
        this.nowCapacity = nowCapacity;
    }

    public void setCustomers(LinkedList customers) {
        this.customers = customers;
    }

    public void setCusNum(int cusNum) {
        this.cusNum = cusNum;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getFirstCus() {
        firstCus = customers.getFirst();
        return firstCus;
    }

    public int getLastCus() {
        lastCus = customers.getLast();
        return lastCus;
    }

    public void setFirstCus(int firstCus) {
        this.firstCus = firstCus;
    }

    public void setLastCus(int lastCus) {
        this.lastCus = lastCus;
    }

    /**
     * 更新当前载重量
     */
    public void refreshNowCap() {
        nowCapacity = 0.0;
        if (!customers.isEmpty()) {
            for (Integer cus : customers) {
                adddNowCapacity(clientDemandArr[cus]);
            }
        }
    }

    /**
     * 计算当前服务时间
     *
     * @return
     */
    public double calNowServiceTime() {
        nowServiceTime = 0.0;
        if (customers.size() > 0) {
            nowServiceTime += distance[0][getFirstCus()];
            for (int i = 0; i < customers.size() - 1; i++) {
                int curCustomer = customers.get(i);
                //如果当前时间没达到ET，则需要等到ET才可以开始服务
                nowServiceTime = (nowServiceTime < VRP.time[curCustomer][0]) ? VRP.time[curCustomer][0] : nowServiceTime;
                nowServiceTime += VRP.serviceTime[curCustomer];
                int nextCustomer = customers.get(i + 1);
                nowServiceTime += distance[curCustomer][nextCustomer];
            }
            //如果当前时间没达到ET，则需要等到ET才可以开始服务
            nowServiceTime = (nowServiceTime < VRP.time[getLastCus()][0]) ? VRP.time[getLastCus()][0] : nowServiceTime;
            nowServiceTime += VRP.serviceTime[getLastCus()];
        }
        return nowServiceTime;
    }

/**
 *更新当前服务时间
 */
    /*private void refreshNowServiceTime() {
        nowServiceTime = 0.0;
        if (customers.size() > 0) {
            nowServiceTime += distance[0][customers.get(0)];
            for (int i = 0; i < customers.size() - 1; i++) {
                int cus = customers.get(i);
                int next = customers.get(i + 1);
                nowServiceTime += VRP.serviceTime[i];
                nowServiceTime += distance[cus][next];
            }
            nowServiceTime += VRP.serviceTime[customers.size() - 1];
        }
    }*/
    /*********getters and setters**********/

    /**
     * 复制
     *
     * @return
     */
    public Truck clone() {
        Truck cloneTruck = new Truck(this.getId());
        cloneTruck.setNowCapacity(this.nowCapacity);
        cloneTruck.setCustomers((LinkedList<Integer>) customers.clone());
        return cloneTruck;
    }
}
