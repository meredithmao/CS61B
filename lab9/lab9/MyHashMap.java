package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private Set<K> keySet;
    private int capacity;
    private double loadFactor;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        this.size = 0;
        this.capacity = DEFAULT_SIZE;
        this.loadFactor = MAX_LF;
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.keySet = new HashSet<K>();
        this.clear();
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.size = 0;
        this.capacity = initialSize;
        this.loadFactor = loadFactor;
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.keySet = new HashSet<K>();
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
//        throw new UnsupportedOperationException();
//        int hashIndex = this.hashCode(key);
        ArrayMap<K, V> chain = this.buckets[DEFAULT_SIZE];
        V value = chain.get(key);
        return value;

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
//        throw new UnsupportedOperationException();
        ArrayMap<K, V> chain = this.buckets[DEFAULT_SIZE];
        chain.put(key, value);
        keySet.add(key);
        this.size +=1;

        double fillFactor = (double) this.size/ DEFAULT_SIZE;
        if (fillFactor > this.MAX_LF) {
            this.resize();
        }
    }

    private void resize() {
        int resizedCapacity = DEFAULT_SIZE * 100;

        MyHashMap<K, V> resizedMap = new MyHashMap<K, V>(resizedCapacity, MAX_LF);

        for (K key: this) {
            V value = this.get(key);
            resizedMap.put(key, value);
        }
        this.capacity = resizedMap.capacity;
        this.buckets = resizedMap.buckets;
        this.loadFactor = resizedMap.loadFactor;

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
//        throw new UnsupportedOperationException();
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
//        throw new UnsupportedOperationException();
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
