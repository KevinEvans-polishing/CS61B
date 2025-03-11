import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node putHelper(Node n, K key, V value) {
        if (n == null) {
            return new Node(key, value);
        } else if (key.compareTo(n.key) < 0) {
            n.left = putHelper(n.left, key, value);
        } else if (key.compareTo(n.key) > 0) {
            n.right = putHelper(n.right, key, value);
        } else if (!value.equals(n.value)) {
            n.value = value;
            return n;
        }
        size++;
        return n;
    }

    public int compareItem(BSTMap<K, V> other) {
        return this.item.key.compareTo(other.item.key);
    }

    private Node item;
    private int size;

    @Override
    public void put(K key, V value) {
        item =  putHelper(item, key, value);
    }

    @Override
    public V get(K key) {
//        if (!containsKey(key)) {
//            return null;
//        }
        return getHelper(item, key).value;
    }

    private Node getHelper(Node n, K key) {
        if (n == null) {
            return null;
        }
        if (n.key.equals(key)) {
            return n;
        } else if (key.compareTo(n.key) < 0) {
            return getHelper(n.left, key);
        } else {
            return getHelper(n.right, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public void printInOrder() {

    }

    @Test
    public void putTest() {
        BSTMap<Integer, Integer> integers = new BSTMap<>();
        integers.put(10, 1);
        integers.put(10, 2);
        integers.put(12, 3);
        integers.put(11 ,4);
        assertThat(integers.size).isEqualTo(3);
    }

    @Test
    public void getTest() {
        BSTMap<Integer, Integer> integers = new BSTMap<>();
        integers.put(10, 1);
        integers.put(9, 2);
        integers.put(8, 3);
        integers.put(11, 4);

        assertThat(integers.get(11)).isEqualTo(4);
        assertThat(integers.get(8)).isEqualTo(3);
    }
}
