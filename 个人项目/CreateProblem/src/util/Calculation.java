package util;

import java.util.ArrayList;
import java.util.List;

public class Calculation {
    //计算
    /*计算一个算式的过程：
    *   (1)由于可能存在分数，因此将全部数据都转化为分数进行计算
    *   (2)计算思路是先去括号(如果有括号)，将其转换成无括号的算式，然后按先乘除后加减的顺序计算
    *
    * */

    /**
     * 进行两数之间的运算，因为参与运算的可能是分数，因此用字符串保存
     * @param n1    参与运算的数
     * @param n2    参与运算的数
     * @param numbers 用于保存操作数
     * @param operators 用于保存操作符
     * @param operator  运算符
     * @return  以字符串的形式返回结果
     */
    private static String doubleCal(String n1, String n2, String operator, List<String> numbers, List<String> operators) throws Exception {

        String result;
        //先转为分数形式
        n1 = Fraction.changeToFrac(n1);
        n2 = Fraction.changeToFrac(n2);
        //分别得到分子和分母
        String[] s1 = n1.split("/");
        String[] s2 = n2.split("/");
        int first_numerator = Integer.parseInt(s1[0]);
        int first_denominator = Integer.parseInt(s1[1]);
        int second_numerator = Integer.parseInt(s2[0]);
        int second_denominator = Integer.parseInt(s2[1]);

       switch (operator) {
           case "+":
               result = Fraction.fracAdd(first_numerator,first_denominator,second_numerator,second_denominator);
               break;
           case "-":
               result = Fraction.fracSub(first_numerator,first_denominator,second_numerator,second_denominator);
               break;
           case "*":
               result = Fraction.fracMul(first_numerator,first_denominator,second_numerator,second_denominator);
               break;
           case "÷":
               if (second_numerator == 0) {
                   //除数不能为零
                   throw new ArithmeticException("除数不能为0");
               }
               result = Fraction.fracDiv(first_numerator,first_denominator,second_numerator,second_denominator);
               break;
           default:
               throw new Exception("illegal operator");
       }
       numbers.add(n1);
       numbers.add(n2);
       operators.add(operator);
       return result;
    }

    /**
     * 进行无括号算式的计算
     * @param problem   无括号算式
     * @param numbers 用于保存操作数
     * @param operators 用于保存操作符
     * @return  返回无括号算式的计算结果
     */
    private static String noBracket(String problem, List<String> numbers, List<String> operators) throws Exception {
        List<String> number = new ArrayList<>();    //操作数
        List<String> operator = new ArrayList<>();  //操作符
        String[] pro = problem.split(" ");
        for (int i = 0; i < pro.length; i++) {
            //对于一个算式，若看成一个字符串数组，则操作数在偶数下标，操作符在奇数下标
            if (i % 2 == 0) {
                number.add(pro[i]);
            } else {
                operator.add(pro[i]);
            }
        }
        //先算乘除，再算加减 这里利用了逻辑或||的运算法则，正好对应计算式子的乘除加减的顺序
        int i;
        String o,n1,n2;
        while (-1 != (i = operator.indexOf("*")) || -1 != (i = operator.indexOf("÷"))
                || -1 != (i = operator.indexOf("+")) || -1 != (i = operator.indexOf("-"))) {
            o = operator.remove(i);
            n1 = number.remove(i);
            n2 = number.remove(i);
            number.add(i, doubleCal(n1, n2, o, numbers, operators));
        }
        return number.get(0);
    }

    /**
     * 带括号算式的计算
     * @param problem  算式
     * @param number 用于保存操作数
     * @param operator 用于保存操作符
     * @return 返回结果
     */
    public static String getResult(String problem, List<String> number, List<String> operator) throws Exception {
        //表示最后一个左括号
        int lIndex;
        //与该左括号对应的右括号
        int rIndex;
        //去括号
        while(-1 != (lIndex = problem.lastIndexOf("("))) {
            rIndex = problem.indexOf(")",lIndex);
            problem = problem.substring(0,lIndex)+noBracket(problem.substring(lIndex+1, rIndex),number,operator)+problem.substring(rIndex+1);
        }
        //处理计算结果
        String result = noBracket(problem,number,operator);     //结果为a/b
        String[] res = result.split("/");//分为a 和 b，分别是分子和分母
        if ("1".equals(res[1])) {
            //说明结果是个整数
            result = res[0];
        } else if(Integer.parseInt(res[0]) > Integer.parseInt(res[1])) {
            //需要化为带分数
            result = Integer.parseInt(res[0]) / Integer.parseInt(res[1]) + "'" +
                    Integer.parseInt(res[0]) % Integer.parseInt(res[1]) + "/" +
                    res[1];
        }
        return result;
    }
}
