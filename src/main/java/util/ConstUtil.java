package util;

/**
 * Created by ab792 on 2017/1/3.
 */
public class ConstUtil {
    public static final String filePath = "benchmark\\A-VRP\\A-n33-k5.vrp";
    /****蚁群算法相关****/
    public static final Double ALPHA = 1.0;    //
    public static final Double BETA = 3.0;     //
    public static final Double RHO = 0.5;   //信息素挥发率
    public static final Integer ANT_NUM = 15;    //蚂蚁数量
    public static final Integer ITER_NUM = 150;     //迭代数
    public static final Double PHEROMONE_MAX = 1.0;       //信息素最大值
    public static final Double PHEROMONE_MIN = 1.0E-90;    //信息素最小值
    public static final Double PHEROMONE_INIT = 0.1;    //信息素的初始值
    /****蚁群算法相关****/
    /****禁忌搜索相关相关****/
    double capPenalty = 100.0;     //重量超出惩罚
    /****禁忌搜索相关相关****/
}
