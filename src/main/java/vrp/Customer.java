package vrp;

import java.io.Serializable;

/**
 * Created by ab792 on 2017/1/20.
 * 客户
 */
public class Customer implements Serializable{
    int id;
    double x;
    double y;
    double demand;  //需求的重量
    Item[] needsItems;

    /**
     * 计算两个客户之间的距离
     * @param that
     * @return
     */
    public double dis(Customer that){
        return Math.sqrt(Math.pow((this.x-that.x),2) + Math.pow((this.y-that.y),2));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", demand=" + demand +
                '}';
    }
}
