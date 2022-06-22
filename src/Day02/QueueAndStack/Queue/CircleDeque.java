package Day02.QueueAndStack.Queue;

/**
 * @ClassName CircleQueue
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/28 14:44
 * @Version 1.0
 */

/*
    循环双端队列
 */
public class CircleDeque<E> implements DequeInterface<E> {
    private int size;
    private int front;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    private int index(int index) {
        index += front;
        if (index < 0) {
            return (index + elements.length);//可以不用再取模了
        }
        return (index) % elements.length;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity < capacity) {
            //扩容,进行位运算的时候一定要将位运算括起来(oldCapacity >> 1)
            int newCapacity = oldCapacity + (oldCapacity >> 1);//oldCapacity >> 1==oldCapacity*0.5

            //创建一个新的数组
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
                //*elements[i]*/错误写法
                // (i+front)%elements.length获取它在数组中的真实索引
            }

            elements = newElements;
            front = 0;//重置对头指针

            System.out.println("oldCapacity\t" + oldCapacity + "扩容为->" + "newCapacity\t" + newCapacity);
        }

    }

    public CircleDeque() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    @Override
    public E deQueueRear() {
        //拿到尾部索引
        if (isEmpty()) return null;
        int rearIndex = index(size - 1);
        E rearElement = elements[rearIndex];
        elements[rearIndex] = null;//置空
        size--;
        return rearElement;
    }

    @Override
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;
    }

    @Override
    public E deQueueFront() {
        //先获取对头元素
        if (isEmpty()) return null;
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1) /*(front + 1) % elements.length*/;
        size--;
        return frontElement;
    }

    @Override
    public E front() {
        return elements[front];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        /* size=0;*///只用这行代码的话，没有将内存清空
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E rear() {
        return elements[index(size - 1)];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(", {[");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]}");
        return sb.toString();
    }

    static void test() {
        CircleDeque<Integer> queue = new CircleDeque<>();

        //头 5,4,3,2,1 ，100,101,102,103,104,8,7,6尾部
        //继续扩容
        //capacity=22 size=20 front=20,
        // {[8,7,6,5,4,3,2,1,100,101,102,103,104,105,106,107,108,109,null,null,10,9]}
        for (int i = 0; i < 10; i++) {
            queue.enQueueFront(i + 1);
            queue.enQueueRear(i + 100);
        }

        System.out.println(queue);

        queue.clear();
        System.out.println("===============");
        while (!queue.isEmpty()) {
            System.out.print(queue.deQueueFront() + "\t");
        }
    }

    public static void main(String[] args) {

        test();
    }
}
