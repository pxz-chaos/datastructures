package Day01.ArrayAndList.DoubleCircleLinkedList;


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
public class DoubleCircleLinkedList<E> extends AbstractList<E> {


    private Node<E> first;
    private Node<E> last;

    @Override
    public void clear() {
        size = 0;
        first = null;//�Ѻ���Ľڵ�ȫ���ϵ�
        last = null;//�Ѻ���Ľڵ�ȫ���ϵ�
    }


    /**
     * �������������֣�ͷ�����м䣬β��
     * ���������ֲ��룬��Ҫ��������߽�����
     * @param index   �����λ��
     * @param element �����Ԫ��
     */

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //index������0

        if (index == size) {//����������Ԫ��
            Node<E> oldLast = last;
            Node<E> newNode= new Node<>(last/*getIndexNode(size-1)*/, element, null);
            last=newNode;
            if (oldLast == null) {//������ӵĵ�һ��Ԫ�أ�Ҳ��������û���κ�Ԫ��
                first = newNode;
            } else {
                oldLast.next = newNode;
            }
        } else {
            Node<E> indexNode = getIndexNode(index);//�ҵ��Լ�
            Node<E> prev = indexNode.prev;//index��ǰһ���ڵ�
            Node<E> node = new Node<>(prev, element, indexNode);
            //�½ڵ��prevָ����prev = indexNode.prev;//index��ǰһ���ڵ�
            //�½ڵ��nextָ����indexNode
            indexNode.prev = node;
        /*
        ���index=0����ôprev��Ϊ�գ�������Ҫ��������
         */
            if (prev == null) {
                first = node;

            } else {
                prev.next = node;
            }

        }

        //������������ӽ������
        // Node<E> indexNode = getIndexNode(index);//�ҵ��Լ�
       /* Node<E> node = new Node<>(null, element, null);
        node.next=indexNode.next;
        indexNode.next=node;

        node.prev=indexNode.next.prev;
        indexNode.next.prev=node;*/
        //������ǰ��������

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

        //indexû�г���size��һ�룬��ô�Ͱ���.next����
        //��Ϊͷ�ڵ㲻�ܶ�
        if (index < (size >> 1)) {
            Node<E> temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            //���߾Ͱ���prev����
            Node<E> temp = last;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
            return temp;
        }

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
        Node<E> indexNode = getIndexNode(index);

        //���index=0����ôindexNode.prev=null��nullû��next
        if (indexNode.prev == null) {
            first = indexNode.next;
        } else {
            indexNode.prev.next = indexNode.next;
        }

        //���index=size-1����ôindexNode.next=null��nullû��prev
        if (indexNode.next == null) {
            last = indexNode.prev;
        } else {
            indexNode.next.prev = indexNode.prev;
        }

        size--;
        return indexNode.element;
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
        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(node);
            node = node.next;
        }
        sb.append("]");
        return sb.toString();
    }

    //  1. �ѽڵ�����ڲ���ʹ���ڲ����д��
    private static class Node<E> {
        E element;//����
        Node<E> next;//next��
        Node<E> prev;


        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }

       /* @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }

            sb.append("_").append(element).append("_");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }

            return sb.toString();
        }*/

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    '}';
        }
    }

    //����һ��
    public static void main(String[] args) {
        DoubleCircleLinkedList<Integer> list = new DoubleCircleLinkedList<>();
//        list.remove(0);
        list.add(0, 10);
        list.add(20);
        list.add(0, 30);
        list.add(0, 40);
        list.add(0, 50);
        list.add(0, 60);


        System.out.println(list.toString());//[60,50,40,30,10,20]
        //size=6, [Node{element=60},Node{element=50},Node{element=40},Node{element=30},Node{element=10},Node{element=20}]


        System.out.println(list.indexOf(40));
        Integer remove = list.remove(1);
        System.out.println("remove:" + remove);

    }
}
