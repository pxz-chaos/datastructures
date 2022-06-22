package Day03.Tree.BinaryTreePackage;

import Day03.Tree.Tree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName BinarySearchTree
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/29 19:09
 * @Version 1.0
 */

/*
二叉树的节点不能为空
前中后序遍历
规则就是看根的位置
根在前，就是前序遍历，根左右
根在中，就是中序遍历，左根右
根在后，就是后序遍历，左右根

注意：左右没有顺序，先右后左也是可以的
 */
@SuppressWarnings("unchecked")
public class BST<E> extends BinaryTree<E> implements Tree<E> {
    private Comparator<E> compactor;//传一个比较器

    public BST(Comparator<E> comparator) {//传一个比较器
        this.compactor = comparator;
    }

    public BST() {
        this.compactor = null;
    }
   /* @Override
    public String toString() {
        Node<E> myNode = (Node<E>) node;
        String parentStr = "null";
        if (myNode.parent != null) {
            parentStr = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentStr + ")";
    }*/

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        //添加的第一个节点
        if (root == null) {
            // root = new Node<>(element, null);//第一个节点（根节点）是没有父节点的
            root = createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }

        //添加的不是第一个节点
        //找到父节点
        Node<E> temp = root;//临时节点，先让其指向根节点，每次遍历都从根节点出发
        Node<E> parent = root;//先让其指向根节点
        int cmp = 0;//记录比较方向
        while (temp != null) {

            //进行比较
            //cmp= ((Comparable<E>) element).compareTo(temp.element);//行代码针对于那种可比较的元素

            cmp = compare(element, temp.element);//记录比较后的结果，这里会返回0，-1,1
            parent = temp;//也就是即将被插入的节点的前一个位置,这行代码必须写在while循环中，不然父节点永远不会更新。
            if (cmp > 0) {//大于零，这是允许有重复元素的操作
                temp = temp.right;//后移，相当于以前的temp=temp.next;
            } else if (cmp < 0) {//小于零
                temp = temp.left;
            } else {//等于零
                //选择覆盖掉原来的内容
                temp.element = element;
                return;
            }

        }

        //退出循环以后，node就为null了，此时的父节点我提前保存了，为parent节点
        //看看插入到父节点的哪一个位置
        // Node<E> newNode = new Node<>(element, parent);
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        afterAdd(newNode);
        size++;
    }

    /**
     * 添加节点之后的操作，主要是维护后面的AVL树
     *
     * @param node 新添加的节点
     */
    protected void afterAdd(Node<E> node) {

    }

    /**
     * 删除节点之后的操作，主要是维护后面的AVL树
     *
     * @param node 待删除的节点
     */
    protected void afterRemove(Node<E> node) {

    }

    @Override
    public void remove(E element) {
        remove(findNode(element));
    }

    /**
     * 真正需要写的删除代码
     *
     * @param node 被删除的那个节点
     */

    private void remove(Node<E> node) {
        if (node == null) return;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<E> sucNode = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = sucNode.element;
            // 删除后继节点
            node = sucNode;

        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {//删除左节点
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            //等到那个节点真正被删除以后再做平衡调整
            afterRemove(node);

        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;
            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
            afterRemove(node);
        }

        size--;
    }

    /**
     * 先根据那个元素找到那个节点的位置
     *
     * @param element 被删除的元素
     * @return 被删除的节点
     */
    private Node<E> findNode(E element) {

        //方法一：；老师写的
       /*
        Node<E> node = root;
       while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) {
                //找到了
                return node;
            }
            if (cmp > 0) {//即element > node.element
                node = node.right;//右移
            } else {//cmp<0
                node = node.left;
            }
        }
        return null;

        */
        //方法二：自己写的
        Node<E> node = root;
        while (node != null && node.element != element) {
            node = compare(node.element, element) > 0 ? node.left : node.right;
        }
        //退出循环以后还没有找到，那么直接返回null;
        return node/*node != null ? node :null*/;
    }

    @Override
    public boolean contains(E element) {
//方法一：采用迭代的方法
//        if (root == null) {
//            return false;
//        }
//
//        Node<E> node = root;
//
//        while (node != null && node.element != element) {
//            node = compare(node.element, element) > 0 ? node.left : node.right;
//        }
//        if (node == null) return false;
//        return node != null;


        //方式二：采用层序遍历

//        Queue<Node<E>> queue = new LinkedList<>();
//        queue.offer(root);
//        boolean flag = false;
//        while (!queue.isEmpty()) {
//            Node<E> node = queue.poll();
//            if (node.element == element) {
//                flag = true;
//                break;
//            }
//            if (node.left != null) queue.offer(node.left);
//            if (node.right != null) queue.offer(node.right);
//        }
//        return flag;
        //方式3：直接调用方法findNode(element)
        return findNode(element) != null;
    }


    /**
     * 是否为完全二叉树
     *
     * @return 是二叉树返回true，否则为false
     */
    public boolean isComplete() {
        if (root == null) return false;//空树就不是

        //层序遍历一波
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;//叶子节点的标记
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            //......度为1的节点要么只有一个，要么一个都没有
            if (leaf && !node.isLeaf()) {//要求你为叶子节点，但是你不是叶子节点
                return false;
            }

            /*if (node.hasTwoChildren()) {//左右均不为空，那么入队
                // ，但是有一个bug，只有左右都存在才能入队
                // 也就是其情况就不能入队了
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) return false;

            else {//后面遍历都必须是叶子节点
                //能来到这里的就是
                *//*node.left!=null&&node.right==null或者node.left==null&&node.right==null*//*
                //代码修复
                if (node.left != null) {
                    queue.offer(node.left);
                }
                leaf = true;

            }*/

            //继续优化
            if (node.left != null) {//左边不为空
                queue.offer(node.left);
            }
            //左边为空
            else if (node.right != null) {//左边为空，右边不为空
                return false;
            }
            if (node.right != null) {//右边不为空
                queue.offer(node.right);
            }
            //右边为空,左边为空或者左边不为空
            else {
                leaf = true;
            }


        }
        return true;
    }


    /**
     * @param e1 节点的值
     * @param e2 被传入节点的值
     * @return 返回值等于0，代表e1等于e2，返回值大于零，代表e1大于e2，返回值小于零，代表e1小于e2
     */
    private int compare(E e1, E e2) {

        if (compactor != null) {// private Compactor<E> compactor;//传一个比较器，如果compactor不为空，则优先用比较器
            return compactor.compare(e1, e2);

        }
        //比较器为空,那么你这个参数就是可比较的。那么就优先用Comparable<E>接口
        return ((Comparable<E>) e1).compareTo(e2);
    }

}



