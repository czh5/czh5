package main;

import util.DocumentOperation;
import util.SimilarityAnalysis;
import util.WordCut;

public class Application {
    public static void main(String[] args) {
        String origPath = args[0];
        String compPath = args[1];
        String resultPath = args[2];

        long start = System.currentTimeMillis();    //开始时间

        double similarity = SimilarityAnalysis.cosSimilarity(
                WordCut.getSplitWord(DocumentOperation.readDocument(origPath)),
                WordCut.getSplitWord(DocumentOperation.readDocument(compPath))
        );
        DocumentOperation.writeDocument(origPath,compPath,resultPath,similarity);

        System.out.println("耗时：" + (System.currentTimeMillis() - start) + " ms");
    }}
