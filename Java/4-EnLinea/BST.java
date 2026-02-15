import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> {
    private class Node {
        K key;
        ArrayList<V> values;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.values = new ArrayList<>();
            this.values.add(value);
        }
    }

    private Node root;

    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, K key, V value) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(node.left, key, value);
        else if (cmp > 0) node.right = insert(node.right, key, value);
        else if (!node.values.contains(value)) {
            node.values.add(value);
        }
        return node;
    }

    public ArrayList<V> get(K key) {
        Node node = get(root, key);
        return (node != null) ? node.values : new ArrayList<>();
    }

    private Node get(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return get(node.left, key);
        else if (cmp > 0) return get(node.right, key);
        else return node;
    }

    public ArrayList<V> range(K lo, K hi) {
        ArrayList<V> result = new ArrayList<>();
        range(root, lo, hi, result);
        return result;
    }

    private void range(Node node, K lo, K hi, ArrayList<V> result) {
        if (node == null) return;
        int cmpLo = lo.compareTo(node.key);
        int cmpHi = hi.compareTo(node.key);
        if (cmpLo < 0) range(node.left, lo, hi, result);
        if (cmpLo <= 0 && cmpHi >= 0) result.addAll(node.values);
        if (cmpHi > 0) range(node.right, lo, hi, result);
    }

    public ArrayList<V> successor(K key) {
        Node succ = null;
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                succ = node;
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return (succ != null) ? succ.values : new ArrayList<>();
    }
}
