package util;

/**
 * Created by ab792 on 2017/1/3.
 */
public class ArrayUtil {
    public static boolean isNotEmpty(){
        return false;
    }
    public static void printArr(double[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (int i=0;i<array.length;i++){
                sb.append(array[i]).append("->");
            }
            String str = sb.toString();
            System.out.println("\n"+str.substring(0,str.length()-2));
        }
    }
    public static void printArr(int[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (int i=0;i<array.length;i++){
                sb.append(array[i]).append("->");
            }
            String str = sb.toString();
            System.out.println("\n"+str.substring(0,str.length()-2));
        }
    }
}
