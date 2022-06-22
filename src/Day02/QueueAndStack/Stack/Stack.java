package Day02.QueueAndStack.Stack;

import Day01.ArrayAndList.SingleLinkedList.SingleLinkedList;

/**
 * @ClassName Stack
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/23 17:03
 * @Version 1.0
 */
public class Stack<E> implements StackInterface<E>{

    SingleLinkedList<E> list=new SingleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E element) {
        list.add(element);
    }

    @Override
    public E pop() {
        return list.remove(size()-1);
    }

    @Override
    public E top() {
        return list.get(size()-1);
    }
//java.util.Stack


}
