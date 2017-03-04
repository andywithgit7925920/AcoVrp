package util;

/**
 * Created by ab792 on 2017/1/3.
 */
public class ArrayUtil {
    private ArrayUtil() {
    }

    /**
     * 将Integer数组的初始值置为0
     * @param arr
     */
    public static void initIntegerArray2Zero(int[] arr){
        if (arr != null && arr.length > 0) {
            for (int i = 0, len = arr.length; i < len; i++) {
                arr[i] = 0;
            }
        }
    }

    /**
     * 将Integer数组的初始值置为1
     * @param arr
     */
    public static void initIntegerArray2One(int[] arr) {
        if (arr != null && arr.length > 0) {
            for (int i = 1, len = arr.length; i < len; i++) {
                arr[i] = 1;
            }
        }
    }
    public static boolean isNotEmpty() {
        return false;
    }

    public static void printArr(double[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]).append("->");
            }
            String str = sb.toString();
            System.out.println("\n" + str.substring(0, str.length() - 2));
        }
    }

    public static void printArr(Character[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]).append("->");
            }
            String str = sb.toString();
            System.out.println("\n" + str.substring(0, str.length() - 2));
        }
    }

    public static void printArr(int[] array) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]).append("->");
            }
            String str = sb.toString();
            System.out.println("\n" + str.substring(0, str.length() - 2));
        }
    }
}
