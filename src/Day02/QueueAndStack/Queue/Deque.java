package Day02.QueueAndStack.Queue;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Deque
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/26 18:47
 * @Version 1.0
 */
public class Deque<E> implements DequeInterface<E> {
    //采用双向链表实现队列
    private List<E> list = new LinkedList<E>();

    @Override
    public int size() {
        return list.size();
    }

    /**
     * 从队尾入队
     *
     * @param element 入队的元素
     */
    @Override
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * 从队尾出队
     *
     * @return 出队的元素
     */
    @Override
    public E deQueueRear() {
        return list.remove(size() - 1);
    }

    /**
     * 从对头入队
     *
     * @param element 入队的元素
     */
    @Override
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    /**
     * @return
     */
    @Override
    public E deQueueFront() {
        return list.remove(0);
    }

    /**
     * 返回头部元素
     *
     * @return
     */
    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }


    @Override
    public E rear() {
        return list.get(size() - 1);
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.enQueueFront(22);
        deque.enQueueFront(11);
        deque.enQueueRear(33);
        deque.enQueueRear(44);

        /*尾部44 33 22 11   头部*/
        /* java.util.Deque*/
        while (!deque.isEmpty()) {
            System.out.println(deque.deQueueFront());
        }
    }
}
