package Day08Trie.Trie;

import java.util.HashMap;
/*
Trie的优点：搜索前缀的效率主要跟前缀的长度有关
Trie的缺点，需要耗费大量的内存，因为一个单词就是一个节点，所以需要改进。
更多的Trie相关的数据结构和算法
Double-array Trie 、Suffix Trie、Patricia Trie、Crit-bit Tree、AC自动机
 */

/**
 * Trie也叫字典树、前缀树、单词查找树
 * Trie搜索字符串的效率主要跟字符串的长度有关
 *
 * @param <V> 存储单词所对应的值
 */
public class Trie<V> implements Trie02<V> {
    private int size;

    private Node<V> root;

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
        size = 0;
        root = null;
    }

    @Override
    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.word;
    }

    @Override
    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }


    @Override
    public V add(String key, V value) {

        keyCheck(key);
        //创建根节点
        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;//需要children的时候才会创建children
                node.children.put(c, childNode);
            }
            node = childNode;
        }

        if (node.word) { // 已经存在这个单词
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 新增一个单词
        node.word = true;
        node.value = value;
        size++;
        return null;
    }


    @Override
    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    @Override
    public V remove(String key) {
        //找到最后一个节点
        Node<V> node = node(key);

        //如果不是单词结尾

        if (node == null || !node.word) {//node为空或者不是以这个单词结尾的
            return null;//不需要处理
        }
        // 必须要删除
        size--;
        V oldValue = node.value;
        //如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {


            node.word = false;
            node.value = null;
            return oldValue;
        }

        //没有子节点，倒着删
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;//往前面走
        }
        return oldValue;
    }

    private Node<V> node(String key) {

        keyCheck(key);
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            char c = key.charAt(i);//d o g
            node = node.children.get(c);//找root对应的子节点

        }

        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be null");
        }

    }

    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character;
        V value;
        boolean word;//是否为单词的结尾（是否为一个完整的单词）


        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
