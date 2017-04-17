package updatestrategy;

import acs.Ant;
import parameter.Parameter;
import util.VrpTransportTemp;
import util.DataUtil;
import vrp.Solution;
/**
 * Created by ab792 on 2017/2/7.
 */
public abstract class BaseUpdateStrategy {
    //public double P = Parameter.RHO;

    public abstract void updatePheBySolution(double[][] pheromone, Solution solution, Parameter parameter);


    /**
     * 第一种更新策略
     * 每次更新前全局信息素挥发
     *
     * @param pheromone
     * @param ant
     */
    public void  updateByAntRule1(double[][] pheromone, Ant ant, VrpTransportTemp vrpTransportTemp,Parameter parameter) {
        //System.out.println("ConstUtil.PHEROMONE_MAX--->"+ConstUtil.PHEROMONE_MAX);
        //System.out.println("ConstUtil.PHEROMONE_MIN--->"+ConstUtil.PHEROMONE_MIN );
         double P = parameter.RHO;
        //信息素挥发
        for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                pheromone[i][j] *= P;
            }
        }
        //System.out.println(ant);
        //信息素更新
        for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                pheromone[i][j] += ant.getDelta()[i][j];
            }
        }
        checkPheromoneLimit(pheromone);
    }

    /**
     * 第一种更新策略
     * 每次更新前全局信息素挥发
     *
     * @param pheromone
     * @param ants
     */
    public void updateByAntRule1(double[][] pheromone, Ant[] ants, VrpTransportTemp vrpTransportTemp,Parameter parameter) {
        //System.out.println("BaseUpdateStrategy.update");
        double P = parameter.RHO;
        //信息素挥发
        for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                pheromone[i][j] *= P;
            }
        }
        checkPheromoneLimit(pheromone);
        for (int i = 0; i < ants.length; i++) {
            //信息素更新
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                for (int k = 0; k < vrpTransportTemp.clientNum; k++) {
                    pheromone[j][k] += ants[i].getDelta()[j][k];
                }
            }
        }
    }

    /**
     * 按照策略一更新种群以及最优蚂蚁的信息素
     * @param pheromone
     * @param ants
     * @param bestAnt
     */
    public void updateByAntRule1(double[][] pheromone, Ant[] ants, Ant bestAnt, VrpTransportTemp vrpTransportTemp,Parameter parameter) {
        //System.out.println("BaseUpdateStrategy.update");
        double P = parameter.RHO;
        //信息素挥发
        for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                pheromone[i][j] *= P;
            }
        }
        checkPheromoneLimit(pheromone);
        for (int i = 0; i < ants.length; i++) {
            //信息素更新
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                for (int k = 0; k < vrpTransportTemp.clientNum; k++) {
                    pheromone[j][k] += ants[i].getDelta()[j][k];
                }
            }
        }
        //精英蚂蚁信息素更新
        for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
            for (int k = 0; k < vrpTransportTemp.clientNum; k++) {
                pheromone[j][k] += bestAnt.getDelta()[j][k];
            }
        }
        checkPheromoneLimit(pheromone);

    }

    /**
     * 第二种更新策略
     * 路线上的信息素不挥发
     *
     * @param pheromone
     * @param ant
     */
    public void updateByAntRule2(double[][] pheromone, Ant ant, VrpTransportTemp vrpTransportTemp,Parameter parameter) {
        double P = parameter.RHO;
        //信息素更新
        for (int i = 0; i < vrpTransportTemp.clientNum; i++) {
            for (int j = 0; j < vrpTransportTemp.clientNum; j++) {
                if (DataUtil.more(ant.getDelta()[i][j], 0.0)) {
                    pheromone[i][j] += ant.getDelta()[i][j];
                }else {
                    pheromone[i][j] *= P;
                }
            }
        }
    }

    public void checkPheromoneLimit(double[][] pheromone) {
        Parameter parameter = new Parameter();
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j < pheromone[i].length; j++) {
                pheromone[i][j] = (pheromone[i][j] < parameter.PHEROMONE_MIN) ? parameter.PHEROMONE_MIN : pheromone[i][j];
                pheromone[i][j] = (pheromone[i][j] > parameter.PHEROMONE_MAX) ? parameter.PHEROMONE_MAX : pheromone[i][j];
            }
        }
    }
}
