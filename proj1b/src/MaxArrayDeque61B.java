import java.util.Comparator;
import java.util.List;

public class MaxArrayDeque61B<T extends Comparable<T>> extends ArrayDeque61B<T >{
    private Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        return getT(comparator);
    }

    public T max(Comparator<T> c) {
        return getT(c);
    }

    private T getT(Comparator<T> comparator) {
        if (size() == 0) {
            return null;
        }
        List<T> list = this.toList();
        list.sort(comparator);
        int result = list.get(0).compareTo(list.get(size() - 1));
        if (result >= 0) {
            return list.get(0);
        } else {
            return list.get(size() - 1);
        }
    }


}
