package Day05.HashMap.map;


import Day05.HashMap.hash.LinkedHashMap;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/19 21:57
 * @Version 1.0
 */
public class Main {
    static void test1() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(10, 112);
        map.put(10, 113);
        map.put(12, 114);
        map.put(18, 116);
        map.put(18, 117);
        map.put(19, 118);
        map.put(21, 119);
        Integer put = map.put(22, 118);
        System.out.println(put);
        map.traversal(new Map.Visitor<Integer, Integer>() {
            @Override
            public boolean visit(Integer key, Integer value) {
                System.out.println("key-value:" + key + "-" + value);
                return false;
            }
        });

    }

    static void test2() {
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, "rose");
        }
        for (int i = 50; i < 100; i++) {
            map.put(i, "jack");
        }
        map.traversal(new Map.Visitor<Integer, String>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println(key + value);
                return false;
            }
        });
    }

    static void test3() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Integer[] data = {40, 46, 94, 77, 76, 38, 24, 15, 82, 89, 97, 56};
        for (int i = 0; i < data.length; i++) {
            map.put(data[i], "rose");
        }
        map.traversal(new Map.Visitor<Integer, String>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println(key + value);
                return false;
            }
        });
    }

    static void test4() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("jack", 12);
        map.put("rose", 12);
        map.put("jim", 19);
        map.put("jake", 12);
map.traversal(new Map.Visitor<String, Integer>() {
    @Override
    public boolean visit(String key, Integer value) {
        System.out.println(key+"-"+value);
        return false;
    }
});

    }

    public static void main(String[] args) {
        // test1();

//        test3();
        test4();
    }
}
