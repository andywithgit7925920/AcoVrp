package util;

import vrp.Truck;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by ab792 on 2016/12/30.
 * 需要从文件读入的信息
 */
public class DataUtil {
    /******待读取信息******/
    public static Integer clientNum;
    public static Integer capacity;
    public static double[][] distance;
    public static int[] clientDemandArr;
    /******待读取信息******/
    public static void importDataFromAVRP(String filePath) throws IOException {
        Double[] x_Axis = null;
        Double[] y_Axis = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line;
        String strBuff;
        boolean flag4NodeCoordSection = false;
        boolean flag4DemandSection = false;
        while (!"EOF".equals((line = reader.readLine()).trim())) {
            if (line.startsWith("DIMENSION")) {
                clientNum = Integer.valueOf(line.substring(12));
                clientDemandArr = new int[clientNum];
                x_Axis = new Double[clientNum];
                y_Axis = new Double[clientNum];
            }
            if (line.startsWith("CAPACITY")) {
                capacity = Integer.valueOf(line.substring(11));
                //将值直接赋给truck
                Truck.capacity = capacity;
            }
            if (line.startsWith("NODE_COORD_SECTION") || line.startsWith("DEMAND_SECTION")) {
                flag4NodeCoordSection = !flag4NodeCoordSection;
            }
            if (line.startsWith("DEMAND_SECTION") || line.startsWith("DEPOT_SECTION")) {
                flag4DemandSection = !flag4DemandSection;
            }
            if (flag4NodeCoordSection) {
                for (int i = 0; i < clientNum; i++) {
                    strBuff = reader.readLine();
                    String[] strArr = strBuff.split(" ");
                    x_Axis[i] = Double.valueOf(strArr[2]);
                    y_Axis[i] = Double.valueOf(strArr[3]);
                }
            }
            if (flag4DemandSection) {
                for (int i = 0; i < clientNum; i++) {
                    strBuff = reader.readLine();
                    String[] strArr = strBuff.split(" ");
                    clientDemandArr[i] = Integer.valueOf(strArr[1]);
                }
            }
        }
        //计算距离矩阵
        distance = new double[clientNum][clientNum];
        for (int i = 0; i < clientNum; i++) {
            distance[i][i] = 0.0;
            for (int j = i + 1; j < clientNum; j++) {
                Double len = Math.sqrt((x_Axis[i] - x_Axis[j]) * (x_Axis[i] - x_Axis[j]) + (y_Axis[i] - y_Axis[j]) * (y_Axis[i] - y_Axis[j]));
                distance[i][j] = len;
                distance[j][i] = distance[i][j];
            }
        }
        //ArrayUtil.printArr(x_Axis);
        //ArrayUtil.printArr(y_Axis);
        ArrayUtil.printArr(clientDemandArr);
        MatrixUtil.printMatrix(distance);
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
