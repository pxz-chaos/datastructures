package Day02.QueueAndStack.Stack;

/**
 * @InterfaceName StackInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/23 17:08
 * @Version 1.0
 */
public interface StackInterface<E> {
    int size();

    boolean isEmpty();

    void push(E element);

    E pop();

    E top();

}
