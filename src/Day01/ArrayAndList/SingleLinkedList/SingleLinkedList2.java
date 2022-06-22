package Day01.ArrayAndList.SingleLinkedList;


import Day01.ArrayAndList.AbstractList;

/**
 * 增加一个虚拟头节点
 *
 * @ClassName Day01.LinkedList
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/19 10:01
 * @Version 1.0
 * 推荐一个算法网站
 * https://visualgo.net/zh
 */
//创建一个链表类
public class SingleLinkedList2<E> extends AbstractList<E> {

    //新增一个构造器，搞一个虚拟头节点

    public SingleLinkedList2() {
        first = new Node<>(null, null);
    }

    private Node<E> first;

    @Override
    public void clear() {
        size = 0;
        first = null;//把后面的节点全部断点
    }


    /**
     * @param index   插入的位置
     * @param element 插入的元素
     */
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        //index可能是0

        Node<E> previous = index == 0 ? first : getIndexNode(index - 1);
        //1,2,3，在2的位置插入，现在previous就是1，index就是2，index的下一个节点就是3

        //previous.next，就是index的下一个节点
        previous.next = new Node<>(element, previous.next);//指向新节点即可1的next域直接指向了index

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

        //因为头节点不能动
        Node<E> temp = first.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
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
        Node<E> prev = index == 0 ? first : getIndexNode(index - 1);
        Node<E> removedNode = prev.next;
        prev.next = prev.next.next;

        size--;
        return removedNode.element;
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
        Node<E> temp = first.next;
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


    //  1. 把节点放在内部，使用内部类的写法
    private static class Node<E> {
        E element;//数据
        Node<E> next;//next域


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

    //测试一把
    public static void main(String[] args) {
        SingleLinkedList2<Integer> list = new SingleLinkedList2<>();
//        list.remove(0);
        list.add(0, 10);
        list.add(1, 20);
        list.add(2, 30);

        list.add(3, 40);
        System.out.println(list);
        System.out.println(list.indexOf(40));
        Integer remove = list.remove(1);
        System.out.println("remove:" + remove);

    }
}
