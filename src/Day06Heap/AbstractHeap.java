package Day06Heap;


import java.util.Comparator;

public abstract class AbstractHeap<E> implements Heap<E> {

    protected int size;

    protected Comparator<E> comparator;
    public AbstractHeap() {
        this(null);
    }

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    @Override
    public int size() {
        return size;
    }
    protected int compare(E e1, E e2) {

        return comparator != null ? comparator.compare(e1, e2) :
                ((Comparable<E>) e1).compareTo(e2);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
