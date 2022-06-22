package Day01.ArrayAndList;

/**
 * @InterfaceName DynamicArrInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 11:14
 * @Version 1.0
 */
/*
增删改查通用接口，只声明，不具体写方法
 */
public interface List<E> {
    /**
     * 清除所有元素
     */
    void clear();

    /**
     * @return 元素的数量
     */
    int size();

    /**
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含element元素
     *
     * @param element
     * @return 包含，返回true 否则，返回false
     */
    boolean contains(E element);

    /**
     * 添加元素到最后面
     *
     * @param element 要添加的元素
     */
    void add(E element);

    /**
     * 在具体的索引处添加元素
     * @param index
     * @param element
     */
    void add(int index, E element);


    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return
     */
    E set(int index, E element);

    /**
     * @param index
     * @return 返回index位置处的元素
     */
    E get(int index);

    /**
     * 删除index位置处的元素
     *
     * @param index
     * @return 返回被删除的元素
     */
    E remove(int index);

    /**
     * 查看元素的位置
     *
     * @param element
     * @return 返回被查看的元素位置
     */
    int indexOf(E element);

    String toString();
}
