package Day04.Set;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName ListSet
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/17 15:27
 * @Version 1.0
 */
/*
�����������ķ�ʽʵ�ּ��ϣ�
 */
public class ListSet<E> implements Set<E> {
    private List<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
//        if (list.contains(element)) return;
//        list.add(element);
        int index = list.indexOf(element);
        if (index != -1) {//˵������
            list.set(index, element);//���ǵ���ǰ������
        }
        else {
            list.add(element);
        }

    }

    @Override
    public void remove(E element) {
        //list.remove(element);
        int index = list.indexOf(element);
        if (index != -1) {//˵������
            list.remove(index);//���ǵ���ǰ������
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;
        for (int i = 0; i < list.size(); i++) {
            if (visitor.visit(list.get(i))) return;
        }
    }

    public static void main(String[] args) {

    }

}
