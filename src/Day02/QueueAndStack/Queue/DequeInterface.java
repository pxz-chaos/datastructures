package Day02.QueueAndStack.Queue;

/**
 * @InterfaceName DequeInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/28 14:16
 * @Version 1.0
 */
public interface DequeInterface<E> {
    int size();

    void enQueueRear(E element);//�Ӷ�β���

    E deQueueRear();//�Ӷ�β����

    void enQueueFront(E element);//�Ӷ�ͷ���

    E deQueueFront();//�Ӷ�ͷ����

    E front();

    void clear();

    boolean isEmpty();

    E rear();
}
