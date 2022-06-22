package Day03.Tree.BinaryTreePackage;

import Day03.Tree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName BinaryTree<E>
 * @Description TODO
 * @Author Zhang_peixin
 * @Date 2021/12/8 16:19
 * @Version 1.0
 */
@SuppressWarnings("unchecked")
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;//必须有一个根节点，这里默认就是null

    protected static class Node<E> {
        E element;//元素
        Node<E> left;//左子节点
        Node<E> right;//右子节点
        Node<E> parent;//父节点

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }


        public boolean isLeaf() {//是否为叶子节点
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//是否有两个节点
            return right != null & left != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 是否为兄弟节点
         *
         * @return 返回兄弟节点，没有就返回空
         */
        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;//没有兄弟节点
        }


        @Override
        public String toString() {

                String parentString = "null";
                if (parent != null) {
                    parentString = parent.element.toString();
                }
                return element + "_p(" + parentString + ")";
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
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
     * @param visitor 抽象类
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
            if (visitor.visit(node.element)) return;//遍历的升级

            if (node.left != null) {//取出的头节点如果有左子节点，那么就入队
                queue.offer(node.left);
            }
            if (node.right != null) {//取出的头节点如果有右子节点，那么就入队
                queue.offer(node.right);
            }

        }
    }


    /**
     * 采用迭代的方法
     * 这里采用层序遍历方法
     *
     * @return 高度
     */
    @SuppressWarnings("unused")
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
     * @return 高度
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
        return Math.max(height1(node.left), height1(node.right)) + 1;
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 判断一棵二叉树是否为平衡二叉树
     * 平衡二叉树：左右子树的高度相差不超过1，这种也叫AVL树
     *
     * @param node 可以直接传入根节点
     * @return true，false
     */

    public boolean isBalanceTree(Node<E> node) {
        if (node == null) return true;
        return (Math.abs(height1(node.left) - height1(node.right)) <= 1) && isBalanceTree(node.left) && isBalanceTree(node.right);
    }


    /**
     * 求前驱结点
     * 前驱结点：中序遍历的前一个节点
     *
     * @param node 节点
     * @return 该节点的前驱结点
     */
    @SuppressWarnings("unused")
    protected Node<E> predecessor(Node<E> node) {
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
     * @param node 查找的节点
     * @return 前驱节点
     */

    protected Node<E> successor(Node<E> node) {
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

    protected void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");//抛出一个非法参数异常
        }
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


}
