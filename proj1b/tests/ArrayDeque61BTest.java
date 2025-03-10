import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    public void getTest() {
        ArrayDeque61B<Integer> de = new ArrayDeque61B<>();
        de.addFirst(10);
        de.addLast(30);
        assertThat(de.get(0)).isEqualTo(10);
    }

    @Test
    public void toListTest() {
        ArrayDeque61B<String> de = new ArrayDeque61B<>();
        de.addLast("a");
        de.addLast("b");
        de.addFirst("c");
        de.addLast("d");
        de.addLast("e");
        de.addFirst("f");
        de.addLast("g");
        de.addLast("h");
        de.addLast("Z");

        assertThat(de.toList()).containsExactly(
                "f", "c", "a",
                "b", "d", "e", "g", "h", "Z"
        ).inOrder();
    }

    @Test
    public void removeFirstAndRemoveFirstTest() {
        ArrayDeque61B<String> de = new ArrayDeque61B<>();
        de.addLast("a");
        de.addLast("b");
        de.addFirst("c");
        de.addLast("d");
        de.addLast("e");
        de.addFirst("f");
        de.addLast("g");
        de.addLast("h");
        de.addLast("Z");

        de.removeFirst();
        de.removeLast();
        assertThat(de.toList()).containsExactly(
                "c", "a", "b", "d",
                "e", "g", "h"
        ).inOrder();
    }

    @Test
    public void nullTest() {
        ArrayDeque61B<Integer> de = new ArrayDeque61B<>();
        de.addLast(10);
        var i = de.removeLast();
        var j = de.removeFirst();
        assertThat(i).isEqualTo(10);
        assertThat(j).isEqualTo(null);

    }

    @Test
    public void addFirstTest() {
        ArrayDeque61B<Integer> id = new ArrayDeque61B<>();
        id.addFirst(10);
        id.addFirst(30);
        id.addFirst(590);

        assertThat(id.toList()).containsExactly(590, 30, 10).inOrder();
    }

    @Test
    public void wholeTest() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(10);
        ad.addLast(16);
        ad.addLast(34);
        ad.addFirst(70);
        ad.addFirst(40);
        ad.addLast(65);
        ad.addLast(43);
        ad.addLast(23);

        ad.addFirst(14);
        // test the resizeUp
        assertThat(ad.toList()).containsExactly(
                14, 40, 70, 10, 16, 34, 65, 43, 23
        ).inOrder();
        System.out.println(ad);

        // test the resizeDown
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(
                70, 10, 16, 34
        ).inOrder();

        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(34).inOrder();

        // test the {@code get()} method
        assertThat(ad.get(0)).isEqualTo(34);
    }

    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
        System.out.println(lld1);
    }

}
