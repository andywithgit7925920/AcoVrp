package refer;

import java.io.*;

/**
 * Created by ab792 on 2016/12/30.
 */
public class ACO {
    //定义蚂蚁群
    Ant []ants;
    int antcount;//蚂蚁的数量
    int [][]distance;//表示城市间距离
    double [][]tao;//信息素矩阵
    int citycount;//城市数量
    int[]besttour;//求解的最佳路径
    int bestlength;//求的最优解的长度
    /** 初始化函数
     *@param filename tsp数据文件
     *@param antnum 系统用到蚂蚁的数量
     */
    public void init(String filename,int antnum) throws  IOException {
        antcount=antnum;
        ants=new Ant[antcount];
        //读取数据
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader tspdata = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        strbuff = tspdata.readLine();
        citycount = Integer.valueOf(strbuff);
        distance = new int[citycount][citycount];
        x = new int[citycount];
        y = new int[citycount];
        for (int citys = 0; citys < citycount; citys++) {
            strbuff = tspdata.readLine();
            String[] strcol = strbuff.split(" ");
            x[citys] = Integer.valueOf(strcol[1]);
            y[citys] = Integer.valueOf(strcol[2]);
        }
        //计算距离矩阵
        for (int city1 = 0; city1 < citycount - 1; city1++) {
            distance[city1][city1] = 0;
            for (int city2 = city1 + 1; city2 < citycount; city2++) {
                distance[city1][city2] = (int) (Math.sqrt((x[city1] - x[city2]) * (x[city1] - x[city2])
                        + (y[city1] - y[city2]) * (y[city1] - y[city2])) + 0.5);
                distance[city2][city1] = distance[city1][city2];
            }
        }
        distance[citycount - 1][citycount - 1] = 0;
        //初始化信息素矩阵
        tao=new double[citycount][citycount];
        for(int i=0;i<citycount;i++)
        {
            for(int j=0;j<citycount;j++){
                tao[i][j]=0.1;
            }
        }
        bestlength=Integer.MAX_VALUE;
        besttour=new int[citycount+1];
        //随机放置蚂蚁
        for(int i=0;i<antcount;i++){
            ants[i]=new Ant();
            ants[i].RandomSelectCity(citycount);
        }
    }
    /**
     * ACO的运行过程
     * @param maxgen ACO的最多循环次数
     *
     */
    public void run(int maxgen){
        for(int runtimes=0;runtimes<maxgen;runtimes++){
            //每一只蚂蚁移动的过程
            for(int i=0;i<antcount;i++){
                for(int j=1;j<citycount;j++){
                    ants[i].SelectNextCity(j,tao,distance);
                }
                //计算蚂蚁获得的路径长度
                ants[i].CalTourLength(distance);
                if(ants[i].tourlength<bestlength){
                    //保留最优路径
                    bestlength=ants[i].tourlength;
                    System.out.println("第"+runtimes+"代，发现新的解"+bestlength);
                    for(int j=0;j<citycount+1;j++)
                        besttour[j]=ants[i].tour[j];
                }
            }
            //更新信息素矩阵
            UpdateTao();
            //重新随机设置蚂蚁
            for(int i=0;i<antcount;i++){
                ants[i].RandomSelectCity(citycount);
            }
        }
    }
    /**
     * 更新信息素矩阵
     */
    private void UpdateTao(){
        double rou=0.5;
        //信息素挥发
        for(int i=0;i<citycount;i++)
            for(int j=0;j<citycount;j++)
                tao[i][j]=tao[i][j]*(1-rou);
        //信息素更新
        for(int i=0;i<antcount;i++){
            for(int j=0;j<citycount;j++){
                tao[ants[i].tour[j]][ants[i].tour[j+1]]+=1.0/ants[i].tourlength;
            }
        }
    }
    /**
     * 输出程序运行结果
     */
    public void ReportResult(){
        System.out.println("最优路径长度是"+bestlength);
    }
}
