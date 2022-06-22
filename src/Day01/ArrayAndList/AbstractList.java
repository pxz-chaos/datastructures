package Day01.ArrayAndList;

/**
 * @ClassName AbstractList
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 21:44
 * @Version 1.0
 */
public abstract class AbstractList<E> implements List<E> {
    //protected
    protected static final int ELEMENT_NOT_FOUND = -1;//
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    /**
     *
     * @param element
     * @return
     */
    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    //??????index???к????У??
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ":Size:" + size + ",indexOutOfBounds");
        }

    }

    protected  /*static ????д????????????????????????????size????????*/void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + ":Size:" + size + ",indexOutOfBounds");
        }
    }
}
