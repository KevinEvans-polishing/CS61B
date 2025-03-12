import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import org.apache.bcel.generic.ANEWARRAY;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        return n;
    }


    private Node item;
    private int size;

    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            size++;
        }
        item =  putHelper(item, key, value);
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
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
        if (size == 0) {
        return false;
        }
        Node result = getHelper(item, key);
        if (result == null) {
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        item = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Iterator<K> iterator = iterator();
        Set<K> ks = new HashSet<>();

        while (iterator.hasNext()) {
            K next = iterator.next();
            ks.add(next);
        }
        return ks;
    }

    @Override
    public V remove(K key) {
        item = remove(item, key);
        return get(key);
    }

    private Node remove(Node item, K key) {
        if (item == null) {
            return null;
        }
        if (key.compareTo(item.key) < 0) {
            item.left = remove(item.left, key);
        } else if (key.compareTo(item.key) > 0) {
            item.right = remove(item.right, key);
        } else {
            if (item.left == null) {
                return item.right;
            } else if (item.right == null) {
                return item.left;
            }

            Node successor = findMax(item.left);
            item.key = successor.key;
            item.left = remove(item.left, successor.key);
        }
        return item;
    }

    private Node findMax(Node item) {
        while (item.right != null) {
            item = item.right;
        }
        return item;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(item);
    }

    private class BSTMapIterator implements Iterator<K>{
        private Stack<Node> stack;

        public BSTMapIterator(Node item) {
            stack = new Stack<>();
            pushAllLeft(item);
        }

        private void pushAllLeft(Node item) {
            while (item != null) {
                stack.push(item);
                item = item.left;
            }
        }

        @Override
        public boolean hasNext() {
            return stack.isEmpty();
        }

        @Override
        public K next() {
            Node i = stack.pop();
            pushAllLeft(i.right);
            return i.key;
        }

    }
}
