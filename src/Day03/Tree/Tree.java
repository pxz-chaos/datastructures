package Day03.Tree;

/**
 * @InterfaceName BinaryTree
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/8 16:01
 * @Version 1.0
 */
public interface Tree<E> {
    int size();

    boolean isEmpty();

    void clear();

    void add(E element);

    void remove(E element);

    boolean contains(E element);


}
