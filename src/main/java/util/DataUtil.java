package util;

import vrp.Truck;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by ab792 on 2016/12/30.
 * 数据的一些常用方法
 */
public class DataUtil {
    private DataUtil() {
    }

    /**
     * double小于
     */
    public static boolean less(double a, double b) {
        return a < b && Math.abs(a - b) > 1e-6;
    }

    /**
     * Double 大于
     */
    public static boolean more(double a, double b) {
        return a > b && Math.abs(a - b) > 1e-6;
    }

    /**
     * Double 小于等于
     */
    public static boolean le(double a, double b) {
        return eq(a, b) || less(a, b);
    }

    /**
     * Double大于等于
     */
    public static boolean ge(double a, double b) {
        return eq(a, b) || more(a, b);
    }

    /**
     * Double等于
     */
    public static boolean eq(double a, double b) {
        return Math.abs(a - b) < 1e-6;
    }


}
