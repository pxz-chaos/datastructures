package Day01.ArrayAndList.SingleCircleLinkedList;


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
public class SingleCircleLinkedList<E> extends AbstractList<E> {

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
        /*
        由于是单向循环链表，往中间插入不需要管，和以前一样，往头节点那里插入就需要维护
         */
        if (index == 0) {
            Node<E> newFirst=new Node<>(element, first);//插入的这个节点就成了新的头节点

            //如何处理那根连线很重要！！！
            // 拿到最后一个节点
            //这里需要考虑一下拿到的最后一个位置是不是第一个位置，如果是第一个位置，那么就让first的next指向first
            Node<E> indexNode = size == 0 ? newFirst : getIndexNode(size-1);
            indexNode.next = newFirst;
            first=newFirst;//还回去

        } else {
            //1,2,3，在2的位置插入，现在previous就是1，index就是2，index的下一个节点就是3
            Node<E> previous = getIndexNode(index - 1);//获取前一个节点

            //previous.next，就是index的下一个节点
            previous.next = new Node<>(element, previous.next);//指向新节点即可1的next域直接指向了index
        }

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
        Node<E> temp = first;
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
        Node<E> removedNode = first;
        if (index == 0) {

            if (size == 1) first = null;
            //拿到最后一个节点，拿到以后让最后一个节点指向first的下一个
            Node<E> indexNode = getIndexNode(size - 1);//这行代码必须要在  first = first.next;之前
            // ，不然调用getIndexNode的时候，会用到first指针，先动了first就找不到最后那个节点了
            first = first.next;
            indexNode.next = first;

            //但是还需要考虑一种情况，size为1呢？就无法删除，所以需要特殊处理


        } else {
            Node<E> prev = getIndexNode(index - 1);//获得待删除节点的前一个节点
            removedNode = prev.next;
            prev.next = prev.next.next;

        }
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

}
