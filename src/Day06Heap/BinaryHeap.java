package Day06Heap;

import Day06Heap.printer.BinaryTreeInfo;

import java.util.Comparator;

/*
二叉堆的逻辑结构就是一个完全二叉树，所以也叫完全二叉堆
鉴于完全二叉树堆顶一些特性，二叉堆的底层一般采用数组实现即可
 */


/**
 * 默认是一个最大堆
 *
 * @param <E> 泛型
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            //复制数组元素到堆中
            size = elements.length;
            int capacity = Math.max(DEFAULT_CAPACITY, elements.length);
            this.elements = (E[]) new Object[capacity];//申请一段存储空间
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator comparator) {
        this(null, comparator);
    }

    public BinaryHeap() {
        this(null, null);
    }


    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;//往最后面加一个

        shiftUp(size - 1);//让最后一个元素上滤


    }

    /**
     * 批量建堆
     * 有自上而下的上滤和自下而上的下滤
     * 自上而下的上滤：将堆中的每一个元素都上滤，元素的取法自上而下
     * 自下而上的下滤：将堆中的每一个元素都下滤，元素的取法从下往上取
     */
    public void heapify() {
        // 自上而下的上滤
//		for (int i = 1; i < size; i++) {
//			shiftUp(i);
//		}

        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            shiftDown(i);
        }
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        elements[0] = elements[size - 1];

        //将最后一个元素清空
        elements[size - 1] = null;
        size--;
        shiftDown(0);//进行下滤操作
        return null;
    }

    /**
     * 思路：先替换掉堆顶元素，然后再进行下滤操作
     *
     * @param element 新元素
     * @return 被替代的堆顶元素
     */
    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            //堆中没有元素，被添加的元素就是第一个元素
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            shiftDown(0);
        }

        return root;
    }

    /**
     * 让index位置处的元素上滤
     *
     * @param index 给定需要上滤的元素索引
     */
    private void shiftUp(int index) {
      /*  E element = elements[index];//获取自己索引处的内容

        while (index > 0) {
            //获取父节点的索引
            int parentIndex = (index - 1) >> 1;//(index-1)/2;
            E parent = elements[parentIndex];//获取父节点的内容

            if (compare(element, parent) <= 0) return;//父节点的内容大于等于新添加来的节点内容
            //能来到这里的就是e>p,就需要交换
            //交换parentIndex和index的内容
            E tmp = elements[index];
            elements[index] = elements[parentIndex];
            elements[parentIndex] = tmp;

            //重新赋值index
            index = parentIndex;
        }*/

        //优化
        E element = elements[index];//获取自己索引处的内容

        while (index > 0) {
            //获取父节点的索引
            int parentIndex = (index - 1) >> 1;//(index-1)/2;
            E parent = elements[parentIndex];//获取父节点的内容

            if (compare(element, parent) <= 0) break;//父节点的内容大于等于新添加来的节点内容
            elements[index] = parent;

            //重新赋值index
            index = parentIndex;
        }
        //退出循环以后
        elements[index] = element;
    }

    /**
     * 下滤操作,index 必须有子节点才进行下滤操作
     *
     * @param index 需要下滤节点的索引
     */
    private void shiftDown(int index) {
        E element = elements[index];

        //第一个叶子节点的索引==非叶子节点的数量
        //非叶子节点的数量为：n1+n2=floor(n/2)

        int half = size >> 1;
        while (index < half/*第一个叶子节点的索引*/) {// 必须保证index位置处的节点为非叶子节点
            //index的取值有两两种情况
            //1.只有左子节点
            //2.同时左右子树

            //默认为左子节点跟它进行比较
            int childIndex = index * 2 + 1;//左子节点索引

            E child = elements[childIndex];
            //右子节点索引
            int rightIndex = childIndex + 1;

            //选出左右子节点最大的那个
            //如何保证有右子树呢？答：rightIndex<size,代表索引是合理的

            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                childIndex = rightIndex;
                child = elements[rightIndex];
            }
            if (compare(element, child) > 0) break;
            //将子节点存放在到index位置
            elements[index] = child;//开始上滤
            //重新设置index
            index = childIndex;
        }
        elements[index] = element;
    }

    private void emptyCheck() {
        if (size == 0) throw new IndexOutOfBoundsException("Heap is empty!");
    }

    private void elementNotNullCheck(E element) {
        if (element == null) throw new IllegalArgumentException("element must not be null!");
    }

    /**
     * 保证有 capacity个容量
     *
     * @param capacity 容量
     *                 举个例子；如果我现在的size=5，我要添加一个数据，那么我的容量至少为6噻
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;//10个内存，不要与size混淆了，size是有效元素的个数
        if (oldCapacity >= capacity) return;//10个够用了，不需要扩容
        //新容量为就容量的1.5倍。官方推荐的扩容标准
        /*int newCapacity = (int) (oldCapacity * 1.5);*/
        //优化，采用位运算
        int newCapacity = oldCapacity + (oldCapacity >> 1);//oldCapacity >> 1==oldCapacity*0.5
        //创建一个新的数组
        E[] newElements = (E[]) new Object[newCapacity];
        //把原来数组中的数据拷贝过来
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

    }

    @Override
    public Object root() {
        //返回0索引，也就是根节点
        return 0;
    }

    @Override
    public Object left(Object node) {
        //返回左子树的索引即可
        Integer index = (Integer) node;
        index = (index * 2 + 1);
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        //返回右子树的索引即可
        Integer index = (Integer) node;
        index = (index * 2 + 2);
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}
