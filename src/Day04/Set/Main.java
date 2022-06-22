package Day04.Set;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 15:26
 * @Version 1.0
 */
public class Main {
    static void test1() {
        long t1 = System.currentTimeMillis();
        Set<Integer> listSet = new ListSet<>();
        for (int i = 0; i < 100_0000; i++) {
            listSet.add(i);
        }
        listSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                // System.out.println(element);
                return false;
            }
        });
        long t2 = System.currentTimeMillis();
        System.out.println("所消耗的时间为:" + (t2 - t1) + "ms");
    }

    static void test2() {
        long t1 = System.currentTimeMillis();
        Set<Integer> set = new TreeSet<>();
        for (int i = 0; i < 100_0000; i++) {
            set.add(i);
        }
       /* set.add(10);
        set.add(20);
        set.add(15);
        set.add(10);*/
        //应该是从小到大排序
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                //System.out.println(element);
                return false;
            }
        });
        long t2 = System.currentTimeMillis();
        System.out.println("所消耗的时间为:" + (t2 - t1) + "ms");
    }

    static void test3() {
        long t1 = System.currentTimeMillis();
        TreeSetByMap<Integer> set = new TreeSetByMap<>();
        for (int i = 0; i < 100_0000; i++) {
            set.add(i);
        }
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                // System.out.println(element);
                return false;
            }
        });
        long t2 = System.currentTimeMillis();
        System.out.println("所消耗的时间为:" + (t2 - t1) + "ms");
    }

    public static void main(String[] args) {

//        test1();
       // test2();
        test3();
    }

}
