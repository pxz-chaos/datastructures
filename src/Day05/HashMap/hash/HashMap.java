package Day05.HashMap.hash;

import Day05.HashMap.map.Map;
import Day05.HashMap.printer.BinaryTreeInfo;
import Day05.HashMap.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@SuppressWarnings({"unchecked", "rawtypes"})
public class HashMap<K, V> implements Map<K, V> {
    //既然是采用数组实现的，那么就需要有数组，其中数组的数据呢，
    // 我们直接采用红黑树结构，为了方便起见，直接把节点存入到数组中[node,node1,node2...]
    private int size;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;//装填因子：节点总数量/哈希表桶数组长度，也叫负载因子
//    在JDK1.8的hashmap中，如果装填因子超过0.75，就扩容为原来的2倍。
//需要扩容的地方在put处

    //来一个数组
    private Node<K, V>[] table;
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 默认容量为 16，主要是把默认容量设置为2^n

    public HashMap() {
        table = new Node[DEFAULT_INITIAL_CAPACITY];
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
        //这里采用table.length而不是size的原因在于，size是指的是有效数据长度，是红黑树节点个数的总和，
        // 我是想把所有数据都清空
        if (size == 0) return;//没必要去清空了，直接返回即可
        for (int i = 0; i < table.length; i++) {
            table[i] = null;//掐除红黑树的头节点
        }
        size = 0;
    }

    /**
     * 添加的思路
     * 首先要确定添加的位置，也就是先确定索引
     * 因此需要生成索引
     *
     * @param key   添加的键
     * @param value 添加的值
     * @return 返回添加之前的value值
     */
    @Override
    /*public V put(K key, V value) {
        resize();
        int index = index(key);//获得索引

        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];

        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;

            //添加完毕后需要修复红黑树
            fixAfterPut(root);
            return null;//因为是新建的节点，所以不存在覆盖现象，返回null就行了
        }
        //这个根节点如果不为空,出现了哈希冲突
        //因此需要形成红黑树

        //添加的不是第一个节点
        //1.找到父节点
        Node<K, V> node = root;//临时节点，先让其指向根节点，每次遍历都从根节点出发
        Node<K, V> parent = root;//先让其指向根节点
        int cmp = 0;//记录比较方向

        K k1 = key;
        int h1 = k1 == null ? 0 : k1.hashCode();

        Node<K, V> result = null;
        boolean searched = false;//是否已经搜索过key
        while (node != null) {

            //2.进行比较
            //cmp= ((Comparable<E>) key).compareTo(temp.key);//行代码针对于那种可比较的元素
//            cmp = compare(key, node.key, h1, node.hash);//记录比较后的结果，这里会返回0，-1,1
            parent = node;//也就是即将被插入的节点的前一个位置,这行代码必须写在while循环中，不然父节点永远不会更新。

            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {
                // node = node.right;
                //上面代码替换为
                cmp = 1;//会自动执行  node = node.right;
            } else if (h1 < h2) {
                // node = node.left;
                cmp = -1;
            }
            //hash值相等
            else if (Objects.equals(k1, k2)) {
                //覆盖
                cmp = 0;
            }

            //k1,k2是否具备可比较性
            else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()//类型相同
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {//具备可比较性
             *//*   这儿有一个bug，举个例子
                如果一个对象具有可比较性，他自己实现了比较规则，那么
                cmp = ((Comparable) k1).compareTo(k2)一定会出现三种情况>0,<0,=0
                这里等于0的时候，就会跳转到
               “ else {//等于零
                //选择覆盖掉原来的内容
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;//覆盖的时候需要返回被覆盖的内容”这行代码，也就是覆盖，
                但是比较大小相等并不意味着equals，因此不能进行覆盖
                解决办法：&&(cmp = ((Comparable) k1).compareTo(k2))!=0 添加一个条件，他们相等的时候，不做任何处理
            }
                 *//*
                // cmp = ((Comparable) k1).compareTo(k2);
            }
            //k1,k2是不具备可比较性
            else if (searched == false) {
                //采用递归
                //扫描左右子树是否包含了被添加的数据，如果找到了，直接覆盖。
                // 否则就随便插入到左右子树，根据内存地址值的大小插入
                if ((node.left != null && (result = findNode(node.left, k1)) != null)
                        || (node.right != null && (result = findNode(node.right, k1)) != null)) {

                    //已经存在这个k，覆盖result
                    node = result;
                    cmp = 0;
                } else {//bu存在这个k
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            } else {//serached==true;
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            //3.
            if (cmp > 0) {//大于零，这是允许有重复元素的操作
                node = node.right;//后移，相当于以前的temp=temp.next;
            } else if (cmp < 0) {//小于零
                node = node.left;
            } else {//等于零
                //选择覆盖掉原来的内容
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;//覆盖的时候需要返回被覆盖的内容
            }

        }

        //退出循环以后，node就为null了，此时的父节点我提前保存了，为parent节点
        //看看插入到父节点的哪一个位置
        // Node<E> newNode = new Node<>(key, parent);
        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        fixAfterPut(newNode);
        size++;

        return null;

    }*/
    public V put(K key, V value) {
        resize();

        int index = index(key);
        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = createNode(key, value, null);
            table[index] = root;
            size++;
            fixAfterPut(root);
            return null;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K, V> result = null;
        boolean searched = false; // 是否已经搜索过这个key
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else if (searched) { // 已经扫描了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { // searched == false; 还没有扫描，然后再根据内存地址大小决定左右
                if ((node.left != null && (result = findNode(node.left, k1)) != null)
                        || (node.right != null && (result = findNode(node.right, k1)) != null)) {
                    // 已经存在这个key
                    node = result;
                    cmp = 0;
                } else { // 不存在这个key
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = createNode(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        // 新添加节点之后的处理
        fixAfterPut(newNode);
        return null;
    }

    private int hash(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        //让子类去实现代码
    }

    private void resize() {
        //负载因子<=0.75；
        if (size / table.length <= DEFAULT_LOAD_FACTOR) return;//不需要扩容；
        Node<K, V>[] oldTable = table;//保留以前的table，也就是扩容之前的table
        table = new Node[oldTable.length << 1];
        //注意：扩容之后的计算的索引值会发生改变
        //将旧的的table中的内容搞到新的table中
        /*
         * 当扩容为原来的2倍时，节点的索引有两种情况：
         * 1.保持不变
         * 2.index=index+旧容量
         *
         * */
//        将所有元素挪到table中，这里采用层序遍历。
        Queue<Node<K, V>> queue = new LinkedList<>();

        for (int i = 0; i < oldTable.length; i++) {
//            oldTable[i]是一棵树根节点
            if (oldTable[i] == null) continue;//如果为空，遍历下一棵红黑树

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                //先把左右子树入队，然后再挪动节点，因为挪动节点的时候，需要重置，
                // 所以为了保证node.left不为空，就将  moveNode(node)方法置于后面
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);

                //挪动节点
                moveNode(node);
            }
        }

    }

    /**
     * 挪动节点
     *
     * @param newNode 被挪动的节点
     */
    private void moveNode(Node<K, V> newNode) {
        //重置：喝一碗孟婆汤，让以前的红黑树忘记左右子树和父节点
        newNode.parent = null;
        newNode.right = null;
        newNode.left = null;
        newNode.color = RED;

        int index = index(newNode);//获得索引

        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];

        if (root == null) {
            root = newNode;
            table[index] = root;

            //添加完毕后需要修复红黑树
            fixAfterPut(root);
            return;
        }
        //这个根节点如果不为空,出现了哈希冲突
        //因此需要形成红黑树

        //添加的不是第一个节点
        //1.找到父节点
        Node<K, V> node = root;//临时节点，先让其指向根节点，每次遍历都从根节点出发
        Node<K, V> parent = root;//先让其指向根节点
        int cmp = 0;//记录比较方向

        K k1 = newNode.key;
        int h1 = newNode.hash;

        Node<K, V> result = null;
        boolean searched = false;//是否已经搜索过key
        while (node != null) {

            //2.进行比较
            //cmp= ((Comparable<E>) key).compareTo(temp.key);//行代码针对于那种可比较的元素
//            cmp = compare(key, node.key, h1, node.hash);//记录比较后的结果，这里会返回0，-1,1
            parent = node;//也就是即将被插入的节点的前一个位置,这行代码必须写在while循环中，不然父节点永远不会更新。

            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {
                // node = node.right;
                //上面代码替换为
                cmp = 1;//会自动执行  node = node.right;
            } else if (h1 < h2) {
                // node = node.left;
                cmp = -1;
            }

            //k1,k2是否具备可比较性
            else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()//类型相同
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // cmp = ((Comparable) k1).compareTo(k2);
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            //3.
            if (cmp > 0) {//大于零，这是允许有重复元素的操作
                node = node.right;//后移，相当于以前的temp=temp.next;
            } else if (cmp < 0) {//小于零
                node = node.left;
            }

        }

        //退出循环以后，node就为null了，此时的父节点我提前保存了，为parent节点
        //看看插入到父节点的哪一个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        fixAfterPut(newNode);
    }

    /**
     * 比较key的大小
     *
     * @param k1 当前节点的key值
     * @param k2 传入节点的key值
     * @param h1 当前节点的key值对应的hash值
     * @param h2 传入节点的key值对应的hash值
     * @return 0,-1,1
     */
    private int compare(K k1, K k2, int h1, int h2) {
        //出现了哈希冲突以后，我们既然需要有比较性才能把key添加到红黑树中
        // ，往往hashMap中的key呢又不需要具备可比较性，一种做法是我们采用key的hashCode用来比较
        // ，大的放红黑树的右边，小的放左边
//        int h1 = k1 == null ? 0 : k1.hashCode();
//        int h2 = k2 == null ? 0 : k2.hashCode();
        //每次传进来都需要进行哈希值的计算，比较耗时间，我们直接写在节点中去，给节点构造方法中计算出对应key的hash值

        //这里会出现一个bug，有可能 int h1, int h2这两个hash值是相等的，返回0，不代表key是相同的，所以还需要进一步判断

        //1.比较哈希值
        int result = h1 - h2;//也有bug，万一h1为正数h2为一个其绝对值很大的负数，result就可能溢出，溢出后可能就为负数了，此时result<0
        //所以用减法不靠谱
        //情况1
        if (result != 0) return result;

        //情况2
        //比较equals

        if (Objects.equals(k1, k2)) return 0;

        //能来到这里，说明hash值相等了，但是他们的类型相同吗？
        //情况3
        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {//为了简化问题，让K1和K2属于同一种类型的,并且具有可比较性
            // (判断k1是否具备可比较性，看它是否实现comparable接口)
            //比较类名
           /* String k1Cls = k1.getClass().getName();//采用反射机制
            String k2Cls = k2.getClass().getName();//采用反射机制
            result = k1Cls.compareTo(k2Cls);
            if (result != 0) {
                //说明不同类     3.1
                return result;//直接按照类名来比较大小
            }*/
            if (k1 instanceof Comparable) return ((Comparable) k1).compareTo(k2);
            //能来到这里，意味着是用一种类型
            if (k1 instanceof Comparable) {
                //k1具备可比较性  3.2
                return result;
            }
        }
        //情况4
        //k1,k2是同一种类型，哈希值也相等,但是不具备可比较性
        //k1!=null,k2==null 不空  hash地址值也可能相等
        //k1==null,k2!=null

        //我们就采用内存地址值做减

        return System.identityHashCode(k1) - System.identityHashCode(k2);//这句代码有bug
    }


    @Override
    public V get(K key) {
        Node<K, V> node = findNode(key);

        return node == null ? null : node.value;
    }

    /**
     * 根据key找value
     *
     * @param key 传入的key
     * @return key对应的节点
     */
    private Node<K, V> findNode1(K key) {
        //第一步还是要找到自己所在的索引位置
        int index = index(key);
        Node<K, V> root = table[index];//拿出红黑树的根节点

        //然后遍历往下找
        Node<K, V> node = root;
        int h1 = key == null ? null : key.hashCode();
        while (node != null) {
            int cmp = compare(key, node.key, h1, node.hash);
            if (cmp == 0) return node;
            if (cmp > 0) node = node.right;
            else if (cmp < 0) {
                node = node.left;
            }
        }
        //退出循环后还没有找到，那就返回null
        return null;
    }

    /**
     * 根据key找value
     * 修正
     *
     * @param key 传入的key
     * @return key对应的节点
     */

    private Node<K, V> findNode(K key) {
        Node<K, V> root = table[index(key)];//拿到根节点
        return root == null ? null : findNode(root, key);
    }

    private Node<K, V> findNode(Node<K, V> node, K k1) {

        int h1 = k1 == null ? null : k1.hashCode();
        //存储查找结果
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            int h2 = node.hash;//节点的hash值
            K k2 = node.key;
            //先比较hash值
            if (h1 > h2) {
                //往右边找
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            }
            //主要是判断2个条件，1.hash值等否 2.内容等否
//                                          2.1 同类型否
//                                          2.2 可比较否
            //h1==h2哈希值相等，现在肯定出现了哈希冲突了
            else if (Objects.equals(k1, k2)) {//hash值等 and 内容等--> 找到了
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0
            ) {//哈希值相等, 内容bu等 具备可比较性

              /*  if (cmp > 0) {
                    node = node.right;
                } else if (cmp < 0) {
                    node = node.left;
                }*/
                node = cmp > 0 ? node.right : node.left;
            }
            //哈希值相等,内容bu等 不具备可比较性,zai增加一种情况，也就是cmp=0
            //现在不可以用内存地址来比较大小，来决定往左往右了，之前已经出现过bug了
            //这里采用递归查找
            else if (node.right != null && (result = findNode(node.right, k1)) != null) {
                return result;
            } else {//只能往左边找
                node = node.left;
            }
           /* else if (node.left != null && (result = findNode(node.left, k1)) != null) {
                return result;
            } else {
                return null;
            }*/
        }
        //退出循环后还没有找到，那就返回null
        return null;

    }

    @Override
    public V remove(K key) {

        return remove(findNode(key));
    }

   /* protected V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;
        size--;

        V oldValue = node.value;
        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> sucNode = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = sucNode.key;
            node.value = sucNode.value;
            //以上代码没有覆盖完全，所以需要再添加hash的覆盖
            node.hash = sucNode.hash;
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
                // root=replacement;
                table[index(node)] = replacement;
            } else if (node == node.parent.left) {//删除左节点
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            //等到那个节点真正被删除以后再做平衡调整
            fixAfterRemove(node);

        } else if (node.parent == null) { // node是叶子节点并且是根节点
            //  root=null;
            table[index(node)] = null;
            fixAfterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }
            fixAfterRemove(node);
        }

        return oldValue;
    }*/
   protected V remove(Node<K, V> node) {
       if (node == null) return null;

       Node<K, V> willNode = node;

       size--;

       V oldValue = node.value;

       if (node.hasTwoChildren()) { // 度为2的节点
           // 找到后继节点
           Node<K, V> s = successor(node);
           // 用后继节点的值覆盖度为2的节点的值
           node.key = s.key;
           node.value = s.value;
           node.hash = s.hash;
           // 删除后继节点
           node = s;
       }

       // 删除node节点（node的度必然是1或者0）
       Node<K, V> replacement = node.left != null ? node.left : node.right;
       int index = index(node);

       if (replacement != null) { // node是度为1的节点
           // 更改parent
           replacement.parent = node.parent;
           // 更改parent的left、right的指向
           if (node.parent == null) { // node是度为1的节点并且是根节点
               table[index] = replacement;
           } else if (node == node.parent.left) {
               node.parent.left = replacement;
           } else { // node == node.parent.right
               node.parent.right = replacement;
           }

           // 删除节点之后的处理
           fixAfterRemove(replacement);
       } else if (node.parent == null) { // node是叶子节点并且是根节点
           table[index] = null;
       } else { // node是叶子节点，但不是根节点
           if (node == node.parent.left) {
               node.parent.left = null;
           } else { // node == node.parent.right
               node.parent.right = null;
           }

           // 删除节点之后的处理
           fixAfterRemove(node);
       }

       // 交给子类去处理
       afterRemove(willNode, node);

       return oldValue;
   }
    private void fixAfterRemove(Node<K, V> node) {
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();

        for (int i = 0; i < table.length; i++) {
//            table[i]是一棵树根节点
            if (table[i] == null) continue;//如果为空，遍历下一棵红黑树

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(node.value, value)) return true;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return false;
    }

    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();

        for (int i = 0; i < table.length; i++) {
//            table[i]是一棵树根节点
            if (table[i] == null) continue;//如果为空，遍历下一棵红黑树

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
    }

    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
            System.out.println("---------------------------------------------------");
        }

    }

    /**
     * 此方法用于根据key生成对应的索引
     *
     * @param key 给定一个key值
     * @return 索引，这个索引是经过hash计算得到的
     */
    private int index(K key) {

        if (key == null) return 0;

        int hashCode = key.hashCode();//得到哈希值
        hashCode = hashCode ^ (hashCode >>> 16);//高低32运算，主要是为了是hash值分布得更加均匀

        return hashCode & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            fixAfterPut(grand);
            return;
        }

        // 叔父节点不是红色
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
            table[index(grand)] = p;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
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
            table[index(grand)] = p;
        }

        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = p;
        //更新高度
        afterRotate(grand);
        afterRotate(p);

    }

    private void afterRotate(Node<K, V> node) {
        //红黑树不需要更新高度
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

    //来一棵含有键值对的红黑树
    @SuppressWarnings("unused")
    public static class Node<K, V> {
        K key;
        V value;
        int hash;//hash值
        boolean color = RED;
        Node<K, V> left;//左子节点
        Node<K, V> right;//右子节点
        Node<K, V> parent;//父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
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


        @Override
        public String toString() {
            return "key=" + key + ", value=" + value;

        }
    }
}
