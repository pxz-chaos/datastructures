package Day04.Set;

import Day03.Tree.BinaryTreePackage.AVL;
import Day03.Tree.BinaryTreePackage.AVLTree;
import Day03.Tree.BinaryTreePackage.RBTree;

import java.util.Comparator;

/**
 * @ClassName TreeSet
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 15:47
 * @Version 1.0
 */
/*
采用红黑树来实现set集合
其实官方实现使用map实现的set集合 java.util.TreeSet，其中的value设置为空，我们自己实现一把
 */
public class TreeSet<E> implements Set<E> {
    private RBTree<E> rbt = new RBTree<>();

    public TreeSet() {
        this(null);
    }

    public TreeSet(Comparator<E> comparator) {
        rbt = new RBTree<>(comparator);
    }

    //    private AVLTree<E> rbt = new AVLTree<>();
    @Override
    public int size() {
        return rbt.size();
    }

    @Override
    public boolean isEmpty() {
        return rbt.isEmpty();
    }

    @Override
    public void clear() {
        rbt.clear();
    }

    @Override
    public boolean contains(E element) {
        return rbt.contains(element);
    }

    @Override
    public void add(E element) {
        rbt.add(element);//当时写的二叉搜索树的时候就已经去重了，也实现了覆盖掉原来是的内容
    }

    @Override
    public void remove(E element) {
        rbt.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {

        //老师建议从小到大遍历
        rbt.inorderTraversal(new Day03.Tree.BinaryTreePackage.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }

}
