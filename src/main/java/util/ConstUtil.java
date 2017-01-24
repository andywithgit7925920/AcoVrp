package util;

/**
 * Created by ab792 on 2017/1/3.
 */
public class ConstUtil {
    public static final String filePath = "benchmark/A-VRP/A-n32-k5.vrp";
    /****蚁群算法相关****/
    public static final Double ALPHA = 0.5;    //
    public static final Double BETA = 0.5;     //
    public static final Double RHO = 0.5;   //信息素挥发率
    public static final Integer ANT_NUM = 5;    //蚂蚁数量
    public static final Integer ITER_NUM = 10000;
    /****蚁群算法相关****/
    /****禁忌搜索相关相关****/
    double capPenalty = 100.0;     //重量超出惩罚
    /****禁忌搜索相关相关****/
}
