package util;

import service.NumberAndOperator;
import Exception.ProblemRepeatException;

import java.util.HashMap;
import java.util.List;

public class Check {

    /**
     * 检查算式是否重复
     * @param record    记录，保存有目前所有不重复的题目
     * @param result    需要验证是否重复的题目的答案
     * @param number    需要验证是否重复的题目的操作数
     * @param operator  需要验证是否重复的题目的操作符
     *
     * 验证思路是：在两算式结果相同的前提下，先判断操作符的数量是否相同，相同则判断操作符的顺序是否完全一致，若仍相同，判断同一
     *                  操作符对应的两个操作数是否相同，若仍相同则两算式相同
     */
    public static boolean checkRepeat(HashMap<String, List<NumberAndOperator>> record, String result, List<String> number, List<String> operator) {
        List<NumberAndOperator> list = record.get(result);
        NumberAndOperator nao;
        List<String> needComparedNumbers;       //同一结果中，一道题目中所有的操作数
        List<String> needComparedOperators;     //同一结果中，一道题目中所有的操作符
        boolean isRepeat;   //记录是否有重复

        for (NumberAndOperator numberAndOperator : list) {
            isRepeat = true;
            nao = numberAndOperator;
            needComparedNumbers = nao.getNumber();
            needComparedOperators = nao.getOperator();
            if (needComparedOperators.size() != operator.size()) {
                continue;   //如果操作符数量不同，就是不同
            } else {
                //操作符数量相同，判断操作符的具体顺序
                for (int k = 0; k < operator.size(); k++) {
                    if (!needComparedOperators.get(k).equals(operator.get(k))) {
                        isRepeat = false;
                        break;   //如果操作符的具体顺序不同，就是不同
                    }
                }
                if (isRepeat) {
                    //如果此时isRepeat为true，表示操作符的具体顺序也相同，判断操作符对应的操作数是否相同
                    for (int j = 0; j < number.size() - 1; j++) {
                        if (!(
                                (needComparedNumbers.get(j).equals(number.get(j)) && needComparedNumbers.get(j + 1).equals(number.get(j + 1)))
                                        || (needComparedNumbers.get(j).equals(number.get(j)) && needComparedNumbers.get(j + 1).equals(number.get(j + 1)))
                        )) {
                            isRepeat = false;
                            break;   //如果同一操作符对应的操作数组成不同，就是不同
                        }
                    }
                }
            }
            if (isRepeat) {
                //如果在判断过程中发现与某个算式重复，直接退出
                return true;
            }

        }
        return false;
    }

    /**
     * 根据算式是否重复对其进行操作
     * @param record    记录，保存有目前所有不重复的题目
     * @param numberAndOperators    保存操作数和操作符
     * @param result    需要验证是否重复的题目的答案
     * @param number    需要验证是否重复的题目的操作数
     * @param operator  需要验证是否重复的题目的操作符
     * @throws ProblemRepeatException 题目重复异常
     */
    public static void insertIntoRecord(HashMap<String, List<NumberAndOperator>> record, List<NumberAndOperator> numberAndOperators, String result, List<String> number, List<String> operator) throws ProblemRepeatException {
        if (!record.containsKey(result)) {
            //如果已有的题目中没有该答案的题目，则肯定不重复，直接添加
            numberAndOperators.add(new NumberAndOperator(number,operator));
            record.put(result,numberAndOperators);
        } else {
            //如果已有该答案，判断是否重复
            if (Check.checkRepeat(record,result,number,operator)) {
                throw new ProblemRepeatException("Problem Repeat"); //重复则抛出异常
            } else {
                //不重复则加入
                numberAndOperators.add(new NumberAndOperator(number,operator));
                record.put(result,numberAndOperators);
            }
        }
    }
}
