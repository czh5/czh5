package main;

import service.Choose;

import java.io.File;


public class FourArithmetic {
    //四则运算
    public static void main(String[] args) throws Exception {
        int problemNum = 20;     //题目数量,不指定时默认为20题
        int numRange = -1;       //范围
        String ePath = null;    //题目路径
        String aPath = null;    //答案路径
        String gPath = "grade.txt";    //判断对错后的结果记录路径
        File file = new File("");
        String rootPath = file.getAbsolutePath() + "\\";

        if (args.length != 2 && args.length != 4) {
            //有三种情况是合法的，
            // 当要生成题目时，要么只指定数据范围，此时长度为2；要么同时指定范围和题目数量，此时长度为4
            // 当要批改时，必须同时给出题目文件和答案文件，长度为4
            throw new Exception("输入格式错误，请正确输入！");
        }

        for (int i = 0; i < args.length - 1; i += 2) {
            if ("-e".equals(args[i])) {
                ePath = args[i + 1];
            } else if ("-a".equals(args[i])) {
                aPath = args[i + 1];
            } else if ("-n".equals(args[i])) {
                problemNum = Integer.parseInt(args[i + 1]);
            } else if ("-r".equals(args[i])) {
                numRange = Integer.parseInt(args[i + 1]);
            }
        }
        if (ePath == null && aPath == null) {
            //表明此时执行的是生成题目的命令，则要判断是否有正确指定数据范围
            if (numRange > 0) {
                Choose.createProblemsAndAnswers(problemNum,numRange);
                System.out.println("题目生成完毕，题目文件为[exercise.txt]，答案文件为[answer.txt]");
            } else {
                //说明未指定数据范围或指定的范围不合法
                throw new Exception("请用[-r]参数指定>0的数据范围！");
            }
        } else if (ePath != null && aPath != null) {
            //表明此时执行的是批改的命令
            Choose.judgeRightAndWrong(rootPath + ePath,rootPath + aPath,rootPath + gPath);
        } else {
            //其他都为非法输入
            throw new Exception("输入格式错误，请正确输入！");
        }



    }
}