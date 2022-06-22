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
�������Ľڵ㲻��Ϊ��
ǰ�к������
������ǿ�����λ��
����ǰ������ǰ�������������
�����У�������������������
���ں󣬾��Ǻ�����������Ҹ�

ע�⣺����û��˳�����Һ���Ҳ�ǿ��Ե�
 */
public class BinarySearchTree<E> implements Tree<E>, BinaryTreeInfo {
    private Comparator<E> compactor;//��һ���Ƚ���
    private int size;

    public BinarySearchTree(Comparator<E> comparator) {//��һ���Ƚ���
        this.compactor = comparator;
    }

    public BinarySearchTree() {
        this.compactor = null;
    }

    private Node<E> root;//������һ�����ڵ㣬����Ĭ�Ͼ���null

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

        //��ӵĵ�һ���ڵ�
        if (root == null) {
            root = new Node<>(element, null);//��һ���ڵ㣨���ڵ㣩��û�и��ڵ��
            size++;
            return;
        }

        //��ӵĲ��ǵ�һ���ڵ�
        //�ҵ����ڵ�
        Node<E> temp = root;//��ʱ�ڵ㣬������ָ����ڵ㣬ÿ�α������Ӹ��ڵ����
        Node<E> parent = root;//������ָ����ڵ�
        int cmp = 0;//��¼�ȽϷ���
        while (temp != null) {

            //���бȽ�
            //cmp= ((Comparable<E>) element).compareTo(temp.element);//�д�����������ֿɱȽϵ�Ԫ��

            cmp = compare(element, temp.element);//��¼�ȽϺ�Ľ��������᷵��0��-1,1
            parent = temp;//Ҳ���Ǽ���������Ľڵ��ǰһ��λ��,���д������д��whileѭ���У���Ȼ���ڵ���Զ������¡�
            if (cmp > 0) {//������
                temp = temp.right;//���ƣ��൱����ǰ��temp=temp.next;
            } else if (cmp < 0) {//С����
                temp = temp.left;
            } else {//������
                //ѡ�񸲸ǵ�ԭ��������
                temp.element = element;
                return;
            }
        }

        //�˳�ѭ���Ժ�node��Ϊnull�ˣ���ʱ�ĸ��ڵ�����ǰ�����ˣ�Ϊparent�ڵ�
        //�������뵽���ڵ����һ��λ��
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
     * ������Ҫд��ɾ������
     *
     * @param node ��ɾ�����Ǹ��ڵ�
     */

    private void remove(Node<E> node) {
        if (node == null) return;

        if (node.hasTwoChildren()) { // ��Ϊ2�Ľڵ�
            // �ҵ���̽ڵ�
            Node<E> sucNode = successor(node);
            // �ú�̽ڵ��ֵ���Ƕ�Ϊ2�Ľڵ��ֵ
            node.element = sucNode.element;
            // ɾ����̽ڵ�
            node = sucNode;
        }

        // ɾ��node�ڵ㣨node�Ķȱ�Ȼ��1����0��
        Node<E> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node�Ƕ�Ϊ1�Ľڵ�
            // ����parent
            replacement.parent = node.parent;
            // ����parent��left��right��ָ��
            if (node.parent == null) { // node�Ƕ�Ϊ1�Ľڵ㲢���Ǹ��ڵ�
                root = replacement;
            } else if (node == node.parent.left) {//ɾ����ڵ�
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }
        } else if (node.parent == null) { // node��Ҷ�ӽڵ㲢���Ǹ��ڵ�
            root = null;
        } else { // node��Ҷ�ӽڵ㣬�����Ǹ��ڵ�
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
        }
        size--;
    }

    /**
     * �ȸ����Ǹ�Ԫ���ҵ��Ǹ��ڵ��λ��
     *
     * @param element ��ɾ����Ԫ��
     * @return ��ɾ���Ľڵ�
     */
    private Node<E> findNode(E element) {

        //����һ������ʦд��
       /*
        Node<E> node = root;
       while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) {
                //�ҵ���
                return node;
            }
            if (cmp > 0) {//��element > node.element
                node = node.right;//����
            } else {//cmp<0
                node = node.left;
            }
        }
        return null;

        */
        //���������Լ�д��
        Node<E> node = root;
        while (node != null && node.element != element) {
            node = compare(node.element, element) > 0 ? node.left : node.right;
        }
        //�˳�ѭ���Ժ�û���ҵ�����ôֱ�ӷ���null;
        return node != null ? node : null;
    }

    @Override
    public boolean contains(E element) {
//����һ�����õ����ķ���
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


        //��ʽ�������ò������

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
        //��ʽ3��ֱ�ӵ��÷���findNode(element)
        return findNode(element) != null;
    }

    /**
     * ǰ�����,���õݹ�,����>��->��
     */
    public void preorderTraversal() {
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        //��ӡ�ڵ�
        if (node == null) return;
        System.out.println(node.element);//����λ��
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /**
     * �Ľ����ǰ���������
     *
     * @param visitor ����ʽ�ӿ�
     */
    public void preorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        preorderTraversal(root, visitor);
    }

    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        //��ӡ�ڵ�
        if (node == null || visitor.stop) return;//visitor.stop=true�˾���ֹ����
        // System.out.println(node.element);//����λ��
        visitor.stop = visitor.visit(node.element);

        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    /**
     * �������,��->��->��
     */
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);//����λ��
        inorderTraversal(node.right);
    }

    /**
     * �Ľ�����������
     *
     * @param visitor
     */
    public void inorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inorderTraversal(root, visitor);
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;//visitor.stop=true��ֹͣ����
        inorderTraversal(node.left, visitor);
//        System.out.println(node.element);//����λ��
        if (visitor.stop) return;//��ֹ������������ʱ���Ѿ�������visitor.stop=true�ˣ���ӡ��û��ֹͣ������ͬ��

        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * �����������->��->��
     */
    public void postorderTraversal() {
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) return;

        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);//����λ��

    }

    public void postorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        postorderTraversal(root, visitor);
    }

    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
//        System.out.println(node.element);//����λ��
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);

    }
    /*
      ������������Ǵ������£�һ��һ�����������Ͳ��ܲ��õݹ���,������ö���
     ˼·��
      1.�Ƚ����ڵ����
      2.ѭ��ִ�����²�����ֱ������Ϊ��
        ����ͷ�ڵ�A���ӣ����з���
        ��A�����ӽڵ����
        ��A�����ӽڵ����
     */

    public void levelOrderTraversal() {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);//�Ƚ����ڵ����

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();//ȡ��ͷ�ڵ�
            System.out.println(node.element);//��ӡ

            if (node.left != null) {//ȡ����ͷ�ڵ���������ӽڵ㣬��ô�����
                queue.offer(node.left);
            }
            if (node.right != null) {//ȡ����ͷ�ڵ���������ӽڵ㣬��ô�����
                queue.offer(node.right);
            }

        }
    }

    public void levelOrderTraversal(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);//�Ƚ����ڵ����

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();//ȡ��ͷ�ڵ�
            // System.out.println(node.element);//֮ǰ�����Ǵ�ӡ
//            visitor.visit(node.element;//���Ԫ���õ�����ȥ�����Լ�ȥʵ���߼�
            if (visitor.visit(node.element) == true) return;//����������

            if (node.left != null) {//ȡ����ͷ�ڵ���������ӽڵ㣬��ô�����
                queue.offer(node.left);
            }
            if (node.right != null) {//ȡ����ͷ�ڵ���������ӽڵ㣬��ô�����
                queue.offer(node.right);
            }

        }
    }

    /**
     * �Ƿ�Ϊ��ȫ������
     *
     * @return
     */
    public boolean isComplete() {
        if (root == null) return false;//�����Ͳ���

        //�������һ��
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;//Ҷ�ӽڵ�ı��
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            //......��Ϊ1�Ľڵ�Ҫôֻ��һ����Ҫôһ����û��
            if (leaf && !node.isLeaf()) {//Ҫ����ΪҶ�ӽڵ㣬�����㲻��Ҷ�ӽڵ�
                return false;
            }

            /*if (node.hasTwoChildren()) {//���Ҿ���Ϊ�գ���ô���
                // ��������һ��bug��ֻ�����Ҷ����ڲ������
                // Ҳ����������Ͳ��������
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) return false;

            else {//���������������Ҷ�ӽڵ�
                //����������ľ���
                *//*node.left!=null&&node.right==null����node.left==null&&node.right==null*//*
                //�����޸�
                if (node.left != null) {
                    queue.offer(node.left);
                }
                leaf = true;

            }*/

            //�����Ż�
            if (node.left != null) {//��߲�Ϊ��
                queue.offer(node.left);
            }
            //���Ϊ��
            else if (node.right != null) {//���Ϊ�գ��ұ߲�Ϊ��
                return false;
            }
            if (node.right != null) {//�ұ߲�Ϊ��
                queue.offer(node.right);
            }
            //�ұ�Ϊ��,���Ϊ�ջ�����߲�Ϊ��
            else {
                leaf = true;
            }


        }
        return true;
    }

    /**
     * ���õ����ķ���
     * ������ò����������
     *
     * @return
     */
    public int height() {
        if (root == null) return 0;

        int height = 0;//��¼���ĸ߶�

        int levelSize = 1;//�洢ÿһ���Ԫ�ظ�����Ĭ����һ�����ڵ�

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;

            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);

            if (levelSize == 0) {//��ζ���Ѿ���������һ��Ԫ�أ�����������һ��Ԫ��
                levelSize = queue.size();//��һ���Ԫ������
                height++;//������һ��߶Ⱦ�+1
            }

        }

        return height;
    }

    /**
     * ���ڼ���������ĸ߶�
     * ����ĳ���ڵ�ĸ߶ȣ�������õݹ����ʽ
     *
     * @return
     */
    public int height1() {
        return height1(root);
    }

    /**
     * ����ĳ���ڵ�ĸ߶ȣ�������õݹ����ʽ
     *
     * @param node ����������ʵ�ĳ���ڵ�ĸ߶�
     * @return �߶�
     * ˼·�������ӽڵ�����߶�+1
     */
    public int height1(Node<E> node) {

        if (node == null) return 0;
        int height = Math.max(height1(node.left), height1(node.right)) + 1;
        return height;
    }

    /**
     * ��ǰ�����
     * ǰ����㣺���������ǰһ���ڵ�
     *
     * @param node �ڵ�
     * @return �ýڵ��ǰ�����
     */
    public Node<E> predecessor(Node<E> node) {
        //ǰ�������Ǹýڵ�����ҵ��Ǹ��ڵ�
        //�����������Ϊ��1,2,3,4,5,6,7,8
        //7��ǰ��������6
        if (node == null) return null;

        // ǰ���ڵ������������У�left.right.right.right....��
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        //����������ľ���node.left==null
        // �Ӹ��ڵ㡢�游�ڵ���Ѱ��ǰ���ڵ�

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;


    }

    /**
     * Ѱ�Һ�̽ڵ�
     * �����ڵ㣺��������ĺ�һ���ڵ�
     *
     * @param node
     * @return
     */
    public Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // ǰ���ڵ������������У�right.left.left.left....��
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // �Ӹ��ڵ㡢�游�ڵ���Ѱ��ǰ���ڵ�
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;

    }
//�����ĸ����������ڴ�ӡʹ�õ�

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
     * @return ����ֵ����0������e1����e2������ֵ�����㣬����e1����e2������ֵС���㣬����e1С��e2
     */
    private int compare(E e1, E e2) {

        if (compactor != null) {// private Compactor<E> compactor;//��һ���Ƚ��������compactor��Ϊ�գ��������ñȽ���
            return compactor.compare(e1, e2);

        }
        //�Ƚ���Ϊ��,��ô������������ǿɱȽϵġ���ô��������Comparable<E>�ӿ�
        return ((Comparable<E>) e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");//�׳�һ���Ƿ������쳣
        }
    }


    private static class Node<E> {
        E element;//Ԫ��
        Node<E> left;//���ӽڵ�
        Node<E> right;//���ӽڵ�
        Node<E> parent;//���ڵ�

        public Node(E element, Node<E> parent) {//����û�����ҽڵ㣬���Դ����ڵ�Ϳ�����
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {//�Ƿ�ΪҶ�ӽڵ�
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//�Ƿ��������ڵ�
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
//�Ż������߼����������һ���ӿڣ����ⲿʹ�����Զ�������ķ�ʽ���������Ǵ�ӡ


/**
 * �����࣬�����Զ��������֮ǰʹ�õ��Ǻ���ʽ�ӿ�
 *
 * @param <E>
 */
abstract class Visitor<E> {
    //void visit(E e);//����ֻ��һ�����󷽷������Կ���ʹ�ú���ʽ�ӿ�
    boolean stop = false;//���ڼ�¼����ʲôʱ����ֹ

    public abstract boolean visit(E e);//�ٴθĽ����Ľ���Ч���ǣ����������Ԫ�أ�����Ҫȫ�����������ж�����
}
