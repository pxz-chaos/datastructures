package Day01.ArrayAndList;

/**
 * @InterfaceName DynamicArrInterface
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 11:14
 * @Version 1.0
 */
/*
��ɾ�Ĳ�ͨ�ýӿڣ�ֻ������������д����
 */
public interface List<E> {
    /**
     * �������Ԫ��
     */
    void clear();

    /**
     * @return Ԫ�ص�����
     */
    int size();

    /**
     * @return �Ƿ�Ϊ��
     */
    boolean isEmpty();

    /**
     * �Ƿ����elementԪ��
     *
     * @param element
     * @return ����������true ���򣬷���false
     */
    boolean contains(E element);

    /**
     * ���Ԫ�ص������
     *
     * @param element Ҫ��ӵ�Ԫ��
     */
    void add(E element);

    /**
     * �ھ�������������Ԫ��
     * @param index
     * @param element
     */
    void add(int index, E element);


    /**
     * ����indexλ�õ�Ԫ��
     *
     * @param index
     * @param element
     * @return
     */
    E set(int index, E element);

    /**
     * @param index
     * @return ����indexλ�ô���Ԫ��
     */
    E get(int index);

    /**
     * ɾ��indexλ�ô���Ԫ��
     *
     * @param index
     * @return ���ر�ɾ����Ԫ��
     */
    E remove(int index);

    /**
     * �鿴Ԫ�ص�λ��
     *
     * @param element
     * @return ���ر��鿴��Ԫ��λ��
     */
    int indexOf(E element);

    String toString();
}
