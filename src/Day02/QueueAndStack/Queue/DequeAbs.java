package Day02.QueueAndStack.Queue;

/**
 * @ClassName QueueAbs
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/28 13:51
 * @Version 1.0
 */
public abstract class DequeAbs<E> implements QueueInterface<E> {

    public abstract int size();

    public abstract void enQueueRear(E element);

    public abstract E deQueueRear();

    public abstract void enQueueFront(E element);

    public abstract E deQueueFront();

    public abstract boolean isEmpty();

    public abstract E front();

    public abstract E rear();

    public abstract void clear();
}
