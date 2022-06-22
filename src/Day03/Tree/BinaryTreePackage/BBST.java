package Day03.Tree.BinaryTreePackage;


import java.util.Comparator;

/**
 * @ClassName BBST
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/16 10:54
 * @Version 1.0
 */
public class BBST<E> extends BST<E> {
    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    protected void rotateLeft(Node<E> grand) {
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
        if (child != null){ child.parent = grand;}

        //����grand�ĸ�parent�ڵ�
        grand.parent = p;


    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    // java.util.TreeMap
    protected void rotateRight(Node<E> grand) {
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
        grand.parent = p;

        //���¸߶�
        afterRotate(grand);
        afterRotate(p);

    }

    protected void rotateL(Node<E> node) {
        if (node != null) {
           Node<E> r = node.right;
            node.right = r.left;
            if (r.left != null) {
                r.left.parent = node;
            }
            //���¸��ڵ�
            r.parent = node.parent;

            if (node.parent == null) {
                root = r;
            } else if (node == node.parent.left) {
                node.parent.left = r;
            } else {
                node.parent.right = r;
            }
            r.left = node;
            node.parent = r;
            //���¸߶�
            afterRotate(node);
            afterRotate(r);
        }

    }

    protected void rotateR(Node<E> node) {
        if (node != null) {
           Node<E> l = node.left;
            node.left = l.right;

            if (l.right != null) {
                l.right.parent = node;
            }

            l.parent = node.parent;

            if (node.parent == null) {
                root = l;
            } else if (node == node.parent.left) {
                node.parent.left = l;
            } else {
                node.parent.right = l;
            }
            l.right = node;
            node.parent = l;
            //���¸߶�
            afterRotate(node);
            afterRotate(l);
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
    protected void rotate(Node<E> r,
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
        //updateHeight(b);
        afterRotate(b);//���¸߶�
        //e-f-g��������
        f.left = e;
        if (e != null) e.parent = f;
        f.right = g;
        if (g != null) g.parent = f;
        afterRotate(f);

        //b-d-f�����Ӹ��ڵ�
        d.left = b;
        b.parent = d;
        d.right = f;
        f.parent = d;
        afterRotate(d);
    }

    protected void afterRotate(Node<E> node) {
        //������ȥʵ�ָ߶ȵĸ��£�ֻ��AVL����Ҫ���¸߶�
    }
}
