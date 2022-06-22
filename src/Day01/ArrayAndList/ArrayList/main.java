package Day01.ArrayAndList.ArrayList;

/**
 * @ClassName Demo01ArrayList
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 11:06
 * @Version 1.0
 */

public class main {
    public static void main(String[] args) {
        //引用数据类型存放地址值，基本数据类型存在内容
        ArrayList<Integer> array = new ArrayList<>();
//        array.add(10);
//        array.add(20);
        for (int i = 0; i < 50; i++) {
            array.add(i);
        }
        for (int i = 0; i < 50; i++) {
            array.remove(0);
        }
        array.add(null);
        array.add(null);
        System.out.println(array.indexOf(null));//2
//        array.clear();
//        System.gc();//JVM回收内存是看心情，但是如果你想让它立刻回收，就调用这行代码 gc=garbage collector垃圾回收
        //array.add(array.size() - 1, 25);
        System.out.println(array);

        System.out.println("==========");
        System.out.println(array.remove(0));
        System.out.println(array.remove(0));
        System.out.println(array);
//        array.get(-1);
    }
}
