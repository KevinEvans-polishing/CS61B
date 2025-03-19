package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {
    private String file1;
    private String file2;
    public HyponymsHandler(String file1, String file2) {
        this.file1 = file1;
        this.file2 = file2;
    }
    @Override
    public String handle(NgordnetQuery q) {
        var g = FileReader.readFile(file1, file2);
        return HyponymSet.getHyponymSet(g, q.words().getFirst()).toString();
    }
}
