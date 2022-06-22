package Day02.QueueAndStack.Queue;

import Day01.ArrayAndList.DoubleLinkedList.DoubleLinkedList;

/**
 * @ClassName Queue
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/26 9:54
 * @Version 1.0
 */

public class Queue<E> implements QueueInterface<E> {

    //采用双向链表实现队列
    private DoubleLinkedList<E> list = new DoubleLinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @param element 入队元素
     */
    @Override
    public void enQueue(E element) {
        list.add(element);//添加到尾部
    }

    /**
     * @return 出队
     */
    @Override
    public E deQueue() {
        return list.remove(0);
    }

    /**
     * @return 返回头元素
     */
    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public void clear() {
        list.clear();
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enQueue(10);
        queue.enQueue(20);
        queue.enQueue(30);
        queue.enQueue(40);
        queue.enQueue(50);

       /* java.util.Deque*/
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
