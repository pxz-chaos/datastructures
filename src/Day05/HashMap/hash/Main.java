package Day05.HashMap.hash;

import Day05.HashMap.hash.Model.Key;
import Day05.HashMap.map.Map;
import Day05.HashMap.hash.Model.SubKey1;
import Day05.HashMap.hash.Model.SubKey2;
import org.junit.Assert;
import org.junit.Test;

/*
哈希表是一个数组，是牺牲空间换时间的一种数据结构
当出现哈希冲突时：不同的key具有相同的hash值，比如重地，通话
hash表中的key可以不具备可比性
hashCode的调用的前提，需要使用hash表这种数据结构时，用于生成索引
equals的调用前提，出现hash冲突的时候，哈希冲突是不可避免的。

自定义对象要想作为hash表的key，必须实现hashCode和equals方法
 */
@SuppressWarnings({"unused"})
public class Main {

    static void test1() {
        String string = "jack";
        int hashCode = 0;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            hashCode = hashCode * 31 + c;
//            hashCode = (hashCode << 5) - hashCode + c;//等价于上面一行代码
        }
        //hashCode=((j*31+a)*31+c)*31+k,这样
        System.out.println(hashCode);//3254239
        System.out.println(string.hashCode());//3254239

    }

    static void test2() {
        int a = 100;
        float b = 10.2f;
        long c = 456L;
        double d = 13.54d;
        String e = "jack";
        System.out.println(Integer.hashCode(a));//100
        System.out.println(Float.hashCode(b));
        System.out.println(Long.hashCode(c));
        System.out.println(Double.hashCode(d));
        System.out.println(e.hashCode());
    }

    static void test3() {
        Person p1 = new Person(10, 12.5, "jack");
        Person p2 = new Person(10, 12.5, "jack");
        System.out.println(p1.hashCode());//-987151928
        System.out.println(p1.hashCode());//-987151928,不重写的话就是内存地址值
    }

    static void test4() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("jack", 19);
        map.put("rose", 10);
        map.put("michel", 100);//返回被覆盖前的value，也就是19


       /* System.out.println(map.containsKey("通话"));
        System.out.println(map.containsKey("重地"));
        System.out.println(map.get("重地"));*/
        System.out.println(map.size());
        System.out.println(map.remove("rose"));
        System.out.println(map.size());
       /* System.out.println("========");
        java.util.HashMap<Object, Object> map1 = new java.util.HashMap<>();
        map1.put("通话", 19);
        map1.put("rose", 10);
        Object o1 = map1.put("重地", 100);
        System.out.println(map1.containsKey("通话"));
        System.out.println(map1.containsKey("重地"));
        System.out.println(map1.get("重地"));*/

    }

    static void test5() {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "Jack");
        }
        map.put(0, "rose");
        map.traversal(new Map.Visitor<Object, Object>() {
            @Override
            public boolean visit(Object key, Object value) {
                System.out.println(key + "-" + value);

                return key.equals(5);
            }
        });
    }

    static void test6() {
        HashMap<Object, Integer> map = new HashMap<>();

        for (int i = 1; i < 20; i++) {
            Key key = new Key(i);
            map.put(key, i + 5);

        }

        System.out.println("=========");
        System.out.println(map.get(new Key(1)));
        System.out.println("---------------");
        map.print();

    }

    static void test7() {
        HashMap<Key, Integer> map = new HashMap<>();

        for (int i = 1; i < 20; i++) {
            map.put(new Key(i), i + 5);

        }
        map.put(new Key(10), 100);
        System.out.println(map.get(new Key(10)));//100
        map.print();

    }

    @Test
    public void test8() {
        HashMap<Key, Integer> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(new Key(i), i);
        }
        map.print();
        Assert.assertTrue(map.size() == 20);
        Assert.assertEquals((int) map.get(new Key(4)), 4);
        Assert.assertEquals((int) map.get(new Key(18)), 18);
        map.put(new Key(21), 21);//21不存在于在之前的红黑树，因此就会进行扫描，这里会出现代码的重复使用
    }

    @Test
    public void test9() {
        HashMap<Object, Integer> map = new HashMap<>();
        SubKey1 key1 = new SubKey1(1);
        SubKey2 key2 = new SubKey2(1);
        map.put(key1,1);
        map.put(key2,2);

       Assert.assertTrue(key1.equals(key2));//true
        Assert.assertTrue(map.size()==1);

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
        //  test6();
        test7();

    }
}


