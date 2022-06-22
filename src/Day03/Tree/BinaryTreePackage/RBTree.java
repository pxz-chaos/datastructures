package Day03.Tree.BinaryTreePackage;

import java.util.Comparator;

/**
 * @ClassName RBTree
 * @Description class
 * @Author Zhang_Pei_xin
 * @Date 2021/12/9 10:53
 * @Version 1.0
 */
@SuppressWarnings("unused")
public class RBTree<E> extends BBST<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    public RBTree() {
        this(null);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // ��ӵ��Ǹ��ڵ� ���� ���絽���˸��ڵ�
        if (parent == null) {
            black(node);
            return;
        }

        // ������ڵ��Ǻ�ɫ��ֱ�ӷ���
        if (isBlack(parent)) return;

        // �常�ڵ�
        Node<E> uncle = parent.sibling();
        // �游�ڵ�
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) { // �常�ڵ��Ǻ�ɫ��B���ڵ����硿
            black(parent);
            black(uncle);
            // ���游�ڵ㵱��������ӵĽڵ�
            afterAdd(grand);
            return;
        }

        // �常�ڵ㲻�Ǻ�ɫ
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }

    }

    @Override
    protected void afterRemove(Node<E> node) {
        // ���ɾ���Ľڵ��Ǻ�ɫ
        // ���� ����ȡ��ɾ���ڵ���ӽڵ��Ǻ�ɫ
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<E> parent = node.parent;
        // ɾ�����Ǹ��ڵ�
        if (parent == null) return;

        // ɾ�����Ǻ�ɫҶ�ӽڵ㡾���硿
        // �жϱ�ɾ����node��������
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { // ��ɾ���Ľڵ�����ߣ��ֵܽڵ����ұ�
            if (isRed(sibling)) { // �ֵܽڵ��Ǻ�ɫ
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // �����ֵ�
                sibling = parent.right;
            }

            // �ֵܽڵ��Ȼ�Ǻ�ɫ
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // �ֵܽڵ�û��1����ɫ�ӽڵ㣬���ڵ�Ҫ���¸��ֵܽڵ�ϲ�
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // �ֵܽڵ�������1����ɫ�ӽڵ㣬���ֵܽڵ��Ԫ��
                // �ֵܽڵ������Ǻ�ɫ���ֵ�Ҫ����ת
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // ��ɾ���Ľڵ����ұߣ��ֵܽڵ������
            if (isRed(sibling)) { // �ֵܽڵ��Ǻ�ɫ
                black(sibling);
                red(parent);
                rotateRight(parent);
                // �����ֵ�
                sibling = parent.left;
            }

            // �ֵܽڵ��Ȼ�Ǻ�ɫ
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // �ֵܽڵ�û��1����ɫ�ӽڵ㣬���ڵ�Ҫ���¸��ֵܽڵ�ϲ�
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // �ֵܽڵ�������1����ɫ�ӽڵ㣬���ֵܽڵ��Ԫ��
                // �ֵܽڵ������Ǻ�ɫ���ֵ�Ҫ����ת
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    /**
     * ����Ⱦɫ����
     *
     * @param node  ���뽫ҪȾɫ�Ľڵ�
     * @param color ��ҪȾ�ɵ���ɫ
     * @return Ⱦɫ��Ľڵ�
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;//Ⱦɫ
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private static class RBNode<E> extends Node<E> {
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return str + element.toString() + "_p(" + parentString + ")";
        }

    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }
}



