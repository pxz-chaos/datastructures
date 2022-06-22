package Day03.Tree.BinaryTreePackage;

import java.util.Comparator;

/**
 * @ClassName AVL
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/9 10:53
 * @Version 1.0
 */
public class AVL<E> extends BST<E> {

    public AVL(Comparator<E> comparator) {
        super(comparator);
    }

    public AVL() {
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
                rebalanced2(node);
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

    /**
     * @param r ���ڵ�
     * @param a ��С�Ľڵ�
     * @param b ��֮
     * @param c ��֮
     * @param d ��ת֮��ĸ��ڵ�
     * @param e ��֮
     * @param f ��֮
     * @param g ���Ľڵ�
     */
    private void rotate(Node<E> r,
                        Node<E> a, Node<E> b, Node<E> c,
                        Node<E> d,
                        Node<E> e, Node<E> f, Node<E> g) {
        //����d��Ϊ��������ĸ��ڵ�
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }
        //a-b-c,����ע��a��c����Ϊ�գ�������
        b.left = a;
        if (a != null) a.parent = b;
        b.right = c;
        if (c != null) c.parent = b;
        updateHeight(b);

        //e-f-g��������
        f.left = e;
        if (e != null) e.parent = f;
        f.right = g;
        if (g != null) g.parent = f;
        updateHeight(f);

        //b-d-f�����Ӹ��ڵ�
        d.left = b;
        b.parent = d;
        d.right = f;
        f.parent = d;
        updateHeight(d);
    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    private void rotateLeft(Node<E> grand) {
        Node<E> p = grand.right;//�õ�p
        Node<E> child = p.left;//�õ�T1
        //�޸�ָ��
        grand.right = child;
        p.left = grand;

        //ά�����ڵ�

        //�ȸ���p�ĸ��ڵ�
        p.parent = grand.parent;

        //����Ҫ����ǰgrand�ĸ��ڵ��������ָ�����ڵ�p�ڵ�
        if (grand.isLeftChild()) {//grand��������
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand��������
            grand.parent.right = p;
        } else {//grandֻ���Ǹ��ڵ���
            root = p;
        }

        //����child��parent
        if (child != null) child.parent = grand;

        //����grand�ĸ�parent�ڵ�
        grand.parent = p;

        //���¸߶�
        updateHeight(grand);
        updateHeight(p);
    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    // java.util.TreeMap
    private void rotateRight(Node<E> grand) {
        Node<E> p = grand.left;//�õ�p
        Node<E> child = p.right;//�õ�T1
        //�޸�ָ��
        grand.left = child;
        p.right = grand;

        //ά�����ڵ�

        //�ȸ���p�ĸ��ڵ�
        p.parent = grand.parent;

        //����Ҫ����ǰgrand�ĸ��ڵ��������ָ�����ڵ�p�ڵ�
        if (grand.isLeftChild()) {//grand��������
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand��������
            grand.parent.right = p;
        } else {//grandֻ���Ǹ��ڵ���
            root = p;
        }

        //����child��parent
        if (child != null) child.parent = grand;

        //����grand�ĸ�parent�ڵ�
        grand.parent = p;

        //���¸߶�
        updateHeight(grand);
        updateHeight(p);

    }

    private void rotateL(Node<E> p) {
        if (p != null) {
            Node<E> r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;

            if (p.parent == null) {
                root = r;
            } else if (p == p.parent.left) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
            updateHeight(p);
            updateHeight(r);
        }

    }

    private void rotateR(Node<E> p) {
        if (p != null) {
            Node<E> l = p.left;
            p.left = l.right;

            if (l.right != null) {
                l.right.parent = p;
            }

            l.parent = p.parent;

            if (p.parent == null) {
                root = l;
            } else if (p == p.parent.left) {
                p.parent.left = l;
            } else {
                p.parent.right = l;
            }
            l.right = p;
            p.parent = l;
            updateHeight(p);
            updateHeight(l);
        }
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
                rebalanced2(node);
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
