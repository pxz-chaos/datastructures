package Day05.HashMap.map;


/**
 * @InterfaceName Map
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/12/19 19:19
 * @Version 1.0
 */
public interface Map<K, V> {
    int size();

    boolean isEmpty();

    void clear();

    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    void traversal(Visitor<K, V> visitor);

    public static abstract class Visitor<K, V> {
         public boolean stop;


        public abstract boolean visit(K key, V value);
    }
}
