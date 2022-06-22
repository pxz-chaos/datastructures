package Day01.ArrayAndList.DoubleCircleLinkedList;

import Day01.ArrayAndList.AssertUtils;
import Day01.ArrayAndList.List;
import Day01.ArrayAndList.SingleCircleLinkedList.SingleCircleLinkedList;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/23 10:17
 * @Version 1.0
 */
public class Main {
    //≤‚ ‘“ª∞—

    static void testList(List<Integer> list) {
        //‘ˆ
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55);//[55,11,22,33,44]
        list.add(2, 66);//[55,11,66,22,33,44]
        list.add(list.size(),77);//[55,11,66,22,33,44,77]


        //…æ
        list.remove(0);//[11,66,22,33,44,77]
        list.remove(2);//[11,66,33,44,77]
        list.remove(list.size()-1);//[11,66,33,44]

        AssertUtils.test(list.indexOf(44)==3);
        AssertUtils.test(list.indexOf(22)==-1);
        AssertUtils.test(list.get(0)==11);
        AssertUtils.test(list.get(1)==66);
        AssertUtils.test(list.get(list.size()-1)==44);

        System.out.println(list);
    }

    public static void main(String[] args) {
        testList(new SingleCircleLinkedList<>());

    }
}
