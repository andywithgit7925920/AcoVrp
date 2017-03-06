package updatestrategy;

import java.io.Serializable;
import vrp.Solution;
import parameter.Parameter;

/**
 * Created by ab792 on 2017/2/7.
 * 更新策略2
 * 则将𝑅中所有边上的信息素进行少量蒸发
 */
public class UpdateStrategy4Case2 extends BaseUpdateStrategy implements Serializable {
    private static final long serialVersionUID = 508974226241482960L;
    public double P = 1 - Parameter.RHO;

    public void updatePheBySolution(double[][] pheromone, Solution solution) {
        if (pheromone != null && solution != null) {
            for (int k1 = 0; k1 < solution.size(); k1++) {
                pheromone[0][solution.getTruckSols().get(k1).getCustomers().get(0)] *= P;
                for (int k2 = 0, len2 = solution.getTruckSols().get(k1).size(); k2 + 1 < len2; k2++) {
                    pheromone[solution.getTruckSols().get(k1).getCustomers().get(k2)][solution.getTruckSols().get(k1).getCustomers().get(k2 + 1)] *= P;
                    pheromone[solution.getTruckSols().get(k1).getCustomers().get(k2 + 1)][solution.getTruckSols().get(k1).getCustomers().get(k2)] *= P;
                }
                pheromone[solution.getTruckSols().get(k1).size() - 1][0] *= P;
            }
        }
        checkPheromoneLimit(pheromone);
    }

}
