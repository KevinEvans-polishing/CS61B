package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private HashMap<String, TimeSeries> wordsRecord = new HashMap<>();
    private TimeSeries yearsRecord = new TimeSeries();
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        TimeSeries ts = new TimeSeries();
        int key;
        double value;
        String lastWord = " ";
        // 读取文件1，并将结果储存到wordsRecord中
        In in = new In(wordsFilename);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            // 分割
            String[] spiltLine = nextLine.split("\t");
            String thisWord  = spiltLine[0];
            key = Integer.parseInt(spiltLine[1]);
            value = Double.parseDouble(spiltLine[2]);
            if (!thisWord.equals(lastWord)) {
                ts = new TimeSeries();
                lastWord = thisWord;
            }
            ts.put(key, value);
            if (wordsRecord != null) {
                wordsRecord.put(spiltLine[0], ts);
            }
        }
        // 读取文件2，并将结果储存到yearsRecord中
        in = new In(countsFilename);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            key = Integer.parseInt(spiltLine[0]);
            value = Double.parseDouble(spiltLine[1]);
            if (yearsRecord != null) {
                yearsRecord.put(key, value);
            }
        }
    }

    public NGramMap(String wordsFilename) {
        TimeSeries ts = new TimeSeries();
        int key;
        double value;
        String lastWord = " ";
        In in = new In(wordsFilename);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            // 分割
            String[] spiltLine = nextLine.split("\t");
            String thisWord  = spiltLine[0];
            key = Integer.parseInt(spiltLine[1]);
            value = Double.parseDouble(spiltLine[2]);
            if (!thisWord.equals(lastWord)) {
                ts = new TimeSeries();
                lastWord = thisWord;
            }
            ts.put(key, value);
            if (wordsRecord != null) {
                wordsRecord.put(spiltLine[0], ts);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // 检索word
        if (!wordsRecord.containsKey(word)) {
            return new TimeSeries();
        }
        // 找到原ts
        var origin = wordsRecord.get(word);
        return new TimeSeries(origin, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (!wordsRecord.containsKey(word)) {
            return new TimeSeries();
        }
        var origin = wordsRecord.get(word);
        var copy = new TimeSeries();
        for (int key : origin.keySet()) {
            copy.put(key, origin.get(key));
        }
        return copy;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        var copy = new TimeSeries();
        for (int key : yearsRecord.keySet()) {
            copy.put(key, yearsRecord.get(key));
        }
        return copy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordsRecord.containsKey(word)) {
            return new TimeSeries();
        }
        var yearTotal = totalCountHistory();
        var wordOf = countHistory(word, startYear, endYear);
        return wordOf.dividedBy(yearTotal);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordsRecord.containsKey(word)) {
            return new TimeSeries();
        }
        var yearTotal = totalCountHistory();
        var wordOf = countHistory(word);
        return wordOf.dividedBy(yearTotal);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            // 将word的频率取出
                ts = ts.plus(weightHistory(word, startYear, endYear));
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts.plus(weightHistory(word));
        }
        return null;
    }

    // TODO: Add any private helper methods.
}
