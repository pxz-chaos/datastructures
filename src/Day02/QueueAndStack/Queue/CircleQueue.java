package Day02.QueueAndStack.Queue;

/**
 * @ClassName CircleQueue
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/28 14:44
 * @Version 1.0
 */
/*
    用数组实现循环队列
 */
public class CircleQueue<E> {
    private int size;
    private int front;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 将索引进行封装
     *
     * @param index 索引
     * @return
     */
    private int index(int index) {
        return (front + index) % elements.length;
    }

    /**
     * @param element 入队元素
     */
    public void enQueue(E element) {
        //搞一个扩容
        ensureCapacity(size + 1);
        elements[index(size)/*(front + size) % elements.length*/] = element;//往最后添加
        size++;
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

    /**
     * @return 出队
     */

    public E deQueue() {
        //先获取对头元素
        if (isEmpty()) return null;
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1) /*(front + 1) % elements.length*/;
        size--;
        return frontElement;
    }

    /**
     * @return 返回头元素
     */

    public E front() {
        return elements[front];
    }


    public void clear() {

        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
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
        CircleQueue<Integer> queue = new CircleQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        queue.clear();
        System.out.println(queue);
        //0-9
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        System.out.println(queue);
        //5-9
        for (int i = 15; i < 20; i++) {
            queue.enQueue(i);
        }

        for (int i = 20; i < 26; i++) {
            queue.enQueue(i);
        }
        //5-9,15-19

        System.out.println(queue);
        System.out.println("===============");
        while (!queue.isEmpty()) {
            System.out.print(queue.deQueue() + "\t");
        }
    }

    public static void main(String[] args) {

        test();
    }
}
