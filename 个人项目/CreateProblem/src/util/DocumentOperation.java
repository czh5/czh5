package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentOperation {
    //文件操作类，提供写入题目、写入答案和判断对错的方法

    /**
     * 将题目和答案分别写入文件中
     * @param problemPath   题目写入的路径
     * @param answerPath    答案写入的路径
     * @param num           题目或答案的数量
     * @param problem       具体题目
     * @param answer        答案
     */
    public static void writeProblemsAndAnswer(String problemPath, String answerPath, int num, String problem, String answer) throws IOException {
        FileWriter proWriter = new FileWriter(problemPath,true);
        FileWriter ansWriter = new FileWriter(answerPath,true);
        proWriter.write(num + "、" + problem + " = \n");
        ansWriter.write(num + "、" + answer + "\n");
        proWriter.close();
        ansWriter.close();
    }

    public static List<String> readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> contents = new ArrayList<>();
        String content;
        while ((content = reader.readLine()) != null) {
            contents.add(content);
        }
        reader.close();
        return contents;
    }

}
