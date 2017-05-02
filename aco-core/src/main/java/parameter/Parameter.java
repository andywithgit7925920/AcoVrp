package parameter;

import enums.Com1EnumInterface;
import enums.Com2EnumInterface;
import enums.ParameterComEnum1;

import java.io.Serializable;

/**
 * Created by ab792 on 2017/2/18.
 */
public class Parameter implements Serializable{
    private static final long serialVersionUID = -5601241703802542551L;
    /****文件相关****/
    public String FILE_PATH = "benchmark\\A-VRP\\A-n33-k5.vrp";
    public String FILE_PATH_SOLOMON = "benchmark\\solomon\\C102.vrp";
    public String FILE_PATH_PAGE = "benchmark\\page5\\vrp10.vrp";
    /****文件相关****/
    /****蚁群算法相关****/
    /**影响因子**/
    //public double ALPHA = 2.0;    //信息素影响因子
    //public final double BETA = 3.0;     //启发信息影响因子
    //public final double GAMMA = 3.0;  //时间窗跨度影响因子
    //public final double DELTA = 2.0; //等待时间影响因子
    //public final double MU = 3.0;    //节约量影响因子
    /**影响因子**/
    //public static double RHO = 0.8;   //信息素挥发率
    public Integer ANT_NUM = 1;    //蚂蚁数量
    public Integer ITER_NUM = 2;     //迭代数
    //public static final double R0 = 0.5;    //用来控制转移规则的参数
    public double PHEROMONE_INIT = 1.0;    //信息素的初始值

    //public static final double O = 500.0;     // Δ𝜏𝑖𝑗 = 𝑂/𝐿(𝑅∗), 𝑂为一常数,
    public Integer BREAK_COUNTER = 10;  //N次以后若结果没有变化则迭代停止
    public Integer RHO_COUNTER = 5;    //RHO_COUNTER之内，最优解的变化值在3之内，则更新RHO
    public Double RHO_THRESHOLD = 3.0;
    public Double BREAK_THESHOLD = 0.001;
    /**最大最小蚁群算法**/
    public double C = 200.0;     // 𝜏max = 𝐶/(𝐿(𝑅∗)×𝑛×(1−𝜌)),
    //public static final double pheSpan = 300.0; //pheromoneMax / Parameter.pheSpan最大最小信息素之间的倍数
    /**最大最小蚁群算法**/
    /****蚁群算法相关****/
    /****vrp相关****/
    public double PUNISH_LEFT = 100;   //软时间窗惩罚因子1
    public double PUNISH_RIGHT = 100;   //软时间窗惩罚因子2
    /****vrp相关****/
    /****spark相关****/
    public String appName = "AcoVrp";
    public String master = "local[*]";
    /****spark相关****/

    /****实验参数设置组合1****/
    public double ALPHA = 3.0;    //信息素影响因子
    public double BETA = 4.0;     //启发信息影响因子
    public double GAMMA = 1.0;  //时间窗跨度影响因子
    public double DELTA = 3.0; //等待时间影响因子
    public static double MU = 2.0;    //节约量影响因子
    public double R0 = 0.2;    //用来控制转移规则的参数
    /****实验参数设置组合2****/
    public double PHEROMONE_MAX = 5.0;       //信息素最大值
    /****实验参数设置组合1****/
    public double O = 500.0;     // Δ𝜏𝑖𝑗 = 𝑂/𝐿(𝑅∗), 𝑂为一常数,
    public double pheSpan = 1000.0; //pheromoneMax / Parameter.pheSpan最大最小信息素之间的倍数
    public double PHEROMONE_MIN = PHEROMONE_MAX/pheSpan;    //信息素最小值
    //public static double PHEROMONE_MIN = 1E-5;    //信息素最小值
    public double RHO = 0.8;   //信息素挥发率
    /****实验参数设置组合2****/
    public void refreshByCom1(Com1EnumInterface com1EnumInterface){
        ALPHA = com1EnumInterface.getALPHA();
        BETA = com1EnumInterface.getBETA();
        GAMMA = com1EnumInterface.getGAMMA();
        DELTA = com1EnumInterface.getDELTA();
        MU = com1EnumInterface.getMU();
        R0 = com1EnumInterface.getR0();
    }
    public void refreshByCom2(Com2EnumInterface com2EnumInterface){
        pheSpan = com2EnumInterface.getPheSpan();
        PHEROMONE_MIN = PHEROMONE_MAX/pheSpan;
        RHO = com2EnumInterface.getRho();
        O = com2EnumInterface.getO();
    }
}
