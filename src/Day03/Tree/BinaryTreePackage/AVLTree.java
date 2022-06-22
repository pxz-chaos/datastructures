package Day03.Tree.BinaryTreePackage;

import java.util.Comparator;

/**
 * @ClassName AVL
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/9 10:53
 * @Version 1.0
 */
public class AVLTree<E> extends BBST<E> {
    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    public AVLTree() {
        this(null);
    }

    /**
     * 重写添加之后做的操作，这个操作仅仅针对于AVL(平衡二叉树)
     *
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        //需要判断新添加的节点到底在哪个位置，这决定我到底是左旋，右旋还是双旋
        //失衡的节点只能是祖父节点及其以上的节点
        //node==node.parent.parent...
        while ((node = node.parent) != null) {//node的parent不为空，也就是node不为根节点
            //判断node是否平衡
            if (isBalanceTree(node)) {
                //更新高度
                //发现你是平衡的，顺便更新一下高度，不用每次都调用height方法更新高度
                // ，因为height里面有递归，会增加耗时
                updateHeight(node);
            } else {
                //恢复平衡
                rebalanced(node);
                //恢复完了以后退出循环即可
                break;
            }
        }
    }

    private void updateHeight(Node<E> node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;//强转一波
        avlNode.updateHeight();

    }

    /**
     * 最核心的内容了
     * grand写全以后就是grandparentNode
     *
     * @param grand 高度最低的那个不平衡节点，至少是爷爷级别的
     */
    private void rebalanced(Node<E> grand) {//拿到g
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();//拿到p
        Node<E> node = ((AVLNode<E>) parent).tallerChild();//拿到n


        if (parent.isLeftChild()) {//p在g的左边
            if (node.isLeftChild()) {//LL
                // rotateRight(grand);//将爷爷节点进行右旋
                rotateR(grand);//将爷爷节点进行右旋
            } else {//LR
                //先左旋p,再右旋g
              /*  rotateLeft(parent);
                rotateRight(grand);*/
                rotateL(parent);
                rotateR(grand);
            }
        } else {//p在g的右边
            if (node.isLeftChild()) {//RL
                //先右旋p,再左旋g
//                rotateRight(parent);
//                rotateLeft(grand);
                rotateR(parent);
                rotateL(grand);

            } else {//RR
                // rotateLeft(grand);//左旋
                rotateL(grand);
            }
        }
    }

    /**
     * 采用统一的旋转方式，就不区分左右旋转了
     *
     * @param grand 根节点（祖父）
     */
    private void rebalanced2(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();//拿到p
        Node<E> node = ((AVLNode<E>) parent).tallerChild();//拿到n


        if (parent.isLeftChild()) {//p在g的左边
            if (node.isLeftChild()) {//LL
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else {//LR
                rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        } else {//p在g的右边
            if (node.isLeftChild()) {//RL
                rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);

            } else {//RR
                rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    @Override
    protected void afterRotate(Node<E> node){
        updateHeight(node);
    }

    /**
     * @param node 待删除的节点
     */
    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {//node的parent不为空，也就是node不为根节点
            //判断node是否平衡
            if (isBalanceTree(node)) {
                //更新高度
                //发现你是平衡的，顺便更新一下高度，不用每次都调用height方法更新高度
                // ，因为height里面有递归，会增加耗时
                updateHeight(node);
            } else {
                //恢复平衡
                rebalanced(node);
                //恢复完了以后退出循环即可
            }
        }
    }

    /**
     * @param node 可以直接传入根节点
     *             我之前写的使用了递归的算法，老师采用非递归的算法，所以就重写一下呗
     * @return 是否是平衡二叉树
     */
    @Override
    public boolean isBalanceTree(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }
// java.util.TreeMap

    public static class AVLNode<E> extends Node<E> {
        int height = 1;//维持一个高度，子类新增的

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 计算节点的平衡因子
         *
         * @return 平衡因子, 也就是左右子树的高度之差
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新树的高度
         */
        public void updateHeight() {
            //以前是用的递归的方式实现的，现在没有使用递归的原因是因为我使用了height属性
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;//左树高度更高，返回左树
            if (leftHeight < rightHeight) return right;//右树高度更高，返回右树
            return isLeftChild() ? left : right;//相等，如果我位于整棵树的左边，返回左子树，否则返回右子树
        }
        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

    /**
     * 创建新节点
     *
     * @param element 节点的内容
     * @param parent  父节点
     * @return 新的一个节点
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

}
