package main;

import util.DocumentOperation;
import util.SimilarityAnalysis;
import util.WordCut;

public class Application {
    public static void main(String[] args) {
        String origPath = args[0];  //原文路径
        String compPath = args[1];  //抄袭文本路径
        String resultPath = args[2];    //结果文件路径

        long start = System.currentTimeMillis();    //开始时间

        String orig = DocumentOperation.readDocument(origPath);
        String comp = DocumentOperation.readDocument(compPath);

        if (orig.isEmpty() && comp.isEmpty()) {
            throw new RuntimeException("错误，文件为空！");
        }

        double similarity = SimilarityAnalysis.cosSimilarity(WordCut.getSplitWord(orig), WordCut.getSplitWord(comp));
        DocumentOperation.writeDocument(origPath,compPath,resultPath,similarity);

        System.out.println("耗时：" + (System.currentTimeMillis() - start) + " ms");
    }
}
