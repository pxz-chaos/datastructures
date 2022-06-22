package Day08Trie.Trie;

public interface Trie01 {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(String key);

    String add(String key);

    String get(String key);

    boolean startsWith(String prefix);

}
