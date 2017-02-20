package vrp;

/**
 * Created by ab792 on 2017/2/18.
 */
public class Parameter {
    public static final String filePath = "benchmark\\A-VRP\\A-n33-k5.vrp";
    public static final String filePathSolomon = "benchmark\\solomon\\C101.vrp";
    /****蚁群算法相关****/
    public static final Double ALPHA = 2.0;    //信息素影响因子
    public static final Double BETA = 3.0;     //启发信息影响因子
    public static Double RHO = 0.85;   //信息素挥发率
    public static final Integer ANT_NUM = 500;    //蚂蚁数量
    public static final Integer ITER_NUM = 10;     //迭代数
    public static final Double PHEROMONE_INIT = 1.0;    //信息素的初始值
    public static Double PHEROMONE_MAX = 1.0 ;       //信息素最大值
    public static Double PHEROMONE_MIN =1E-50;    //信息素最小值
    public static final Double O = 100.0;     // Δ𝜏𝑖𝑗 = 𝑂/𝐿(𝑅∗), 𝑂为一常数,
    public static final Double C = 200.0;     // 𝜏max = 𝐶/(𝐿(𝑅∗)×𝑛×(1−𝜌)),
    /****蚁群算法相关****/


    /****禁忌搜索相关相关****/
    double capPenalty = 100.0;     //重量超出惩罚
    /****禁忌搜索相关相关****/
}
