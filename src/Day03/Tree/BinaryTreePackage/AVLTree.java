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
     * ��д���֮�����Ĳ���������������������AVL(ƽ�������)
     *
     * @param node ����ӵĽڵ�
     */
    @Override
    protected void afterAdd(Node<E> node) {
        //��Ҫ�ж�����ӵĽڵ㵽�����ĸ�λ�ã�������ҵ�������������������˫��
        //ʧ��Ľڵ�ֻ�����游�ڵ㼰�����ϵĽڵ�
        //node==node.parent.parent...
        while ((node = node.parent) != null) {//node��parent��Ϊ�գ�Ҳ����node��Ϊ���ڵ�
            //�ж�node�Ƿ�ƽ��
            if (isBalanceTree(node)) {
                //���¸߶�
                //��������ƽ��ģ�˳�����һ�¸߶ȣ�����ÿ�ζ�����height�������¸߶�
                // ����Ϊheight�����еݹ飬�����Ӻ�ʱ
                updateHeight(node);
            } else {
                //�ָ�ƽ��
                rebalanced(node);
                //�ָ������Ժ��˳�ѭ������
                break;
            }
        }
    }

    private void updateHeight(Node<E> node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;//ǿתһ��
        avlNode.updateHeight();

    }

    /**
     * ����ĵ�������
     * grandдȫ�Ժ����grandparentNode
     *
     * @param grand �߶���͵��Ǹ���ƽ��ڵ㣬������үү�����
     */
    private void rebalanced(Node<E> grand) {//�õ�g
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();//�õ�p
        Node<E> node = ((AVLNode<E>) parent).tallerChild();//�õ�n


        if (parent.isLeftChild()) {//p��g�����
            if (node.isLeftChild()) {//LL
                // rotateRight(grand);//��үү�ڵ��������
                rotateR(grand);//��үү�ڵ��������
            } else {//LR
                //������p,������g
              /*  rotateLeft(parent);
                rotateRight(grand);*/
                rotateL(parent);
                rotateR(grand);
            }
        } else {//p��g���ұ�
            if (node.isLeftChild()) {//RL
                //������p,������g
//                rotateRight(parent);
//                rotateLeft(grand);
                rotateR(parent);
                rotateL(grand);

            } else {//RR
                // rotateLeft(grand);//����
                rotateL(grand);
            }
        }
    }

    /**
     * ����ͳһ����ת��ʽ���Ͳ�����������ת��
     *
     * @param grand ���ڵ㣨�游��
     */
    private void rebalanced2(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();//�õ�p
        Node<E> node = ((AVLNode<E>) parent).tallerChild();//�õ�n


        if (parent.isLeftChild()) {//p��g�����
            if (node.isLeftChild()) {//LL
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else {//LR
                rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        } else {//p��g���ұ�
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
     * @param node ��ɾ���Ľڵ�
     */
    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {//node��parent��Ϊ�գ�Ҳ����node��Ϊ���ڵ�
            //�ж�node�Ƿ�ƽ��
            if (isBalanceTree(node)) {
                //���¸߶�
                //��������ƽ��ģ�˳�����һ�¸߶ȣ�����ÿ�ζ�����height�������¸߶�
                // ����Ϊheight�����еݹ飬�����Ӻ�ʱ
                updateHeight(node);
            } else {
                //�ָ�ƽ��
                rebalanced(node);
                //�ָ������Ժ��˳�ѭ������
            }
        }
    }

    /**
     * @param node ����ֱ�Ӵ�����ڵ�
     *             ��֮ǰд��ʹ���˵ݹ���㷨����ʦ���÷ǵݹ���㷨�����Ծ���дһ����
     * @return �Ƿ���ƽ�������
     */
    @Override
    public boolean isBalanceTree(Node<E> node) {
        return Math.abs(((AVLNode<E>) node).balanceFactor()) <= 1;
    }
// java.util.TreeMap

    public static class AVLNode<E> extends Node<E> {
        int height = 1;//ά��һ���߶ȣ�����������

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * ����ڵ��ƽ������
         *
         * @return ƽ������, Ҳ�������������ĸ߶�֮��
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        /**
         * �������ĸ߶�
         */
        public void updateHeight() {
            //��ǰ���õĵݹ�ķ�ʽʵ�ֵģ�����û��ʹ�õݹ��ԭ������Ϊ��ʹ����height����
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;//�����߶ȸ��ߣ���������
            if (leftHeight < rightHeight) return right;//�����߶ȸ��ߣ���������
            return isLeftChild() ? left : right;//��ȣ������λ������������ߣ����������������򷵻�������
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
     * �����½ڵ�
     *
     * @param element �ڵ������
     * @param parent  ���ڵ�
     * @return �µ�һ���ڵ�
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

}
