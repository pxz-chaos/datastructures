package Day02.QueueAndStack.Queue;

/**
 * @InterfaceName QueueInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/26 9:55
 * @Version 1.0
 */
public interface QueueInterface<E> {
    int size();//Ԫ�ص�����


    boolean isEmpty();//�Ƿ�Ϊ��

    void enQueue(E element);//���

    E deQueue();//����

    E front();//��ȡ���е�ͷԪ��



    void clear();//�������Ԫ��
}
