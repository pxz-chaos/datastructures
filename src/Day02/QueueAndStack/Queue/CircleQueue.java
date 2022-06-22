package Day02.QueueAndStack.Queue;

/**
 * @ClassName CircleQueue
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/28 14:44
 * @Version 1.0
 */
/*
    ������ʵ��ѭ������
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
     * ���������з�װ
     *
     * @param index ����
     * @return
     */
    private int index(int index) {
        return (front + index) % elements.length;
    }

    /**
     * @param element ���Ԫ��
     */
    public void enQueue(E element) {
        //��һ������
        ensureCapacity(size + 1);
        elements[index(size)/*(front + size) % elements.length*/] = element;//��������
        size++;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity < capacity) {
            //����,����λ�����ʱ��һ��Ҫ��λ����������(oldCapacity >> 1)
            int newCapacity = oldCapacity + (oldCapacity >> 1);//oldCapacity >> 1==oldCapacity*0.5

            //����һ���µ�����
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
                //*elements[i]*/����д��
                // (i+front)%elements.length��ȡ���������е���ʵ����
            }

            elements = newElements;
            front = 0;//���ö�ͷָ��

            System.out.println("oldCapacity\t" + oldCapacity + "����Ϊ->" + "newCapacity\t" + newCapacity);
        }

    }

    /**
     * @return ����
     */

    public E deQueue() {
        //�Ȼ�ȡ��ͷԪ��
        if (isEmpty()) return null;
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1) /*(front + 1) % elements.length*/;
        size--;
        return frontElement;
    }

    /**
     * @return ����ͷԪ��
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
