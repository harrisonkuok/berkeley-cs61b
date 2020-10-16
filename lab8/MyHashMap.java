import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private Entry<K, V>[] buckets;
    private int initialSize;
    private int load;
    private double loadFactor;
    private int size;

    public MyHashMap() {
        this.initialSize = 16;
        buckets = new Entry[initialSize];
        this.loadFactor = 0.75;
        size = 0;
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        buckets = new Entry[initialSize];
        this.loadFactor = 0.75;
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        buckets = new Entry[initialSize];
        this.loadFactor = loadFactor;
        size = 0;
    }

    private class Entry<K, V> {

        K key;
        V val;
        Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> n) {
            key = k;
            val = v;
            next = n;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void update(V value) {
            val = value;
        }
    }

    private int hash(K k) {
        return Math.floorMod(k.hashCode(), buckets.length);
    }

    @Override
    public void clear() {
        buckets = new Entry[initialSize];
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return get(key) != null;
    }

    private V getHelper(K key, Entry<K, V> n) {
        if (n == null) {
            return null;
        } else if (n.getKey().equals(key)) {
            return n.getVal();
        } else {
            return getHelper(key, n.getNext());
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int hash = hash(key);
        if (buckets[hash] == null) {
            return null;
        }
        return getHelper(key, buckets[hash]);
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        size = 0;
        load = 0;
        Entry<K, V>[] oldBuckets = buckets;
        buckets = new Entry[oldBuckets.length * 2];
        for (Entry<K, V> bucket : oldBuckets) {
            if (bucket == null) {
                continue;
            }
            while (bucket != null) {
                put(bucket.getKey(), bucket.getVal());
                bucket = bucket.getNext();
            }
        }
    }

    private void putHelper(K key, V value, Entry<K, V> n) {
        if (n.getKey().equals(key)) {
            n.update(value);
        } else if (n.getNext() == null) {
            n.next = new Entry<>(key, value, null);
            size += 1;
        } else {
            putHelper(key, value, n.getNext());
        }
    }

    @Override
    public void put(K key, V value) {
        if ((double)(load / buckets.length) > loadFactor) {
            resize();
        }
        int hash = hash(key);
        if (buckets[hash] == null) {
            buckets[hash] = new Entry<>(key, value, null);
            size += 1;
            load += 1;
        } else {
            putHelper(key, value, buckets[hash]);
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (Entry<K, V> bucket : buckets) {
            while (bucket != null) {
                keys.add(bucket.key);
                bucket = bucket.getNext();
            }
        }
        return keys;
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
        return keySet().iterator();
    }
}
