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
    //����˫������ʵ�ֶ���
    private List<E> list = new LinkedList<E>();

    @Override
    public int size() {
        return list.size();
    }

    /**
     * �Ӷ�β���
     *
     * @param element ��ӵ�Ԫ��
     */
    @Override
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * �Ӷ�β����
     *
     * @return ���ӵ�Ԫ��
     */
    @Override
    public E deQueueRear() {
        return list.remove(size() - 1);
    }

    /**
     * �Ӷ�ͷ���
     *
     * @param element ��ӵ�Ԫ��
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
     * ����ͷ��Ԫ��
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

        /*β��44 33 22 11   ͷ��*/
        /* java.util.Deque*/
        while (!deque.isEmpty()) {
            System.out.println(deque.deQueueFront());
        }
    }
}
