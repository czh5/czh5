package service;

import util.Calculation;
import util.Check;
import util.DocumentOperation;
import util.Problems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Choose {
    //选择类型：生成题目和答案或批改

    /**
     * 生成题目和答案并将它们写入exercise.txt和answer.txt中
     * @param problemNum    题目的数量
     * @param numRange  数据范围
     */
    public static void createProblemsAndAnswers(int problemNum, int numRange) {
        Problems pro = new Problems();
        HashSet<Character> signs;   //保存运算符
        String problem;  //题目
        String result;   //答案
        HashMap<String, List<NumberAndOperator>> record = new HashMap<>();   //用于记录出现过的题目和其答案
        List<NumberAndOperator> numberAndOperators = new ArrayList<>();
        List<String> number;
        List<String> operator;
        File file = new File("");
        String rootPath = file.getAbsolutePath() + "\\";

        int num = 0;
        while (num < problemNum) {
            try {
                number = new ArrayList<>();
                operator = new ArrayList<>();
                signs = Problems.getSign();
                pro.setNeedSigns(signs);
                Problems.totalSignNum = signs.size();
                problem = Problems.addBrackets(pro.getTotalProblem(numRange, pro));
                result = Calculation.getResult(problem, number, operator);
                Check.insertIntoRecord(record, numberAndOperators, result, number, operator);

            } catch (Exception e) {
                //如果捕捉到异常，说明题目有问题，需要重新生成
                continue;
            }
            num++;
            try {
                DocumentOperation.writeProblemsAndAnswer(rootPath+"exercise.txt",rootPath+"answer.txt",num,problem,result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批改
     * @param ePath 题目路径
     * @param aPath 答案路径
     * @param gPath 结果写入路径
     * @throws IOException  IO异常
     */
    public static void judgeRightAndWrong(String ePath, String aPath, String gPath) throws IOException {

        List<String> answer = DocumentOperation.readFile(ePath);    //填入的答案
        List<String> rightAnswers = DocumentOperation.readFile(aPath);  //正确答案
        int rightNum = 0;   //正确的题目数量
        StringBuilder correct = new StringBuilder("(");   //用于记录正确和错误的具体题目编号
        StringBuilder wrong = new StringBuilder("(");

        for (int i = 0; i < rightAnswers.size(); i++) {
            if (answer.get(i).endsWith("= ")) {
                //表示没填，直接判错误
                wrong.append(i + 1).append(",");
            } else if (answer.get(i).split("= ")[1].equals(rightAnswers.get(i).split("、")[1])) {
                //答案相同则正确
                correct.append(i + 1).append(",");
                rightNum++;
            } else {
                //错误
                wrong.append(i + 1).append(",");
            }
        }

        FileWriter writer = new FileWriter(gPath);
        if (correct.toString().endsWith("(")) {
            //表示此时全错
            correct.append(")\n");
            wrong = new StringBuilder(wrong.substring(0, wrong.length() - 1) + ")");
        } else if (wrong.toString().endsWith("(")) {
            //表示此时全对
            wrong.append(")");
            correct = new StringBuilder(correct.substring(0, correct.length() - 1) + ")\n");
        } else {
            //表示有对有错
            correct = new StringBuilder(correct.substring(0, correct.length() - 1) + ")\n");
            wrong = new StringBuilder(wrong.substring(0, wrong.length() - 1) + ")");
        }
        writer.write("Correct：" + rightNum + correct);
        writer.flush();
        writer.write("Wrong：" + (answer.size() - rightNum) + wrong);
        writer.close();

        System.out.println("批改完毕，批改结果可在[grade.txt]中查看");
    }
}
