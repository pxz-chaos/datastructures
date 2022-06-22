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
���ú������ʵ��set����
��ʵ�ٷ�ʵ��ʹ��mapʵ�ֵ�set���� java.util.TreeSet�����е�value����Ϊ�գ������Լ�ʵ��һ��
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
        rbt.add(element);//��ʱд�Ķ�����������ʱ����Ѿ�ȥ���ˣ�Ҳʵ���˸��ǵ�ԭ���ǵ�����
    }

    @Override
    public void remove(E element) {
        rbt.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {

        //��ʦ�����С�������
        rbt.inorderTraversal(new Day03.Tree.BinaryTreePackage.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }

}
