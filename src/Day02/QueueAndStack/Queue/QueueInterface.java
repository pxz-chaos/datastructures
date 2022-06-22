package Day02.QueueAndStack.Queue;

/**
 * @InterfaceName QueueInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/26 9:55
 * @Version 1.0
 */
public interface QueueInterface<E> {
    int size();//元素的数量


    boolean isEmpty();//是否为空

    void enQueue(E element);//入队

    E deQueue();//出队

    E front();//获取队列的头元素



    void clear();//清空所有元素
}
