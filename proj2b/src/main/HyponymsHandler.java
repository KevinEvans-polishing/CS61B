package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private String file1;
    private String file2;
    private String wordFileName;
    private String countFileName;
    private NGramMap map;

    public HyponymsHandler(String file1, String file2, String wordFileName, String countFileName) {
        this.file1 = file1;
        this.file2 = file2;
        this.wordFileName = wordFileName;
        this.countFileName = countFileName;
        map = new NGramMap(wordFileName, countFileName);
    }

    @Override
    public String handle(NgordnetQuery q) {
        var g = FileReader.readFile(file1, file2);
        TreeSet<String> plainSet = HyponymSet.getHyponymSet(g, q.words());
        if (q.k() == 0 || q.startYear() == 0 || q.endYear() == 0) {
            return plainSet.toString();
        }
        return filterByK(plainSet, map, q.k(), q.startYear(), q.endYear()).toString();
    }

    public TreeSet<String> filterByK(TreeSet<String> origin, NGramMap map,
                                     int k, int startYear, int endYear) {
        // 遍历origin里的所有词
        // 将他们分别与map中的数据进行比对
        // 找到最大的前k项
        // 还需要对年份进行筛选
        Map<Integer, String> mapOfWordAndCount = new TreeMap<>(Comparator.reverseOrder());
        Iterator<String> iterator = origin.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            int countOfWord = countOfWord(map, word, startYear, endYear);
            mapOfWordAndCount.put(countOfWord, word);
        }
        return getTopKStrings(mapOfWordAndCount, k);
    }

    private int countOfWord(NGramMap map, String word, int startYear, int endYear) {
        TimeSeries timeSeries = map.countHistory(word, startYear, endYear);
        int count = 0;
        for (int key : timeSeries.keySet()) {
            count += timeSeries.get(key);
        }
        return count;
    }

    private TreeSet<String> getTopKStrings(Map<Integer, String> map, int k) {
        int index = 0;
        TreeSet<String> strings = new TreeSet<>();
        for (int key : map.keySet()) {
            strings.add(map.get(key));
            index++;
            if (index >= k) {
                break;
            }
        }
        return strings;
    }

}
