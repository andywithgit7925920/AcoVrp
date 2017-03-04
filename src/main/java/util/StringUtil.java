package util;

/**
 * Created by ab792 on 2017/1/5.
 */
public class StringUtil {

    private StringUtil() {
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals(""))
            return true;
        return false;
    }
}
