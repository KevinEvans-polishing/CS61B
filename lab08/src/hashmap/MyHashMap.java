package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Hector
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    // 哈希表的桶
    private Collection<Node>[] buckets;
    // 存储Node的数量 -- 相当于N
    private int size;
    // 当前的容量 -- 相当于M
    private int capacity;
    // 增量因子
    private double loadFactor;
    // 增量倍数
    private static final int FACTOR = 2;

    /** Constructors */
    public MyHashMap() {
        capacity = 16;
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        loadFactor = 0.75;
    }

    public MyHashMap(int initialCapacity) {
        capacity = initialCapacity;
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void put(K key, V value) {
        // 不允许key为空值
        if (key != null) {
            // 如果key存在则替换value
            for (Collection<Node> c : buckets) {
                for (Node node : c) {
                    if (node.key.equals(key)) {
                        node.value = value;
                        return;
                    }
                }
            }
            // 如果key不存在
            size++;
            if (1.0 * size / capacity > loadFactor) {
                resize();
            }
            // 加入新节点
            // 先找到所处位置链表内的节点
            Node newNode = new Node(key, value);
            int index = Math.floorMod(newNode.hashCode(), capacity);
            var location = buckets[index];
            location.add(newNode);
        }
    }

    @Override
    public V get(K key) {
        for (Collection<Node> c : buckets) {
            for (Node node : c) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        for (Collection<Node> c : buckets) {
            for (Node node : c) {
                if (node.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        TreeSet<K> ks = new TreeSet<>();
        for (Collection<Node> nodes : buckets) {
            Iterator<Node> iterator = nodes.iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                ks.add(node.key);
            }
        }
        return ks;
    }

    @Override
    public V remove(K key) {
        V value;
        for (Collection<Node> nodes : buckets) {
            for (Node node : nodes) {
                if (node.key.equals(key)) {
                    value = node.value;
                    nodes.remove(node);
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
       return new MyHashMapIterator<K>(buckets);
    }

    private class MyHashMapIterator<K> implements Iterator<K> {
        private final Collection<Node>[] buckets;
        private int thisBucketIndex = 0;
        private Iterator<Node> nodeIterator = null;

        public MyHashMapIterator(Collection<Node>[] buckets) {
            this.buckets = buckets;
            moveToNextNonEmptyBucket();
        }

        // 移动到下一个非空桶
        private boolean moveToNextNonEmptyBucket() {
            while (thisBucketIndex < buckets.length) {
                Collection<Node> bucket = buckets[thisBucketIndex];
                if (bucket != null && !bucket.isEmpty()) {
                    nodeIterator = bucket.iterator();
                    thisBucketIndex++;
                    return true;
                }
                thisBucketIndex++;
            }
            nodeIterator = null;
            return false;
        }

        @Override
        public boolean hasNext() {
            if (nodeIterator != null && nodeIterator.hasNext()) {
                return true;
            }
            return moveToNextNonEmptyBucket();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                return null;
            }
            return (K) nodeIterator.next().key;
        }
    }

    private void resize() {
        // 调整数组大小
        capacity *= FACTOR;
        Collection<Node>[] newBuckets = new Collection[capacity];;
        for (int i = 0; i < capacity; i++) {
            newBuckets[i] = createBucket();
        }
        // 在HashTable上重新分布Node
        for (Collection<Node> nodes : buckets) {
            Iterator<Node> iterator = nodes.iterator();
            while (iterator.hasNext()) {
                var node =  iterator.next();
                int index = Math.floorMod(node.hashCode(), capacity);
                newBuckets[index].add(node);
            }
        }
        buckets = newBuckets;
    }
}
