package Day03.Tree.BST;

import Day03.Tree.Tree;
import Day03.Tree.printer.BinaryTreeInfo;

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
public class BinarySearchTree<E> implements Tree<E>, BinaryTreeInfo {
    private Comparator<E> compactor;//传一个比较器
    private int size;

    public BinarySearchTree(Comparator<E> comparator) {//传一个比较器
        this.compactor = comparator;
    }

    public BinarySearchTree() {
        this.compactor = null;
    }

    private Node<E> root;//必须有一个根节点，这里默认就是null

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);

        //添加的第一个节点
        if (root == null) {
            root = new Node<>(element, null);//第一个节点（根节点）是没有父节点的
            size++;
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
            if (cmp > 0) {//大于零
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
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
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
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
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
        return node != null ? node : null;
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
     * 前序遍历,采用递归,根—>左->右
     */
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        //打印节点
        if (node == null) return;
        System.out.println(node.element);//根的位置
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /**
     * 改进后的前序遍历方法
     *
     * @param visitor 函数式接口
     */
    public void preorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        preorderTraversal(root, visitor);
    }

    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        //打印节点
        if (node == null || visitor.stop) return;//visitor.stop=true了就终止遍历
        // System.out.println(node.element);//根的位置
        visitor.stop = visitor.visit(node.element);

        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历,左->根->右
     */
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);//根的位置
        inorderTraversal(node.right);
    }

    /**
     * 改进后的中序遍历
     *
     * @param visitor
     */
    public void inorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inorderTraversal(root, visitor);
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;//visitor.stop=true就停止遍历
        inorderTraversal(node.left, visitor);
//        System.out.println(node.element);//根的位置
        if (visitor.stop) return;//防止遍历左子树的时候已经出现了visitor.stop=true了，打印还没有停止，下面同理

        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * 后序遍历，左->右->根
     */
    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) return;

        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);//根的位置

    }

    public void postorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        postorderTraversal(root, visitor);
    }

    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
//        System.out.println(node.element);//根的位置
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);

    }
    /*
      层序遍历，就是从上往下，一层一层遍历，这里就不能采用递归了,这里采用队列
     思路：
      1.先将根节点入队
      2.循环执行以下操作，直到队列为空
        将对头节点A出队，进行访问
        将A的左子节点入队
        将A的右子节点入队
     */

    public void levelOrderTraversal() {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);//先将根节点入队

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();//取出头节点
            System.out.println(node.element);//打印

            if (node.left != null) {//取出的头节点如果有左子节点，那么就入队
                queue.offer(node.left);
            }
            if (node.right != null) {//取出的头节点如果有右子节点，那么就入队
                queue.offer(node.right);
            }

        }
    }

    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);//先将根节点入队

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();//取出头节点
            // System.out.println(node.element);//之前这里是打印
//            visitor.visit(node.element;//这个元素拿到外面去，你自己去实现逻辑
            if (visitor.visit(node.element) == true) return;//遍历的升级

            if (node.left != null) {//取出的头节点如果有左子节点，那么就入队
                queue.offer(node.left);
            }
            if (node.right != null) {//取出的头节点如果有右子节点，那么就入队
                queue.offer(node.right);
            }

        }
    }

    /**
     * 是否为完全二叉树
     *
     * @return
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
     * 采用迭代的方法
     * 这里采用层序遍历方法
     *
     * @return
     */
    public int height() {
        if (root == null) return 0;

        int height = 0;//记录树的高度

        int levelSize = 1;//存储每一层的元素个数，默认有一个根节点

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);

            if (levelSize == 0) {//意味着已经访问完了一层元素，即将访问下一层元素
                levelSize = queue.size();//下一层的元素数量
                height++;//访问完一层高度就+1
            }

        }

        return height;
    }

    /**
     * 用于计算二叉树的高度
     * 访问某个节点的高度，这里采用递归的形式
     *
     * @return
     */
    public int height1() {
        return height1(root);
    }

    /**
     * 访问某个节点的高度，这里采用递归的形式
     *
     * @param node 传入你想访问的某个节点的高度
     * @return 高度
     * 思路：左右子节点的最大高度+1
     */
    public int height1(Node<E> node) {

        if (node == null) return 0;
        int height = Math.max(height1(node.left), height1(node.right)) + 1;
        return height;
    }

    /**
     * 求前驱结点
     * 前驱结点：中序遍历的前一个节点
     *
     * @param node 节点
     * @return 该节点的前驱结点
     */
    public Node<E> predecessor(Node<E> node) {
        //前驱结点就是该节点的最右的那个节点
        //比如中序遍历为，1,2,3,4,5,6,7,8
        //7的前驱结点就是6
        if (node == null) return null;

        // 前驱节点在左子树当中（left.right.right.right....）
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        //能来到这里的就是node.left==null
        // 从父节点、祖父节点中寻找前驱节点

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;


    }

    /**
     * 寻找后继节点
     * 后驱节点：中序遍历的后一个节点
     *
     * @param node
     * @return
     */
    public Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;

    }
//下面四个方法适用于打印使用的

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }

    /**
     * @param e1
     * @param e2
     * @return 返回值等于0，代表e1等于e2，返回值大于零，代表e1大于e2，返回值小于零，代表e1小于e2
     */
    private int compare(E e1, E e2) {

        if (compactor != null) {// private Compactor<E> compactor;//传一个比较器，如果compactor不为空，则优先用比较器
            return compactor.compare(e1, e2);

        }
        //比较器为空,那么你这个参数就是可比较的。那么就优先用Comparable<E>接口
        return ((Comparable<E>) e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");//抛出一个非法参数异常
        }
    }


    private static class Node<E> {
        E element;//元素
        Node<E> left;//左子节点
        Node<E> right;//右子节点
        Node<E> parent;//父节点

        public Node(E element, Node<E> parent) {//可能没有左右节点，所以传父节点就可以了
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {//是否为叶子节点
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//是否有两个节点
            return right != null & left != null;
        }
    }

}

/*interface BinaryTree<E> {
    int size();

    boolean isEmpty();

    void clear();

    void add(E element);

    void remove(E element);

    boolean contains(E element);

}*/
//优化遍历逻辑，这里采用一个接口，让外部使用者自定义遍历的方式，不仅仅是打印


/**
 * 抽象类，用于自定义遍历，之前使用的是函数式接口
 *
 * @param <E>
 */
abstract class Visitor<E> {
    //void visit(E e);//这里只有一个抽象方法，所以可以使用函数式接口
    boolean stop = false;//用于记录遍历什么时候终止

    public abstract boolean visit(E e);//再次改进，改进的效果是，按需求遍历元素，不需要全部遍历完所有二叉树
}
