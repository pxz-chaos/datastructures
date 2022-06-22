package Day01.ArrayAndList.DoubleCircleLinkedList;


import Day01.ArrayAndList.AbstractList;


/**
 * @ClassName Day01.LinkedList
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 10:01
 * @Version 1.0
 * 推荐一个算法网站
 * https://visualgo.net/zh
 */
//创建一个链表类
public class DoubleCircleLinkedList<E> extends AbstractList<E> {


    private Node<E> first;
    private Node<E> last;

    @Override
    public void clear() {
        size = 0;
        first = null;//把后面的节点全部断点
        last = null;//把后面的节点全部断点
    }


    /**
     * 插入的情况有三种，头部，中间，尾部
     * 无论是哪种插入，都要谨慎处理边界条件
     * @param index   插入的位置
     * @param element 插入的元素
     */

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //index可能是0

        if (index == size) {//往最后面添加元素
            Node<E> oldLast = last;
            Node<E> newNode= new Node<>(last/*getIndexNode(size-1)*/, element, null);
            last=newNode;
            if (oldLast == null) {//链表添加的第一个元素，也就是现在没有任何元素
                first = newNode;
            } else {
                oldLast.next = newNode;
            }
        } else {
            Node<E> indexNode = getIndexNode(index);//找到自己
            Node<E> prev = indexNode.prev;//index的前一个节点
            Node<E> node = new Node<>(prev, element, indexNode);
            //新节点的prev指向了prev = indexNode.prev;//index的前一个节点
            //新节点的next指向了indexNode
            indexNode.prev = node;
        /*
        如果index=0，那么prev就为空，所以需要单独考虑
         */
            if (prev == null) {
                first = node;

            } else {
                prev.next = node;
            }

        }

        //在索引后面添加进行添加
        // Node<E> indexNode = getIndexNode(index);//找到自己
       /* Node<E> node = new Node<>(null, element, null);
        node.next=indexNode.next;
        indexNode.next=node;

        node.prev=indexNode.next.prev;
        indexNode.next.prev=node;*/
        //在索引前面进行添加

        size++;
    }

    /**
     * 获取index位置对应的节点对象
     *
     * @param index 在该索引处插入节点
     * @return 返回索引处节点
     */
    private Node<E> getIndexNode(int index) {
        //先检查一波索引
        rangeCheck(index);

        //index没有超过size的一半，那么就按照.next遍历
        //因为头节点不能动
        if (index < (size >> 1)) {
            Node<E> temp = first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp;
        } else {
            //否者就按照prev遍历
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
        E oldNode = node.element;//保留一下以前的内容
        node.element = element;//把以前的内容覆盖掉就可以了
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

        //如果index=0，那么indexNode.prev=null，null没有next
        if (indexNode.prev == null) {
            first = indexNode.next;
        } else {
            indexNode.prev.next = indexNode.next;
        }

        //如果index=size-1，那么indexNode.next=null，null没有prev
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

        //遍历整个数组
        Node<E> temp = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                //如果你想要查看的那个元素在数组中，我就返回索引给你
                if (temp.element == null) return i;//找到为null的索引
                //这里的代码还可以优化，如果有多个重复的元素的话，将重复元素的下标存在一个集合中，返回集合，后面再补充
                temp = temp.next;//后移
            }
        } else {
            for (int i = 0; i < size; i++) {
                //如果你想要查看的那个元素在数组中，我就返回索引给你
                if (element.equals(temp.element)) return i;
                //这里的代码还可以优化，如果有多个重复的元素的话，将重复元素的下标存在一个集合中，返回集合，后面再补充
                temp = temp.next;//后移
            }
        }
        //没有找到，就返回-1
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

    //  1. 把节点放在内部，使用内部类的写法
    private static class Node<E> {
        E element;//数据
        Node<E> next;//next域
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

    //测试一把
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
