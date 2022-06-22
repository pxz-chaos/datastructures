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
        //添加的第一个节点
        if (root == null) {
            // root = new Node<>(key, null);//第一个节点（根节点）是没有父节点的
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
            return null;
        }

        //添加的不是第一个节点
        //找到父节点
        Node<K, V> temp = root;//临时节点，先让其指向根节点，每次遍历都从根节点出发
        Node<K, V> parent = root;//先让其指向根节点
        int cmp = 0;//记录比较方向
        while (temp != null) {

            //进行比较
            //cmp= ((Comparable<E>) key).compareTo(temp.key);//行代码针对于那种可比较的元素

            cmp = compare(key, temp.key);//记录比较后的结果，这里会返回0，-1,1
            parent = temp;//也就是即将被插入的节点的前一个位置,这行代码必须写在while循环中，不然父节点永远不会更新。
            if (cmp > 0) {//大于零，这是允许有重复元素的操作
                temp = temp.right;//后移，相当于以前的temp=temp.next;
            } else if (cmp < 0) {//小于零
                temp = temp.left;
            } else {//等于零
                //选择覆盖掉原来的内容
                temp.key = key;
                V oldValue = temp.value;
                temp.value = value;
                return oldValue;
            }

        }

        //退出循环以后，node就为null了，此时的父节点我提前保存了，为parent节点
        //看看插入到父节点的哪一个位置
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
        if (comparator != null) {// private Compactor<E> compactor;//传一个比较器，如果compactor不为空，则优先用比较器
            return comparator.compare(k1, k2);

        }
        //比较器为空,那么你这个参数就是可比较的。那么就优先用Comparable<E>接口
        return ((Comparable<K>) k1).compareTo(k2);
    }


    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        if (parent == null) {//添加的是根节点
            black(node);//直接将节点染成黑色
            return;
        }
        //如果父节点是黑色，就直接返回，因为我们默认添加的颜色为红色
        if (isBlack(parent)) return;

        //能来到下面的，都是父节点为红色，就会出现双红现象


        Node<K, V> uncle = parent.sibling(); //uncle节点
        Node<K, V> grand = red(parent.parent); //grand节点

        //对叔父节点进行判断
        if (isRed(uncle)) {//叔父节点是红色，属于上溢现象
            // ，parent,uncle染为黑色,再将grand染成红色，当做为新添加的节点处理，采用递归，grand完成向上合并。
            black(parent);
            black(uncle);
//            red(grand);
            afterPut(grand);
            return;
        }

        if (isBlack(uncle)) {
            //叔父节点是黑色,有两种情况需要处理
            //1.LL/RR-->父节点染成黑色，再单旋祖父节点
            //2.LR/RL-->将自己染成黑色，祖父节点染成红色。再进行双旋操作
            if (parent.isLeftChild()) {
                if (node.isLeftChild()) {//LL
                    black(parent);//父节点染成黑色
                    // rotateRight(grand);//再右旋祖父节点

                } else {//LR
                    black(node);
                    //red(grand);
                    rotateLeft(parent);
                    //rotateRight(grand);
                }
                rotateRight(grand);//都需要旋转，直接提取出来
            }
            if (parent.isRightChild()) {
                if (node.isLeftChild()) {//RL
                    black(node);
                    // red(grand);
                    rotateRight(parent);
                    // rotateLeft(grand);
                } else {//RR
                    black(parent);
                    // rotateLeft(grand);//再左单旋祖父节点
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
        //退出循环以后还没有找到，那么直接返回null;
        return node;
    }

    @Override
    public V remove(K key) {
        return remove(findNode(key));
    }

    /**
     * 真正需要写的删除代码
     *
     * @param node 被删除的那个节点
     */

    private V remove(Node<K, V> node) {
        if (node == null) return null;

        V oldValue = node.value;
        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> sucNode = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = sucNode.key;
            node.value = sucNode.value;
            // 删除后继节点
            node = sucNode;

        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {//删除左节点
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            //等到那个节点真正被删除以后再做平衡调整
            afterRemove(node);

        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;
            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
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
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
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
     * 求前驱结点
     * 前驱结点：中序遍历的前一个节点
     *
     * @param node 节点
     * @return 该节点的前驱结点
     */
    @SuppressWarnings("unused")
    private Node<K, V> predecessor(Node<K, V> node) {
        //前驱结点就是该节点的最右的那个节点
        //比如中序遍历为，1,2,3,4,5,6,7,8
        //7的前驱结点就是6
        if (node == null) return null;

        // 前驱节点在左子树当中（left.right.right.right....）
        Node<K, V> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        //能来到这里的就是node.left==null
        // 从父节点、祖父节点中寻找前驱节点

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        return node.parent;


    }

    /**
     * 寻找后继节点
     * 后驱节点：中序遍历的后一个节点
     *
     * @param node 查找的节点
     * @return 前驱节点
     */

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
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
        //只能遍历红黑树找到value，因为value不具有可比较性，不能像找key那样去找
        //所以这里采用层序遍历
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
        //采用中序遍历
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        //采用中序遍历

        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);

        if (visitor.stop) return;
        visitor.visit(node.key, node.value);

        traversal(node.right, visitor);

    }

    //来一棵含有键值对的红黑树
    @SuppressWarnings("unused")
    public static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;//左子节点
        Node<K, V> right;//右子节点
        Node<K, V> parent;//父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {//是否为叶子节点
            return right == null & left == null;
        }

        public boolean hasTwoChildren() {//是否有两个节点
            return right != null & left != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 是否为兄弟节点
         *
         * @return 返回兄弟节点，没有就返回空
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;//没有兄弟节点
        }


    }

    /**
     * 进行染色操作
     *
     * @param node  传入将要染色的节点
     * @param color 需要染成的颜色
     * @return 染色后的节点
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;//染色
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
            throw new IllegalArgumentException("key must not be null");//抛出一个非法参数异常
        }
    }

    /**
     * 左旋
     *
     * @param grand 需要进行左旋转的节点
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> p = grand.right;//拿到p
        Node<K, V> child = p.left;//拿到T1
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
        if (child != null) child.parent = grand;

        //更新grand的父parent节点
        grand.parent = p;


    }

    /**
     * 右旋
     *
     * @param grand 需要进行右旋转的节点
     */
    // java.util.TreeMap
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> p = grand.left;//拿到p
        Node<K, V> child = p.right;//拿到T1
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


        //更新高度
        afterRotate(grand);
        afterRotate(p);

    }

    private void afterRotate(Node<K, V> node) {
//红黑树不需要更新高度
    }

}
