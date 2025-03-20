package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        var words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            response += word + ": ";
            response += map.countHistory(word, startYear, endYear).toString() + "\n";
        }
        return response;
    }
}
