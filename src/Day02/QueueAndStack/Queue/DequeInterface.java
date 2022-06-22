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

    void enQueueRear(E element);//从队尾入队

    E deQueueRear();//从队尾出队

    void enQueueFront(E element);//从队头入队

    E deQueueFront();//从队头出队

    E front();

    void clear();

    boolean isEmpty();

    E rear();
}
