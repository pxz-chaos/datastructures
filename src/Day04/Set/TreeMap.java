package Day04.Set;


import Day05.HashMap.map.Map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName TreeMap
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/19 19:29
 * @Version 1.0
 */
@SuppressWarnings({"unchecked","unused"})
class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private int size;
    private Node<K, V> root;
    private final Comparator comparator;

    public TreeMap(Comparator comparator) {
        this.comparator = comparator;
    }

    public TreeMap() {
        this(null);
    }

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
    public V put(K key, V value) {

        keyNotNullCheck(key);
        //��ӵĵ�һ���ڵ�
        if (root == null) {
            // root = new Node<>(key, null);//��һ���ڵ㣨���ڵ㣩��û�и��ڵ��
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        //��ӵĲ��ǵ�һ���ڵ�
        //�ҵ����ڵ�
        Node<K, V> temp = root;//��ʱ�ڵ㣬������ָ����ڵ㣬ÿ�α������Ӹ��ڵ����
        Node<K, V> parent = root;//������ָ����ڵ�
        int cmp = 0;//��¼�ȽϷ���
        while (temp != null) {

            //���бȽ�
            //cmp= ((Comparable<E>) key).compareTo(temp.key);//�д�����������ֿɱȽϵ�Ԫ��

            cmp = compare(key, temp.key);//��¼�ȽϺ�Ľ��������᷵��0��-1,1
            parent = temp;//Ҳ���Ǽ���������Ľڵ��ǰһ��λ��,���д������д��whileѭ���У���Ȼ���ڵ���Զ������¡�
            if (cmp > 0) {//�����㣬�����������ظ�Ԫ�صĲ���
                temp = temp.right;//���ƣ��൱����ǰ��temp=temp.next;
            } else if (cmp < 0) {//С����
                temp = temp.left;
            } else {//������
                //ѡ�񸲸ǵ�ԭ��������
                temp.key = key;
                V oldValue = temp.value;
                temp.value = value;
                return oldValue;
            }

        }

        //�˳�ѭ���Ժ�node��Ϊnull�ˣ���ʱ�ĸ��ڵ�����ǰ�����ˣ�Ϊparent�ڵ�
        //�������뵽���ڵ����һ��λ��
        // Node<E> newNode = new Node<>(key, parent);
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp >= 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        afterPut(newNode);
        size++;
        return null;
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {// private Compactor<E> compactor;//��һ���Ƚ��������compactor��Ϊ�գ��������ñȽ���
            return comparator.compare(k1, k2);

        }
        //�Ƚ���Ϊ��,��ô������������ǿɱȽϵġ���ô��������Comparable<E>�ӿ�
        return ((Comparable<K>) k1).compareTo(k2);
    }


    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        if (parent == null) {//��ӵ��Ǹ��ڵ�
            black(node);//ֱ�ӽ��ڵ�Ⱦ�ɺ�ɫ
            return;
        }
        //������ڵ��Ǻ�ɫ����ֱ�ӷ��أ���Ϊ����Ĭ����ӵ���ɫΪ��ɫ
        if (isBlack(parent)) return;

        //����������ģ����Ǹ��ڵ�Ϊ��ɫ���ͻ����˫������


        Node<K, V> uncle = parent.sibling(); //uncle�ڵ�
        Node<K, V> grand = red(parent.parent); //grand�ڵ�

        //���常�ڵ�����ж�
        if (isRed(uncle)) {//�常�ڵ��Ǻ�ɫ��������������
            // ��parent,uncleȾΪ��ɫ,�ٽ�grandȾ�ɺ�ɫ������Ϊ����ӵĽڵ㴦�����õݹ飬grand������Ϻϲ���
            black(parent);
            black(uncle);
//            red(grand);
            afterPut(grand);
            return;
        }

        if (isBlack(uncle)) {
            //�常�ڵ��Ǻ�ɫ,�����������Ҫ����
            //1.LL/RR-->���ڵ�Ⱦ�ɺ�ɫ���ٵ����游�ڵ�
            //2.LR/RL-->���Լ�Ⱦ�ɺ�ɫ���游�ڵ�Ⱦ�ɺ�ɫ���ٽ���˫������
            if (parent.isLeftChild()) {
                if (node.isLeftChild()) {//LL
                    black(parent);//���ڵ�Ⱦ�ɺ�ɫ
                    // rotateRight(grand);//�������游�ڵ�

                } else {//LR
                    black(node);
                    //red(grand);
                    rotateLeft(parent);
                    //rotateRight(grand);
                }
                rotateRight(grand);//����Ҫ��ת��ֱ����ȡ����
            }
            if (parent.isRightChild()) {
                if (node.isLeftChild()) {//RL
                    black(node);
                    // red(grand);
                    rotateRight(parent);
                    // rotateLeft(grand);
                } else {//RR
                    black(parent);
                    // rotateLeft(grand);//�������游�ڵ�
                }
                rotateLeft(grand);
            }

        }
    }


    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return findNode(key).value;
    }

    private Node<K, V> findNode(K key) {
        Node<K, V> node = root;
        while (node != null && node.key != key) {
            node = compare(node.key, key) > 0 ? node.left : node.right;
        }
        //�˳�ѭ���Ժ�û���ҵ�����ôֱ�ӷ���null;
        return node;
    }

    @Override
    public V remove(K key) {
        return remove(findNode(key));
    }

    /**
     * ������Ҫд��ɾ������
     *
     * @param node ��ɾ�����Ǹ��ڵ�
     */

    private V remove(Node<K, V> node) {
        if (node == null) return null;

        V oldValue = node.value;
        if (node.hasTwoChildren()) { // ��Ϊ2�Ľڵ�
            // �ҵ���̽ڵ�
            Node<K, V> sucNode = successor(node);
            // �ú�̽ڵ��ֵ���Ƕ�Ϊ2�Ľڵ��ֵ
            node.key = sucNode.key;
            node.value = sucNode.value;
            // ɾ����̽ڵ�
            node = sucNode;

        }

        // ɾ��node�ڵ㣨node�Ķȱ�Ȼ��1����0��
        Node<K, V> replacement = node.left != null ? node.left : node.right;

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
        return oldValue;
    }


    private void afterRemove(Node<K, V> node) {
        // ���ɾ���Ľڵ��Ǻ�ɫ
        // ���� ����ȡ��ɾ���ڵ���ӽڵ��Ǻ�ɫ
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        // ɾ�����Ǹ��ڵ�
        if (parent == null) return;

        // ɾ�����Ǻ�ɫҶ�ӽڵ㡾���硿
        // �жϱ�ɾ����node��������
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
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
     * ��ǰ�����
     * ǰ����㣺���������ǰһ���ڵ�
     *
     * @param node �ڵ�
     * @return �ýڵ��ǰ�����
     */
    @SuppressWarnings("unused")
    private Node<K, V> predecessor(Node<K, V> node) {
        //ǰ�������Ǹýڵ�����ҵ��Ǹ��ڵ�
        //�����������Ϊ��1,2,3,4,5,6,7,8
        //7��ǰ��������6
        if (node == null) return null;

        // ǰ���ڵ������������У�left.right.right.right....��
        Node<K, V> p = node.left;
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
     * @param node ���ҵĽڵ�
     * @return ǰ���ڵ�
     */

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // ǰ���ڵ������������У�right.left.left.left....��
        Node<K, V> p = node.right;
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

    @Override
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        //ֻ�ܱ���������ҵ�value����Ϊvalue�����пɱȽ��ԣ���������key����ȥ��
        //����������ò������
        if (root == null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valueEquals(node.value, value)) return true;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        return false;
    }

    private boolean valueEquals(V v1, V v2) {
        /* return v1 == null ? null : v2 == null ? null : v1.equals(v2);*/
        return (v1 == v2) || (v1 != null && v1.equals(v2));
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        //�����������
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        //�����������

        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);

        if (visitor.stop) return;
        visitor.visit(node.key, node.value);

        traversal(node.right, visitor);

    }

    //��һ�ú��м�ֵ�Եĺ����
    @SuppressWarnings("unused")
    public static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;//���ӽڵ�
        Node<K, V> right;//���ӽڵ�
        Node<K, V> parent;//���ڵ�

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {//�Ƿ�ΪҶ�ӽڵ�
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//�Ƿ��������ڵ�
            return right != null & left != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * �Ƿ�Ϊ�ֵܽڵ�
         *
         * @return �����ֵܽڵ㣬û�оͷ��ؿ�
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;//û���ֵܽڵ�
        }


    }

    /**
     * ����Ⱦɫ����
     *
     * @param node  ���뽫ҪȾɫ�Ľڵ�
     * @param color ��ҪȾ�ɵ���ɫ
     * @return Ⱦɫ��Ľڵ�
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;//Ⱦɫ
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");//�׳�һ���Ƿ������쳣
        }
    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> p = grand.right;//�õ�p
        Node<K, V> child = p.left;//�õ�T1
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


    }

    /**
     * ����
     *
     * @param grand ��Ҫ��������ת�Ľڵ�
     */
    // java.util.TreeMap
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> p = grand.left;//�õ�p
        Node<K, V> child = p.right;//�õ�T1
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


        //���¸߶�
        afterRotate(grand);
        afterRotate(p);

    }

    private void afterRotate(Node<K, V> node) {
//���������Ҫ���¸߶�
    }

}
