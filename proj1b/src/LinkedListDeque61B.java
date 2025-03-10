import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LinkedListDeque61B<T> implements Deque61B<T> {
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }

    private class LinkedListDeque61BIterator implements Iterator<T> {
        private int position;

        public LinkedListDeque61BIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T item = get(position);
            position++;
            return item;
        }
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(Node prev, T item, Node next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null,null, null);
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == 0) {
            Node newNode = new Node(sentinel, x, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node newNode = new Node(sentinel, x, sentinel.next);
            sentinel.next = newNode;
            sentinel.next.next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == 0) {
            Node newNode = new Node(sentinel, x, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        } else {
            Node newNode = new Node(sentinel.prev, x, sentinel);
            sentinel.prev = newNode;
            sentinel.prev.prev.next = newNode;
        }
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        var p = sentinel.next;
        while (p.next != sentinel) {
            list.add(p.item);
            p = p.next;
        }
        list.add(p.item);
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        var firstNode = sentinel.next;
        T item = firstNode.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        firstNode.prev = null;
        firstNode.next = null;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        var lastNode = sentinel.prev;
        T item = lastNode.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        lastNode.prev = null;
        lastNode.next = null;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        var p = sentinel.next;
        int count = 0;
        while (count != index) {
            p = p.next;
            count++;
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        var p = sentinel.next;
        return getRecursiveHelper(p, index);
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque61B otherList) {
            if (this.size != otherList.size) {
                return false;
            }
            for (T x : this) {
                if(!otherList.contains(x)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean contains(T x) {
        for (int i = 0; i < size; i++) {
            if (get(i) == x) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}
