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
     * 左旋
     *
     * @param grand 需要进行左旋转的节点
     */
    protected void rotateLeft(Node<E> grand) {
        Node<E> p = grand.right;//拿到p
        Node<E> child = p.left;//拿到T1
        //修改指向
        grand.right = child;
        p.left = grand;

        //维护父节点

        //先更新p的父节点
        p.parent = grand.parent;

        //还需要让以前grand的父节点左或者右指向现在的p节点
        if (grand.isLeftChild()) {//grand是左子树
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand是右子树
            grand.parent.right = p;
        } else {//grand只能是根节点了
            root = p;
        }

        //更新child的parent
        if (child != null){ child.parent = grand;}

        //更新grand的父parent节点
        grand.parent = p;


    }

    /**
     * 右旋
     *
     * @param grand 需要进行右旋转的节点
     */
    // java.util.TreeMap
    protected void rotateRight(Node<E> grand) {
        Node<E> p = grand.left;//拿到p
        Node<E> child = p.right;//拿到T1
        //修改指向
        grand.left = child;
        p.right = grand;

        //维护父节点

        //先更新p的父节点
        p.parent = grand.parent;

        //还需要让以前grand的父节点左或者右指向现在的p节点
        if (grand.isLeftChild()) {//grand是左子树
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand是右子树
            grand.parent.right = p;
        } else {//grand只能是根节点了
            root = p;
        }

        //更新child的parent
        if (child != null) child.parent = grand;
        grand.parent = p;

        //更新高度
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
            //更新父节点
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
            //更新高度
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
            //更新高度
            afterRotate(node);
            afterRotate(l);
        }
    }
    /**
     * @param r 根节点
     * @param a 最小的节点
     * @param b 次之
     * @param c 次之
     * @param d 旋转之后的根节点
     * @param e 次之
     * @param f 次之
     * @param g 最大的节点
     */
    protected void rotate(Node<E> r,
                        Node<E> a, Node<E> b, Node<E> c,
                        Node<E> d,
                        Node<E> e, Node<E> f, Node<E> g) {
        //先让d成为这颗子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }
        //a-b-c,但是注意a和c可能为空，左子树
        b.left = a;
        if (a != null) a.parent = b;
        b.right = c;
        if (c != null) c.parent = b;
        //updateHeight(b);
        afterRotate(b);//更新高度
        //e-f-g，右子树
        f.left = e;
        if (e != null) e.parent = f;
        f.right = g;
        if (g != null) g.parent = f;
        afterRotate(f);

        //b-d-f，连接根节点
        d.left = b;
        b.parent = d;
        d.right = f;
        f.parent = d;
        afterRotate(d);
    }

    protected void afterRotate(Node<E> node) {
        //让子类去实现高度的更新，只有AVL才需要更新高度
    }
}
