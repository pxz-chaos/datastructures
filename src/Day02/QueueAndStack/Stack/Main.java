package Day02.QueueAndStack.Stack;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/23 10:17
 * @Version 1.0
 */
public class Main {
    //≤‚ ‘“ª∞—

    static void testList(Stack<Integer> stack) {
        //‘ˆ
        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);

        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }


       /* list.add(0, 55);//[55,11,22,33,44]
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
*/

    }

    public static void main(String[] args) {
        testList(new Stack<Integer>());

    }
}
