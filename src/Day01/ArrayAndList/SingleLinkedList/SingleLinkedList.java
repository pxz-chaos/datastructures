package Day01.ArrayAndList.SingleLinkedList;

import Day01.ArrayAndList.AbstractList;
import Day01.ArrayAndList.ArrayList.ArrayList;


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
public class SingleLinkedList<E> extends AbstractList<E> {

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
        if (index == 0) {
            first = new Node<>(element, first);
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

    public void remove() {
        remove(size - 1);
        size--;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> removedNode = null;
        if (index == 0) {
            removedNode = first;
            first = first.next;

        } else {
            Node<E> prev = getIndexNode(index - 1);//��ô�ɾ���ڵ��ǰһ���ڵ�
            removedNode = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return removedNode.element;
    }

    /**
     * @param element ������Ԫ��
     * @return ������Ԫ�ص�������û�з���-1
     */
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

    public ArrayList<Integer> indexOfAll(E element) {
        Node<E> tmp = first;
        ArrayList<Integer> list = new ArrayList<>();
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (tmp.element == null) {
                    list.add(i);
                }
                tmp = tmp.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                //�������Ҫ�鿴���Ǹ�Ԫ���������У��Ҿͷ�����������
                if (element.equals(tmp.element)) list.add(i);
                //����Ĵ��뻹�����Ż�������ж���ظ���Ԫ�صĻ������ظ�Ԫ�ص��±����һ�������У����ؼ��ϣ������ٲ���
                tmp = tmp.next;//����
            }
        }
        //û���ҵ����ͷ���null
        return list;
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

    //����һ��
    public static void main(String[] args) {
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
//        list.remove(0);
        list.add(20);
        list.add(300);
        list.add(300);
        list.add(300);
        list.add(300);
        list.add(null);
        list.add(null);
        list.add(null);
        list.add(0, 10);
        list.add(1, 20);
        list.add(2, 30);
        int len = list.size();
        for (int i = 0; i < len; i++) {
            System.out.println(list.get(i));
        }
//        ArrayList<Integer> all = list.indexOfAll(null);
//        System.out.println(all);



        list.add(3, 40);
        System.out.println(list);
        System.out.println(list.indexOf(40));
        Integer remove = list.remove(1);
        System.out.println("remove:" + remove);
    }
}
