package Day05.HashMap.map;


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
@SuppressWarnings({"unchecked", "unused"})
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
        //???????????
        if (root == null) {
            // root = new Node<>(key, null);//?????????????????и?????
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        //??????????????
        //????????
        Node<K, V> temp = root;//?????????????????????α??????????????
        Node<K, V> parent = root;//?????????????
        int cmp = 0;//?????????
        while (temp != null) {

            //???б??
            //cmp= ((Comparable<E>) key).compareTo(temp.key);//?д???????????????????

            cmp = compare(key, temp.key);//???????????????????0??-1,1
            parent = temp;//??????????????????????λ??,???д??????д??while????У????????????????????
            if (cmp > 0) {//??????????????????????????
                temp = temp.right;//??????????????temp=temp.next;
            } else if (cmp < 0) {//С????
                temp = temp.left;
            } else {//??????
                //??????????????
                temp.key = key;
                V oldValue = temp.value;
                temp.value = value;
                return oldValue;
            }

        }

        //?????????node???null?????????????????????????parent???
        //???????????????????λ??
        // Node<E> newNode = new Node<>(key, parent);
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        afterPut(newNode);
        size++;
        return null;
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {// private Compactor<E> compactor;//???????????????compactor??????????????????
            return comparator.compare(k1, k2);

        }
        //????????,????????????????????????????????Comparable<E>???
        return ((Comparable<K>) k1).compareTo(k2);
    }


    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        if (parent == null) {//??????????
            black(node);//????????????
            return;
        }
        //????????????????????????????????????????????
        if (isBlack(parent)) return;

        //???????????????????????????????????????

        Node<K, V> uncle = parent.sibling(); //uncle???
        Node<K, V> grand = red(parent.parent); //grand???

        //???常???????ж?
        if (isRed(uncle)) {//?常?????????????????????
            // ??parent,uncle?????,???grand???????????????????????????飬grand???????????
            black(parent);
            black(uncle);
            afterPut(grand);
            return;
        }

        //?常???????,????????????????
        //1.LL/RR-->??????????????????游???
        //2.LR/RL-->?????????????游??????????????????????
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {//LL
                black(parent);//??????????

            } else {//LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);//????????????????????
        }
        else {
            if (node.isLeftChild()) {//RL
                black(node);
                rotateRight(parent);
            } else {//RR
                black(parent);
            }
            rotateLeft(grand);
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
        //??????????????????????????null;
        return node;
    }

    @Override
    public V remove(K key) {
        return remove(findNode(key));
    }

    /**
     * ???????д?????????
     *
     * @param node ?????????????
     */

    private V remove(Node<K, V> node) {
        if (node == null) return null;

        V oldValue = node.value;
        if (node.hasTwoChildren()) { // ???2????
            // ????????
            Node<K, V> sucNode = successor(node);
            // ?ú????????????2??????
            node.key = sucNode.key;
            node.value = sucNode.value;
            // ????????
            node = sucNode;

        }

        // ???node???node???????1????0??
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node????1????
            // ????parent
            replacement.parent = node.parent;
            // ????parent??left??right?????
            if (node.parent == null) { // node????1????????????
                root = replacement;
            } else if (node == node.parent.left) {//???????
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            //???????????????????????????????
            afterRemove(node);

        } else if (node.parent == null) { // node???????????????
            root = null;
            afterRemove(node);
        } else { // node?????????????????
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
        // ??????????????
        // ???? ??????????????????????
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        // ???????????
        if (parent == null) return;

        // ?????????????????硿
        // ?ж???????node????????
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // ?????????????????????????
            if (isRed(sibling)) { // ?????????
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // ???????
                sibling = parent.right;
            }

            // ???????????
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // ????????1???????????????????????????
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // ???????????1????????????????????
                // ????????????????????????
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // ?????????????????????????
            if (isRed(sibling)) { // ?????????
                black(sibling);
                red(parent);
                rotateRight(parent);
                // ???????
                sibling = parent.left;
            }

            // ???????????
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // ????????1???????????????????????????
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // ???????????1????????????????????
                // ????????????????????????
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
     * ????????
     * ??????????????????????
     *
     * @param node ???
     * @return ???????????
     */
    @SuppressWarnings("unused")
    private Node<K, V> predecessor(Node<K, V> node) {
        //????????????????????????
        //??????????????1,2,3,4,5,6,7,8
        //7???????????6
        if (node == null) return null;

        // ?????????????????У?left.right.right.right....??
        Node<K, V> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        //??????????????node.left==null
        // ???????游??????????????

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;


    }

    /**
     * ???????
     * ???????????????????????
     *
     * @param node ???????
     * @return ??????
     */

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // ?????????????????У?right.left.left.left....??
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // ???????游??????????????
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
        //??????????????value?????value?????п??????????????key???????
        //??????????ò??????
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
        //???????????
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        //???????????

        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);

        if (visitor.stop) return;
        visitor.visit(node.key, node.value);

        traversal(node.right, visitor);

    }

    //????ú??м?????????
    @SuppressWarnings("unused")
    public static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;//??????
        Node<K, V> right;//??????
        Node<K, V> parent;//?????

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {//?????????
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//????????????
            return right != null & left != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * ?????????
         *
         * @return ???????????о?????
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;//????????
        }


    }

    /**
     * ??????????
     *
     * @param node  ???????????
     * @param color ??????????
     * @return ???????
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;//??
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
            throw new IllegalArgumentException("key must not be null");//???????????????
        }
    }

    /**
     * ????
     *
     * @param grand ????????????????
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> p = grand.right;//???p
        Node<K, V> child = p.left;//???T1
        //??????
        grand.right = child;
        p.left = grand;

        //????????

        //?????p??????
        p.parent = grand.parent;

        //??????????grand?????????????????????p???
        if (grand.isLeftChild()) {//grand????????
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand????????
            grand.parent.right = p;
        } else {//grand???????????
            root = p;
        }

        //????child??parent
        if (child != null) child.parent = grand;

        //????grand???parent???
        grand.parent = p;


    }

    /**
     * ????
     *
     * @param grand ????????????????
     */
    // java.util.TreeMap
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> p = grand.left;//???p
        Node<K, V> child = p.right;//???T1
        //??????
        grand.left = child;
        p.right = grand;

        //????????

        //?????p??????
        p.parent = grand.parent;

        //??????????grand?????????????????????p???
        if (grand.isLeftChild()) {//grand????????
            grand.parent.left = p;
        } else if (grand.isRightChild()) {//grand????????
            grand.parent.right = p;
        } else {//grand???????????
            root = p;
        }

        //????child??parent
        if (child != null) child.parent = grand;
        grand.parent = p;
        //??????
        afterRotate(grand);
        afterRotate(p);

    }

    private void afterRotate(Node<K, V> node) {
        //????????????????
    }

}
