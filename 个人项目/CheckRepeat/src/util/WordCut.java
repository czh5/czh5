package util;

import java.util.*;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

public class WordCut {
    //对文本进行分词操作

    /**
     * 对已读入的文件进行分词，并返回分词的结果
     * @param text  已读入的文件
     * @return 返回分词结果
     */
    public static List<Word> getSplitWord(String text) {
        //调用word分词器中的分词函数
        return WordSegmenter.segWithStopWords(text);
    }

}
