package Day04.Set;

/**
 * @InterfaceName Set
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 15:22
 * @Version 1.0
 */
public interface Set<E> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(E element);

    void add(E element);

    void remove(E element);

    void traversal(Visitor<E> visitor);

    public static abstract class Visitor<E> {
        boolean stop=false;

        public abstract boolean visit(E element);
    }
}
