import edu.princeton.cs.algs4.Stack;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    /**
     * Map-Tree-Node int the BSTMap data structure
     */
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
    // item as the reference of the whole tree
    private Node item;
    // node numbers
    private int size;

    /**
     * using {@code putHelper()} to put certain node
     * @param key the key of the node
     * @param value the value of the node
     */
    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            size++;
        }
        item =  putHelper(item, key, value);
    }

    /**
     * recursive method to help to put a node
     * @param n the root node
     * @param key the key of the node
     * @param value the value of the node
     * @return the node after put
     */
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

    /**
     * get the certain value of the key
     * using {@code getHelper()}
     * @param key the key of the node
     * @return the value
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        return getHelper(item, key).value;
    }

    /**
     * get certain node using recursive
     * @param n the root node
     * @param key the needed key
     * @return the Node that you are searching for
     */
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

    /**
     * to tell you whether the BSTMap contains the certain key
     * @param key the key
     * @return true if it does contain that key, else false
     */
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

    /**
     * tell you the size of the BSTMap
     * @return the size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * clear the BSTMap to the very beginning
     */
    @Override
    public void clear() {
        item = null;
        size = 0;
    }

    /**
     * gather the whole key set
     * @return the keys in the BSTMap as a Set
     */
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

    /**
     * remove the node of specific key
     * using the recursive method {@code remove()}
     * which shows the Hibbard deletion
     * @param key the key
     * @return the value of the key
     */
    @Override
    public V remove(K key) {
        item = remove(item, key);
        return get(key);
    }

    /**
     * a helper method which true complete the deletion
     * case 1: delete the node with only one or no child node
     * case 2: delete the node with two child nodes
     * @param item the root node
     * @param key the key of the target node
     * @return root that completed the remove
     */
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

    /**
     * find the max node of the root
     * @param item the tree root
     * @return the max node in the tree
     */
    private Node findMax(Node item) {
        while (item.right != null) {
            item = item.right;
        }
        return item;
    }

    /**
     * get an iterator
     * @return a BSTMap iterator
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(item);
    }

    /**
     * a BSTMapIterator using {@code Stack} to do all the iteration tasks
     */
    private class BSTMapIterator implements Iterator<K>{
        // a stack to store the node
        private Stack<Node> stack;

        /**
         * construct a BSTMapIterator and adding all left node to the stack
         * @param item the root
         */
        public BSTMapIterator(Node item) {
            stack = new Stack<>();
            pushAllLeft(item);
        }

        /**
         * adding all left node in the stack
         * @param item the root
         */
        private void pushAllLeft(Node item) {
            while (item != null) {
                stack.push(item);
                item = item.left;
            }
        }

        /**
         * @return true if the structure has next thing\
         * else false
         */
        @Override
        public boolean hasNext() {
            return stack.isEmpty();
        }


        /**
         * @return the key value of next node
         */
        @Override
        public K next() {
            Node i = stack.pop();
            pushAllLeft(i.right);
            return i.key;
        }
    }

    /**
     * Morris Traversal
     * @param root the root node
     */
    public void inorder(Node root) {
        Node current = root;

        while (current != null) {
            if (current.left == null) {
                System.out.print(current.key + " ");
                current = current.right;
            } else {
                // 中序遍历所需的前驱节点
                Node predecessor = current.left;
                // 找到最右的节点作为前驱
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                // 如果前驱节点的右侧为空，说明还没建立回到current的链接，
                // 进而说明还没有遍历current的左子树
                // 那么就先遍历左子树(中序遍历先遍历左子树)
                if (predecessor.right == null) {
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // 说明已经建立了链接，左子树遍历完成
                    // 接下来遍历右子树
                    // 先讲链接销毁，使树的结构恢复
                    predecessor.right = null;
                    System.out.print(current.key + " ");
                    current = current.right;
                }
            }
        }
    }

    // 想要用莫里斯遍历来实现iterator的next()
    // 只需将inorder()中的打印语句更换为return语句
    public static void main(String[] args) {
        BSTMap<Integer, Integer> integers = new BSTMap<>();
        integers.put(10, 1);
        integers.put(5, 2);
        integers.put(-2, 3);
        integers.put(2, 4);
        integers.put(-1, 5);
        integers.put(6, 6);
        integers.put(8, 7);
        integers.put(30, 8);
        integers.put(40, 9);

        integers.inorder(integers.item);
    }
}
