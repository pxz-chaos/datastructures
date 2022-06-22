package Day01.ArrayAndList.ArrayList;

import Day01.ArrayAndList.List;

/**
 * @ClassName DynamicArray
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 11:09
 * @Version 1.0
 */
/*
    自己写一个动态数组
    扩容倍数和缩容倍数不要相成乘1，否则就会引起复杂度震荡现象
 */
    @SuppressWarnings({"unused","unchecked","UnusedDeclaration"})
public class ArrayList<E> implements List<E> {
    //成员变量
    private int size;//元素的数量
    private E[] elements;//所有元素


    //去参构造器的默认数组容量设置为常量
    private static final int DEFAULT_CAPACITY = 10;

    private static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 有参构造器
     *
     * @param capacity 创建一个固定容量的数组
     *                 超过默认容量，我才自定义多少个，相当于一个分段函数
     */
    public ArrayList(int capacity) {
        capacity = (capacity > DEFAULT_CAPACITY) ? capacity : DEFAULT_CAPACITY;
        elements = (E[]) new Object[capacity];//泛型数组的写法
    }

    //无参构造器，默认容量为10个
    public ArrayList() {
        /*elements= (E[]) new Object[DEFAULT_CAPACITY];*/
        //优化一下，用无参的构造器调用有参的构造器
        this(DEFAULT_CAPACITY);//调用了DynamicArray(int capacity)有参构造器

    }

    @Override
    public void clear() {
        //非泛型的处理手段，就是不需要全部清除内存的内容，但是是泛型的话，就必须强制清除,数组仍然保留
        //如果我size等于0了，就算之前的数组里面还有数据，但是我都无法访问了
        // 虽然数据都存在，感觉有点浪费空间，但是我这个数组以后还要用呢，就不用再new了
        //如果你觉得还是满意，那就这样吧
        /*if (size < 100) {
            size = 0;
        } else {
            elements=null;
        }*/

        for (int i = 0; i < size; i++) {
            elements[i] = null;     //把对象给干掉，地址保留下来
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public void add(E element) {
        add(size,element);
    }

    /**
     * 找到要插入的位置，然后将后面的元素后移
     * 一定要倒着移动，先移动最后一个元素，再移动倒数第一个元素
     *
     * @param index   需要插入元素的位置
     * @param element 即将插入的元素
     */
    @Override
    public void add(int index, E element) {
        //需要对这个index进行合法性校验
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);//为了保证容量

        //先腾出要插入的数据位置，腾出位置，也就是将index后面的数据整体后移
        //		例如：11、22、33、44、55要在索引为为1的位置插入68
//		第一步后移index等于1的后面所有元素，变成11、22、22、33、44、55
//		将22替换成68，最终效果为11、68、22、33、44、55
        for (int i = size - 1; i >= index; i--) {
//            elements[i]=elements[i-1];//错误写法，当index=0时i-1=-1;
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
    }


    @Override
    public E get(int index) {
        //需要对这个index进行合法性校验
        //每次都进行合法性检验，何不直接封装为一个统一的方法？
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        //需要对这个index进行合法性校验
        rangeCheck(index);
        //把原来的要被替换的显示出来
        E oldElement = elements[index];
        elements[index] = element;//把新的内容覆盖原来的位置即可

        return oldElement;
    }


    /**
     * 重点!!!!
     *
     * @param index
     * @return 返回被删除的元素
     * 思路：有后面的元素覆盖那个被删除的位置，后面元素依次往前覆盖。
     * 不用管最后一个元素
     * 最后size--
     */
    @Override
    public E remove(int index) {
        /**
         * 可以考虑缩容
         */
        if (isEmpty()) {
            return null;
        }
        //需要对这个index进行合法性校验
        rangeCheck(index);
        E removedElement = elements[index];
        /*for (int i = 0; i < size; i++) {
            elements[index + i] = elements[index + 1 + i];
        }*/
        //优化
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        elements[size] = null;//最后一个元素设置为null

        //进行缩容
        trim();
        return removedElement;
    }


    public void remove(E element) {
        remove(indexOf(element));//通过元素找索引，再调用remove(int index)方法即可
    }

    /**
     * 查看元素的索引
     *
     * @param element 被移除的元素
     * @return 被移除的元素
     */
    @Override
    public int indexOf(E element) {
        //遍历整个数组
        if (element == null) {
            for (int i = 0; i < size; i++) {
                //如果你想要查看的那个元素在数组中，我就返回索引给你
                if (elements[i] == null) return i;//找到为null的索引
                //这里的代码还可以优化，如果有多个重复的元素的话，将重复元素的下标存在一个集合中，返回集合，后面再补充
            }
        } else {
            for (int i = 0; i < size; i++) {
                //如果你想要查看的那个元素在数组中，我就返回索引给你
                if (element.equals(elements[i])) return i;
                //这里的代码还可以优化，如果有多个重复的元素的话，将重复元素的下标存在一个集合中，返回集合，后面再补充
            }
        }
        //没有找到，就返回-1
        return ELEMENT_NOT_FOUND;
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
        /*
         解释一下位运算
         a>>n，意思就是将a先转化为二进制，再整体移动n位
         几个例子
         4>>1
         a=4 二进制的话就是100 100右移一位变为010=2
         4->2四就变成了2
         其实就是移动一位，值变为原来的1/2
         */
        //创建一个新的数组
        E[] newElements = (E[]) new Object[newCapacity];
        //把原来数组中的数据拷贝过来
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        //复制完毕后，用newElements覆盖掉elements
        elements = newElements;
        //为了显示已经进行了扩容
        System.out.println("oldCapacity\t" + oldCapacity + "扩容为->" + "newCapacity\t" + newCapacity);
        /* System.arraycopy(elements, 0, newElements,0,oldCapacity);*/
        /*
        Params:
        src – the source array.原来的数组
        srcPos – starting position in the source array.原数组的起始位置
        dest – the destination array.现在的新数组
        destPos – starting position in the destination data.新数组的起始位置
        length – the number of array elements to be copied.复制的元素个数
        public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);
         */


    }

    //制定缩容规则：比如剩余空间还一半的时候
    private void trim() {
        int oldCapacity = elements.length;//拿到原来数组的容量
        int newCapacity = oldCapacity >> 1;

        //capacity <= DEFAULT_CAPACITY
        // 这个条件是为了防止一直缩容，不然就会一直缩容下去，这种操作是不允许的
        //12-6-3-1
        if (size >= newCapacity || oldCapacity <= DEFAULT_CAPACITY) return;
        //进行数组的复制
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        //把数组还回去
        elements = newElements;
        System.out.println("oldCapacity\t" + oldCapacity + "缩容为->" + "newCapacity\t" + newCapacity);

    }

    //对传入的index进行合法性校验
    private /*static 错误写法，静态不能访问非静态，所以这里的size就会找不到*/void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ":Size:" + size + ",index越界异常");
        }
    }
    private void rangeCheckForAdd(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + ":Size:" + size + ",index越界异常");
        }
    }
    @Override
    public String toString() {

        //达到这种效果
        // {size=3, elements=[10, 15, 10]}
       /* return "DynamicArray{" +
                "size=" + size +
                ", elements=" + Arrays.toString(elements) +
                '}';*/
        //[]
        if (elements == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{size=" + size + ", elements=[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
                sb.append(elements[i]);
            } else {
                sb.append(elements[i]);
            }
        }
        sb.append("]}");
        return sb.toString();
    }

}
