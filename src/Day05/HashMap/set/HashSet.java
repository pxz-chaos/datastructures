package Day05.HashMap.set;

import Day04.Set.Set;
import Day05.HashMap.hash.HashMap;
import Day05.HashMap.map.Map;


public class HashSet<E> implements Set<E> {
    private HashMap<E, Object> map = new HashMap<>();

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