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
�������Ľڵ㲻��Ϊ��
ǰ�к������
������ǿ�����λ��
����ǰ������ǰ�������������
�����У�������������������
���ں󣬾��Ǻ�����������Ҹ�

ע�⣺����û��˳�����Һ���Ҳ�ǿ��Ե�
 */
@SuppressWarnings("unchecked")
public class BST<E> extends BinaryTree<E> implements Tree<E> {
    private Comparator<E> compactor;//��һ���Ƚ���

    public BST(Comparator<E> comparator) {//��һ���Ƚ���
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
        //��ӵĵ�һ���ڵ�
        if (root == null) {
            // root = new Node<>(element, null);//��һ���ڵ㣨���ڵ㣩��û�и��ڵ��
            root = createNode(element, null);
            size++;
            afterAdd(root);
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
            if (cmp > 0) {//�����㣬�����������ظ�Ԫ�صĲ���
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
     * ��ӽڵ�֮��Ĳ�������Ҫ��ά�������AVL��
     *
     * @param node ����ӵĽڵ�
     */
    protected void afterAdd(Node<E> node) {

    }

    /**
     * ɾ���ڵ�֮��Ĳ�������Ҫ��ά�������AVL��
     *
     * @param node ��ɾ���Ľڵ�
     */
    protected void afterRemove(Node<E> node) {

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

            //�ȵ��Ǹ��ڵ�������ɾ���Ժ�����ƽ�����
            afterRemove(node);

        } else if (node.parent == null) { // node��Ҷ�ӽڵ㲢���Ǹ��ڵ�
            root = null;
            afterRemove(node);
        } else { // node��Ҷ�ӽڵ㣬�����Ǹ��ڵ�
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
        return node/*node != null ? node :null*/;
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
     * �Ƿ�Ϊ��ȫ������
     *
     * @return �Ƕ���������true������Ϊfalse
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
     * @param e1 �ڵ��ֵ
     * @param e2 ������ڵ��ֵ
     * @return ����ֵ����0������e1����e2������ֵ�����㣬����e1����e2������ֵС���㣬����e1С��e2
     */
    private int compare(E e1, E e2) {

        if (compactor != null) {// private Compactor<E> compactor;//��һ���Ƚ��������compactor��Ϊ�գ��������ñȽ���
            return compactor.compare(e1, e2);

        }
        //�Ƚ���Ϊ��,��ô������������ǿɱȽϵġ���ô��������Comparable<E>�ӿ�
        return ((Comparable<E>) e1).compareTo(e2);
    }

}



