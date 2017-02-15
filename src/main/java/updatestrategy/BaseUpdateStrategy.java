package updatestrategy;

import acs.Ant;
import vrp.Solution;
import util.ConstUtil;

import static util.DataUtil.clientNum;

/**
 * Created by ab792 on 2017/2/7.
 */
public abstract class BaseUpdateStrategy {
    public double P = ConstUtil.RHO;

    public abstract void update(double[][] pheromone, Solution solution);

    public void update(double[][] pheromone, Ant ant) {
        //信息素挥发
        for (int i = 0; i < clientNum; i++) {
            for (int j = 0; j < clientNum; j++) {
                pheromone[i][j] *= P;
            }
        }
        //System.out.println(ant);
        //信息素更新
        for (int i = 0; i < clientNum; i++) {
            for (int j = 0; j < clientNum; j++) {
                pheromone[i][j] += ant.getDelta()[i][j];
            }
        }
        checkPheromoneLimit(pheromone);
    }

    public void update(double[][] pheromone, Ant[] ants) {
        System.out.println("BaseUpdateStrategy.update");
        //信息素挥发
        for (int i = 0; i < clientNum; i++) {
            for (int j = 0; j < clientNum; j++) {
                pheromone[i][j] *= P;
            }
        }
        checkPheromoneLimit(pheromone);
        for (int i=0;i<ants.length;i++){
            //信息素更新
            for (int j = 0; j < clientNum; j++) {
                for (int k = 0; k < clientNum; k++) {
                    pheromone[j][k] += ants[i].getDelta()[j][k];
                }
            }
        }
    }

    public void checkPheromoneLimit(double[][] pheromone) {
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j < pheromone[i].length; j++) {
                pheromone[i][j] = (pheromone[i][j] < ConstUtil.PHEROMONE_MIN) ? ConstUtil.PHEROMONE_MIN : pheromone[i][j];
                pheromone[i][j] = (pheromone[i][j] > ConstUtil.PHEROMONE_MAX) ? ConstUtil.PHEROMONE_MAX : pheromone[i][j];
            }
        }
    }
}
