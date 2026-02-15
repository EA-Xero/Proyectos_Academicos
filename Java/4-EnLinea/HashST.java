import java.util.LinkedList;

public class HashST<K, V> {
    private static final int INIT_CAPACITY = 16;
    private LinkedList<Node<K, V>>[] table;

    private static class Node<K, V> {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public HashST() {
        table = new LinkedList[INIT_CAPACITY];
        for (int i = 0; i < INIT_CAPACITY; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % INIT_CAPACITY;
    }

    public void put(K key, V value) {
        int i = hash(key);
        for (Node<K, V> node : table[i]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        table[i].add(new Node<>(key, value));
    }

    public V get(K key) {
        int i = hash(key);
        for (Node<K, V> node : table[i]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }
}
