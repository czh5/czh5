package test;


import main.Application;
import util.WordCut;
import util.SimilarityAnalysis;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.List;

public class FunctionTest {
        //功能测试
        @Test
        public void TestApplication(){
            long start = System.currentTimeMillis();
            Application.main(new String[]{
                    "C:/Users/Administrator/Desktop/testtxt/orig.txt",
                    "C:/Users/Administrator/Desktop/testtxt/orig_0.8_add.txt",
                    "C:/Users/Administrator/Desktop/testtxt/result.txt"
            });
            System.out.println("耗时：" +(System.currentTimeMillis() - start) + " ms");
        }

        @Test
        public void testWordCut(){
            // 测试正常情况下分词
            List<Word> string1 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            // 测试完全倒序情况下分词
            List<Word> string2 = WordCut.getSplitWord("些彻透的解理了为只，法方多很了试尝，题问些一到遇我。");
            // 测试字符串中加入乱码情况下分词
            List<Word> string3 = WordCut.getSplitWord("我#%遇到&一些问*题，尝试了很~.多方法，只-为了理解_的透彻$(些。");
            // 测试字符串中有空格情况下分词
            List<Word> string4 = WordCut.getSplitWord("  我遇到一   些问 题，尝试  了很 多方 法，只为  了 理 解的 透彻些。");
            // 测试空串情况下的分词
            List<Word> string5 = WordCut.getSplitWord("");

            System.out.println("String1:" + string1);
            System.out.println("String2:" + string2);
            System.out.println("String3:" + string3);
            System.out.println("String4:" + string4);
            System.out.println("String4:" + string5);
        }

        @Test
        public void testSimilarityAnalysis(){

            DecimalFormat df = new DecimalFormat("0.00");   //保留两位小数

            // 测试字符串完全相同时
            List<Word> test1 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test2 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            System.out.println("result1: " + df.format(SimilarityAnalysis.cosSimilarity(test1,test2)));

            // 测试字符串互为倒序时
            List<Word> test3 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test4 = WordCut.getSplitWord("些彻透的解理了为只，法方多很了试尝，题问些一到遇我。");
            System.out.println("result2: " + df.format(SimilarityAnalysis.cosSimilarity(test3, test4)));

            // 测试字符串添加符号时
            List<Word> test5 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test6 = WordCut.getSplitWord("我#%遇到&一些问*题，尝试了很~.多方法，只-为了理解_的透彻$(些。");
            System.out.println("result3: " + df.format(SimilarityAnalysis.cosSimilarity(test5, test6)));

            // 测试字符串部分改动时
            List<Word> test7 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test8 = WordCut.getSplitWord("我遇到一些困难，尝试了许多方法，只为了理解的透彻些。");
            System.out.println("result4: " + df.format(SimilarityAnalysis.cosSimilarity(test7, test8)));

            // 测试字符串出现生僻字时
            List<Word> test9 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test10 = WordCut.getSplitWord("我遇到一些问瑅，尝试了很多錺法，只为了理解的透瞮些。");
            System.out.println("result5: " + df.format(SimilarityAnalysis.cosSimilarity(test9, test10)));

            // 测试字符串是空串时
            List<Word> test11 = WordCut.getSplitWord("");
            List<Word> test12 = WordCut.getSplitWord("");
            System.out.println("result6: " + df.format(SimilarityAnalysis.cosSimilarity(test11, test12)));

            // 测试字符串部分缺失时
            List<Word> test13 = WordCut.getSplitWord("我遇到一些问题，尝试了很多方法，只为了理解的透彻些。");
            List<Word> test14 = WordCut.getSplitWord("我遇到问题，尝试了方法，只为理解的透彻。");
            System.out.println("result7: " + df.format(SimilarityAnalysis.cosSimilarity(test13, test14)));
        }
    }
