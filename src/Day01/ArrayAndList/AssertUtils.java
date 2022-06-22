package Day01.ArrayAndList;

/**
 * @ClassName AssertUtils
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/23 10:28
 * @Version 1.0
 */
/*
≤‚ ‘¿‡
 */
public class AssertUtils {
    public static void test(boolean value){
        try {
            if (!value)
            throw new Exception("≤‚ ‘Œ¥Õ®π˝");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
