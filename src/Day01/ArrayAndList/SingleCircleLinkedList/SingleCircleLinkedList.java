package Day01.ArrayAndList.SingleCircleLinkedList;


import Day01.ArrayAndList.AbstractList;


/**
 * @ClassName Day01.LinkedList
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 10:01
 * @Version 1.0
 * �Ƽ�һ���㷨��վ
 * https://visualgo.net/zh
 */
//����һ��������
public class SingleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    @Override
    public void clear() {
        size = 0;
        first = null;//�Ѻ���Ľڵ�ȫ���ϵ�
    }


    /**
     * @param index   �����λ��
     * @param element �����Ԫ��
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //index������0
        /*
        �����ǵ���ѭ���������м���벻��Ҫ�ܣ�����ǰһ������ͷ�ڵ�����������Ҫά��
         */
        if (index == 0) {
            Node<E> newFirst=new Node<>(element, first);//���������ڵ�ͳ����µ�ͷ�ڵ�

            //��δ����Ǹ����ߺ���Ҫ������
            // �õ����һ���ڵ�
            //������Ҫ����һ���õ������һ��λ���ǲ��ǵ�һ��λ�ã�����ǵ�һ��λ�ã���ô����first��nextָ��first
            Node<E> indexNode = size == 0 ? newFirst : getIndexNode(size-1);
            indexNode.next = newFirst;
            first=newFirst;//����ȥ

        } else {
            //1,2,3����2��λ�ò��룬����previous����1��index����2��index����һ���ڵ����3
            Node<E> previous = getIndexNode(index - 1);//��ȡǰһ���ڵ�

            //previous.next������index����һ���ڵ�
            previous.next = new Node<>(element, previous.next);//ָ���½ڵ㼴��1��next��ֱ��ָ����index
        }

        size++;
    }

    /**
     * ��ȡindexλ�ö�Ӧ�Ľڵ����
     *
     * @param index �ڸ�����������ڵ�
     * @return �����������ڵ�
     */
    private Node<E> getIndexNode(int index) {
        //�ȼ��һ������
        rangeCheck(index);

        //��Ϊͷ�ڵ㲻�ܶ�
        Node<E> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = getIndexNode(index);
        E oldNode = node.element;//����һ����ǰ������
        node.element = element;//����ǰ�����ݸ��ǵ��Ϳ�����
        return oldNode;
    }

    @Override
    public E get(int index) {
        return getIndexNode(index).element;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> removedNode = first;
        if (index == 0) {

            if (size == 1) first = null;
            //�õ����һ���ڵ㣬�õ��Ժ������һ���ڵ�ָ��first����һ��
            Node<E> indexNode = getIndexNode(size - 1);//���д������Ҫ��  first = first.next;֮ǰ
            // ����Ȼ����getIndexNode��ʱ�򣬻��õ�firstָ�룬�ȶ���first���Ҳ�������Ǹ��ڵ���
            first = first.next;
            indexNode.next = first;

            //���ǻ���Ҫ����һ�������sizeΪ1�أ����޷�ɾ����������Ҫ���⴦��


        } else {
            Node<E> prev = getIndexNode(index - 1);//��ô�ɾ���ڵ��ǰһ���ڵ�
            removedNode = prev.next;
            prev.next = prev.next.next;

        }
        size--;
        return removedNode.element;
    }

    @Override
    public int indexOf(E element) {
        //������������
        Node<E> temp = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                //�������Ҫ�鿴���Ǹ�Ԫ���������У��Ҿͷ�����������
                if (temp.element == null) return i;//�ҵ�Ϊnull������
                //����Ĵ��뻹�����Ż�������ж���ظ���Ԫ�صĻ������ظ�Ԫ�ص��±����һ�������У����ؼ��ϣ������ٲ���
                temp = temp.next;//����
            }
        } else {
            for (int i = 0; i < size; i++) {
                //�������Ҫ�鿴���Ǹ�Ԫ���������У��Ҿͷ�����������
                if (element.equals(temp.element)) return i;
                //����Ĵ��뻹�����Ż�������ж���ظ���Ԫ�صĻ������ظ�Ԫ�ص��±����һ�������У����ؼ��ϣ������ٲ���
                temp = temp.next;//����
            }
        }
        //û���ҵ����ͷ���-1
        return -1;
    }

    @Override
    public String toString() {
        Node<E> temp = first;
        if (temp.element == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{size=" + size + ", elements=[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
                sb.append(temp.element);
            } else {
                sb.append(temp.element);
            }
            temp = temp.next;
        }
        sb.append("]}");
        return sb.toString();
    }


    //  1. �ѽڵ�����ڲ���ʹ���ڲ����д��
    private static class Node<E> {
        E element;//����
        Node<E> next;//next��


        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }


    }

}
