import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.floorMod;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int length;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int FACTOR = 2;


    public void copyArray(int newLength) {
        T[] newItems = (T[]) new Object[newLength];

//        for (int i = 0; i < nextLast; i++) {
//            newItems[i] = items[i];
//        }
//        for (int i = nextFirst + 1; i < length; i++) {
//            int number = length - i;
//            newItems[newLength - number] = items[i];
//        }
        int j = 0;
        int n = 0;
        for (int i = nextFirst + 1; i < nextFirst + 1 + size; i++) {
            n = floorMod(i, length);// 原length
            newItems[j] = items[n];
            j++;
        }
        items = newItems;
        length = newLength;
        nextFirst = length - 1; // 已更新length
        nextLast = size;
    }

    public void resizeUp() {
        int newLength = length * FACTOR;
        copyArray(newLength);
    }

    public void resizeDown() {
        int newLength = length / FACTOR;
        copyArray(newLength);
    }

    public ArrayDeque61B() {
        length = 8;
        items = (T[]) new Object[length];
        size = 0;
        nextLast = 0;
        nextFirst = length - 1;
    }

    @Override
    public void addFirst(T x) {
        if (size == length) {
            resizeUp();
        }
        items[nextFirst] = x;
        nextFirst = floorMod(nextFirst - 1, length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == length) {
            resizeUp();
        }
        items[nextLast] = x;
        nextLast = floorMod(nextLast + 1, length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        int n = 0;
        for (int i = nextFirst + 1; i < nextFirst + 1 + size; i++) {
            n = floorMod(i, length);
            list.add(items[n]);
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return false;
        }
        return true;
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

        size--;
        nextFirst = floorMod(nextFirst + 1, length);
        T item = items[nextFirst];
        items[nextFirst] = null;
        if (size <= length / 4) {
            resizeDown();
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        nextLast = floorMod(nextLast - 1, length);
        T item = items[nextLast];
        items[nextLast] = null;
        if (size <= length / 4) {
            resizeDown();
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            return null;
        }
        index = floorMod(index + nextFirst + 1, length);
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException(
                "No need to implement getRecursive for proj 1b"
        );
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int position;

        public ArrayDeque61BIterator() {
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ArrayDeque61B otherDeque) {
            if (this.size != otherDeque.size) {
                return false;
            }
            for (T x : this) {
                if (!otherDeque.contains(x)) {
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
