import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
        }
    }

    public  BSTMap() {
        this.clear();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    private V getHelper(Node x, K key) {
        if (x == null) {
            return null;
        }
        int difference = key.compareTo(x.key);
        if (difference < 0) {
            return getHelper(x.left, key);
        } else if (difference > 0) {
            return getHelper(x.right, key);
        } else {
            return x.value;
        }
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    private Node putHelper(Node x, K key, V value) {
        if (x == null) {
            size += 1;
            return new Node(key, value,  1);
        }
        int difference = key.compareTo(x.key);
        if (difference < 0) {
            x.left = putHelper(x.left, key, value);
        } else if (difference > 0) {
            x.right = putHelper(x.right, key, value);
        } else {
            x.value = value;

        }
        return x;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
