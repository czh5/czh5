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
    public static double cosSimilarity(List<Word> orig, List<Word> comp) {

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

    }
}
