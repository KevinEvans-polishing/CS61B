package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 整个类都是用来辅助寻找下义词集的
 * 所以不关心是否有相应的构造函数
 * 或者说构造函数就是用来找下义词集的
 */
public class HyponymSet {
    public static Set<String> getHyponymSet(Graph G, String word) {
        // 1. 得到word对应的邻接表index集合
        // 所以相应的方法应该由Graph API提供
        List<Integer> indexList = findListsWhereWordIn(G, word);
        Set<Integer> hyponymsOfInteger = new HashSet<>();
        // 2. 将index和index对应List的所有元素取出
        // 并放入集合中
        // 注意需要递归取出
        // 因为List中元素对应的index所指向的list不一定为空
        for (int index : indexList) {
            hyponymsOfInteger.addAll(getEverythingfromTheIndexList(G, index));
        }
        // 3. 将id集合映射回word集合
        //  注意某些id对应的节点不只一个词
        // 以及去重问题
        return backToStringSet(hyponymsOfInteger);
    }
}
