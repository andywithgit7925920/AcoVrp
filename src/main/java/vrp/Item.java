package vrp;

import java.io.Serializable;

/**
 * Created by ab792 on 2017/1/20.
 * 顾客需求的货物
 */
public class Item implements Serializable{
    double height;  //高
    double width;   //长
    double length;  //宽

    public Item(double height, double width, double length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }
}
