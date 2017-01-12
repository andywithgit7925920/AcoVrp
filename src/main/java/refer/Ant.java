package refer; /**
 * Created by ab792 on 2016/12/30.
 */

import java.util.Random;

/**
 *蚂蚁类
 * @author FashionXu
 */
public class Ant {
    /**
     * 蚂蚁获得的路径
     */
    public int[]tour;
    int[] unvisitedcity;    //unvisitedcity 取值是0或1，1表示没有访问过，0表示访问过
    /**
     * 蚂蚁获得的路径长度
     */
    public int tourlength;
    int citys;
    /**
     * 随机分配蚂蚁到某个城市中
     * 同时完成蚂蚁包含字段的初始化工作
     * @param citycount 总的城市数量
     */
    public void RandomSelectCity(int citycount){
        citys=citycount;
        unvisitedcity=new int[citycount];
        tour=new int[citycount+1];
        tourlength=0;
        for(int i=0;i<citycount;i++){
            tour[i]=-1;
            unvisitedcity[i]=1;
        }
        long r1 = System.currentTimeMillis();
        Random rnd=new Random(r1);
        int firstcity=rnd.nextInt(citycount);
        unvisitedcity[firstcity]=0;
        tour[0]=firstcity;
    }
    /**
     * 选择下一个城市
     * @param index 需要选择第index个城市了
     * @param tao   全局的信息素信息
     * @param distance  全局的距离矩阵信息
     */
    public void SelectNextCity(int index,double[][]tao,int[][]distance){
        double []p;
        p=new double[citys];
        double alpha=1.0;
        double beta=2.0;
        double sum=0;
        int currentcity=tour[index-1];
        //计算公式中的分母部分
        for(int i=0;i<citys;i++){
            if(unvisitedcity[i]==1)
                sum+=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta));
        }
        //计算每个城市被选中的概率
        for(int i=0;i<citys;i++){
            if(unvisitedcity[i]==0)
                p[i]=0.0;
            else{
                p[i]=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta))/sum;
            }
        }
        long r1 = System.currentTimeMillis();
        Random rnd=new Random(r1);
        double selectp=rnd.nextDouble();
        //轮盘赌选择一个城市；
        double sumselect=0;
        int selectcity=-1;
        for(int i=0;i<citys;i++){
            sumselect+=p[i];
            if(sumselect>=selectp){
                selectcity=i;
                break;
            }
        }
        if (selectcity==-1)
            System.out.println();
        tour[index]=selectcity;
        unvisitedcity[selectcity]=0;
    }
    /**
     * 计算蚂蚁获得的路径的长度
     * @param distance  全局的距离矩阵信息
     */
    public void CalTourLength(int [][]distance){
        tourlength=0;
        tour[citys]=tour[0];
        for(int i=0;i<citys;i++){
            tourlength+=distance[tour[i]][tour[i+1]];
        }
    }
}


