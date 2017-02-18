package localsearch;

import vrp.Solution;
import vrp.Truck;

/**
 * Created by ab792 on 2017/2/14.
 */
public interface BaseStretegy {
    /**
     * 路径的更新搜索
     * @param preSolution
     * @throws Exception
     */
    public void updateSolution(Solution preSolution) throws Exception;

    /**
     * 计算路径交换的代价
     * @param truck1
     * @param truck2
     * @param indexI
     * @param indexJ
     * @return
     * @throws Exception
     */
    public double calCost(Truck truck1, Truck truck2, int indexI, int indexJ) throws Exception;
}
