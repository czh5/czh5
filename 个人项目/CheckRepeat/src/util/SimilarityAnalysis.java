package util;

import org.apdplat.word.segmentation.Word;

import java.util.*;


public class SimilarityAnalysis {
    //对分词进行相似度分析

    /**
     * 采用余弦相似性来进行查重操作，具体为：
     *      1)获取原文和要比较的文本的全部分词
     *      2)分别统计两篇文章里在上述得到的分词中出现的次数，分别为x1,x2...xn和y1,y2...yn
     *      3)代入余弦相似性计算公式得到结果：(x1y1+x2y2+...+xn*yn) / [sqrt(x1²+x2²+x3²+...+xn²)*sqrt(y1²+y2²+y3²+...+yn²)]
     *
     * @param orig 原文
     * @param comp 要比较的文本
     * @return 相似度
     */

    //第二版相似性算法，第一版保留在下方注释中
    public static double cosSimilarity(List<Word> orig, List<Word> comp) {

            // 构建词集origMap和compMap用于保存原文和要比较的文本中的分词
            HashMap<Word,Integer> origMap = new HashMap<>();
            HashMap<Word,Integer> compMap = new HashMap<>();

            // 遍历原文，加入分词，同时计算词频
            for(Word word : orig){
                int num = 1;
                if (origMap.containsKey(word)) {
                    num = origMap.get(word) + 1;
                }
                origMap.put(word,num);

            }
            // 遍历要比较的文件，加入分词，同时计算词频
            for(Word word : comp){
                int num = 1;
                if (compMap.containsKey(word)) {
                    num = compMap.get(word) + 1;
                }
                compMap.put(word,num);
            }

            // 计算文本相似度
            double molecule = 0;
            double denominator1 = 0;
            double denominator2 = 0;


            for (Word word : origMap.keySet()) {
                if (compMap.containsKey(word)) {
                    // 分子，即x1y1+x2y2+...+xn*yn
                    molecule += origMap.get(word)*compMap.get(word);
                }
                // 分母构成
                denominator1 += Math.pow(origMap.get(word),2); //x1²+x2²+x3²+...+xn²
            }
            for (Integer appearNum : compMap.values()) {
                denominator2 += Math.pow(appearNum,2);  //y1²+y2²+y3²+...+yn²
            }

            //分母，即sqrt(x1²+x2²+x3²+...+xn²)*sqrt(y1²+y2²+y3²+...+yn²)
            double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
            // 返回相似度
            return molecule/denominator;

    }

    //以下是第一版的余弦相似性算法，具体实现为先得到两个文本中的所有分词，再分别遍历得到两个
    //文本中分词的词频，最终代入公式得到余弦相似性。
    //思考后发现，公式中(x1y1+x2y2+...+xn*yn) / [sqrt(x1²+x2²+x3²+...+xn²)*sqrt(y1²+y2²+y3²+...+yn²)]对于
    //两文本的同一个分词xk和yk，若xk=0，yk！=0，即该词为某文本独有的词，则xk*yk=0，可以直接不算，xk²=0，因此此时
    //xk可以不统计，最终决定采用hashMap存词频

    /*public static double cosSimilarity(List<Word> orig, List<Word> comp) {

        // 构建词集wordList用于保存原文和要比较的文本中的所有词
        List<Word> wordList = new ArrayList<>();
        // 遍历原文，加入分词
        for(Word word : orig){
            if(!wordList.contains(word)){
                wordList.add(word);
            }
        }
        // 遍历要比较的文件，加入分词
        for(Word word : comp){
            if(!wordList.contains(word)){
                wordList.add(word);
            }
        }

        // 计算两文件的分词词频
        int[] origWordNum = new int[wordList.size()];
        int index = 0;
        for(Word word : wordList){
            int num = 0;
            for(Word w : orig){
                if(word.equals(w)){
                    num++;
                }
            }
            origWordNum[index++] = num;
        }

        int[] compWordNum = new int[wordList.size()];
        index = 0;
        for(Word word : wordList){
            int num = 0;
            for(Word w : comp){
                if(word.equals(w)){
                    num++;
                }
            }
            compWordNum[index++] = num;
        }

        // 计算文本相似度
        double molecule = 0;
        double denominator1 = 0;
        double denominator2 = 0;

        for(int i = 0;i < wordList.size();++i){
            // 分子，即x1y1+x2y2+...+xn*yn
            molecule += origWordNum[i]*compWordNum[i];
            // 分母构成
            denominator1 += Math.pow(origWordNum[i],2); //x1²+x2²+x3²+...+xn²
            denominator2 += Math.pow(compWordNum[i],2); //y1²+y2²+y3²+...+yn²
        }

        //分母，即sqrt(x1²+x2²+x3²+...+xn²)*sqrt(y1²+y2²+y3²+...+yn²)
        double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
        // 返回相似度
        return molecule/denominator;

    }*/
}
