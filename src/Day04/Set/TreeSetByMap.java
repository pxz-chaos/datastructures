package Day04.Set;

import Day05.HashMap.map.Map;

/**
 * @ClassName TreeSetByMap
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/20 9:48
 * @Version 1.0
 */
public class TreeSetByMap<E> implements Set<E> {
    private TreeMap<E, Object> map = new TreeMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
        map.put(element, null);
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });
    }
}
