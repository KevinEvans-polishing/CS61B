import main.Graph;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static main.FileReader.readFile;
import static main.HyponymSet.getHyponymSet;

public class TestMyGraphAndGetHyponymSet {
    @Test
    public void test11() {
        String file1 = "./data/wordnet/synsets11.txt";
        String file2 = "./data/wordnet/hyponyms11.txt";
        Graph g = readFile(file1, file2);

        assertThat(getHyponymSet(g, "increase")).containsExactly("augmentation", "increase", "jump", "leap").inOrder();
    }

    @Test
    public void test14() {
        String file1 = "./data/wordnet/synsets14.txt";
        String file2 = "./data/wordnet/hyponyms14.txt";
        Graph g = readFile(file1, file2);
        assertThat(getHyponymSet(g, "alteration")).containsExactly(
                "adjustment", "alteration", "change" , "conversion", "increase", "jump",
                "leap", "modification","mutation",
                "saltation", "transition"
        ).inOrder();
    }

    @Test
    public void test16() {
        String file1 = "./data/wordnet/synsets16.txt";
        String file2 = "./data/wordnet/hyponyms16.txt";
        Graph g = readFile(file1, file2);
        assertThat(getHyponymSet(g,"change")).containsExactly(
                "alteration", "change", "demotion", "increase",
                "jump", "leap", "modification", "saltation", "transition", "variation"
        );
    }
}
