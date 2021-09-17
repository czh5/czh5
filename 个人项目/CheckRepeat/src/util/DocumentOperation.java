package util;

import java.io.*;
import java.text.DecimalFormat;

public class DocumentOperation {
    //文件操作类，包括文件读和写

    /**
     *读取文件
     * @param filePath  通过文件路径读取文件，将txt转化为String
     * @return  返回得到的文本字符串
     */
    public static String readDocument(String filePath){

        BufferedReader reader = null;
        StringBuilder inf = new StringBuilder();

        String readInf ;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((readInf = reader.readLine()) != null) {
                inf.append(System.lineSeparator()).append(readInf);    //将读到的每一行都存入inf中
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inf.toString();
    }

    /**
     *写入文件
     * @param origPath 原文路径
     * @param compPath 抄袭文本的路径
     * @param resultPath 结果的路径
     * @param similarity 相似度
     */
    public static void writeDocument(String origPath,String compPath,String resultPath,double similarity) {
        FileWriter writer = null;
        DecimalFormat df = new DecimalFormat("0.00");   //保留两位小数
        try {
            writer = new FileWriter(resultPath);
            writer.write("原文路径：" + origPath + "\n");
            writer.flush();
            writer.write("抄袭版论文路径：" + compPath + "\n");
            writer.flush();
            writer.write("查重结果为：" + df.format(similarity) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
