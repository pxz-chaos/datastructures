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

    //����˫������ʵ�ֶ���
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
     * @param element ���Ԫ��
     */
    @Override
    public void enQueue(E element) {
        list.add(element);//��ӵ�β��
    }

    /**
     * @return ����
     */
    @Override
    public E deQueue() {
        return list.remove(0);
    }

    /**
     * @return ����ͷԪ��
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
