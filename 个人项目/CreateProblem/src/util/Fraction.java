package util;
import Exception.ResultLessThanZeroException;

public class Fraction {
    //分数
    // 本类提供五个方法，分别是将数据转化为分数和进行分数的加、减、乘、除

    public static String changeToFrac(String n){
        String fracN;
        if (n.contains("/")) {
            //如果原本就是一个分数，判断是否是带分数
            if (n.contains("'")) {
                //假设有带分数：a'b/c
                String[] s1 = n.split("'");     //将a'b/c分为 a  b/c
                String[] s2 = s1[1].split("/"); //将b/c分为 b c
                fracN = (Integer.parseInt(s1[0]) * Integer.parseInt(s2[1]) + Integer.parseInt(s1[1])) + "/" + s2[1];
            }else {
                //如果不是带分数，直接赋值
                fracN = n;
            }
        } else {
            //如果是一个整数，那么置分母为1
            fracN = n + "/1";
        }
        return fracN;
    }

    /**
     * 辗转相除法求两数的最大公约数
     * @param x 第一个数
     * @param y 第二个数
     * @return  返回两数的最大公约数
     */
    private static int gcd(int x,int y) {
        int r;
        while (y != 0) {
            r = x % y;
            x = y;
            y = r;
        }
        return x;
    }

    /**
     * 求两分数之和
     * @param first_numerator       第一个分数的分子
     * @param first_denominator     第一个分数的分母
     * @param second_numerator      第二个分数的分子
     * @param second_denominator    第二个分数的分母
     * @return      以字符串的形式返回分数
     */
    public static String fracAdd(int first_numerator, int first_denominator, int second_numerator, int second_denominator) {
        int numerator;   //结果的分子
        int denominator; //结果的分母

        if (first_denominator == second_denominator) {
            //如果分母相同，直接相加
            numerator = first_numerator + second_numerator;
            denominator = first_denominator;
        } else {
            //分母不用，要通分在相加
            numerator = first_numerator * second_denominator + second_numerator * first_denominator;
            denominator = first_denominator * second_denominator;
        }

        //约分
        int gcd = gcd(numerator,denominator);
        numerator /= gcd;
        denominator /= gcd;

        return numerator + "/" + denominator;
    }

    /**
     * 求两分数之差
     * @param first_numerator       第一个分数的分子
     * @param first_denominator     第一个分数的分母
     * @param second_numerator      第二个分数的分子
     * @param second_denominator    第二个分数的分母
     * @return      以字符串的形式返回分数
     */
    public static String fracSub(int first_numerator, int first_denominator, int second_numerator, int second_denominator) throws ResultLessThanZeroException {
        int numerator;   //结果的分子
        int denominator; //结果的分母

        if (first_denominator == second_denominator) {
            //如果分母相同，直接相减
            numerator = first_numerator - second_numerator;
            denominator = first_denominator;
        } else {
            //分母不用，要通分再相减
            numerator = first_numerator * second_denominator - second_numerator * first_denominator;
            denominator = first_denominator * second_denominator;
        }
        if (numerator < 0) {
            //若计算过程中出现结果为负的，抛出异常
            throw new ResultLessThanZeroException("计算过程中出现结果为负的算式");
        }

        //约分
        int gcd = gcd(numerator,denominator);
        numerator /= gcd;
        denominator /= gcd;

        return numerator + "/" + denominator;
    }

    /**
     * 求两分数之积
     * @param first_numerator       第一个分数的分子
     * @param first_denominator     第一个分数的分母
     * @param second_numerator      第二个分数的分子
     * @param second_denominator    第二个分数的分母
     * @return      以字符串的形式返回分数
     */
    public static String fracMul(int first_numerator, int first_denominator, int second_numerator, int second_denominator) {
        int numerator;   //结果的分子
        int denominator; //结果的分母

        numerator = first_numerator * second_numerator;     //分子乘分子
        denominator = first_denominator * second_denominator;   //分母乘分母

        //约分
        int gcd = gcd(numerator,denominator);
        numerator /= gcd;
        denominator /= gcd;

        return numerator + "/" + denominator;
    }

    /**
     * 求两分数之商
     * @param first_numerator       第一个分数的分子
     * @param first_denominator     第一个分数的分母
     * @param second_numerator      第二个分数的分子
     * @param second_denominator    第二个分数的分母
     * @return      以字符串的形式返回分数
     */
    public static String fracDiv(int first_numerator, int first_denominator, int second_numerator, int second_denominator) {
        //(a/b) ÷ (c/d)等价于(a/b) * (d/c)
        return fracMul(first_numerator,first_denominator,second_denominator,second_numerator);
    }
}
