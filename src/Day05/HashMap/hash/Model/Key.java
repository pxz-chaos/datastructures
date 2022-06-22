package Day05.HashMap.hash.Model;

public class Key {
    protected int value;

    public Key(int key) {
        this.value = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key1 = (Key) o;
        return value == key1.value;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
