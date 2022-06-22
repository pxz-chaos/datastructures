package Day08Trie.Trie;


public interface Trie02<V> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(String key);

    V add(String key,V value);

    V get(String key);

    boolean startsWith(String prefix);

    V remove(String key);

}
