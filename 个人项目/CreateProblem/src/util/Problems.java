package util;

import java.util.*;

public class Problems {

    //题目类
    /*
    *   本类包含四个方法，分别是：
    *       getSign             得到需要用到的运算符
    *       getPartProblem      得到部分表达式
    *       getTotalProblem     将部分表达式和运算符拼接得到总的表达式
    *       addBrackets         为总的表达式随机添加括号
    * */

    private HashSet<Character> needSigns;   //用于记录表达式需要的运算符
    public static int totalSignNum = 0;


    public HashSet<Character> getNeedSigns() {
        return needSigns;
    }

    public void setNeedSigns(HashSet<Character> needSigns) {
        this.needSigns = needSigns;
    }

    /**
     *  得到算式要用到的运算符
     * @return 返回需要用到的运算符
     */
    public static HashSet<Character> getSign() {
        char[] allSign = {'+','-','*','÷'};  //可供选择的符号
        Random random = new Random();
        HashSet<Character> signs = new HashSet<>();
        int signNum = random.nextInt(3) + 1;    // 1 <= 运算符个数 <= 3

        int signsLength = signs.size();     //运算符的数量
        do {
            char newSign = allSign[random.nextInt(4)]; //得到四则运算符中的一个
            if (!signs.contains(newSign)) {
                //如果需要的运算符中不包含新的，则加入
                signs.add(newSign);
                signsLength++;  //长度+1
            }
        }while (signsLength < signNum);

        return signs;
    }

    /**
     *
     * @param max   生成的数据的最大值，由用户的-r参数指定
     * @return  返回总体算式的部分，以字符串的形式返回
     */
    private String getPartProblem(int max){
        //假设有算是 e = a + b，则a,b可以是自然数，分数，子算式，因此用1,2,3来分别代表这三种情况
        Random random = new Random();
        int type = random.nextInt(3) + 1;
        String returnData = null;
        switch (type) {
            case 1: //自然数
                returnData = String.valueOf(random.nextInt(max));
                break;
            case 2: //分数
                int  molecule;  //分子
                int denominator;    //分母
                int  consult;   //分子和分母的商
                int remainder;  //分子取余分母的余数

                do {
                    molecule = random.nextInt(max); //分子范围是0~(max-1)
                    denominator = random.nextInt(max - 1) + 1;    //分母范围是1~(max-1)
                    consult = molecule / denominator;
                    remainder = molecule % denominator;
                    //若分子/分母的结果小于max-1，则生成的分数肯定合法
                    //若分子刚好整除分母，则表示此时生成的分数实际上是个整数，也合法
                } while (consult >= max - 1 && remainder != 0);
                if (remainder == 0) {
                    //分子整除分母，直接返回整数
                    returnData = String.valueOf(molecule / denominator);
                } else {
                    //此时为分数
                    if (consult == 0) {
                        //此时为真分数
                        returnData = molecule + "/" + denominator;
                    } else {
                        //此时为假分数,应化为真分数
                        returnData = consult + "'" + remainder + "/" + denominator;
                    }
                }
                break;
            case 3: //子算式
                if (totalSignNum >= 3) {
                    //若此时整体算式的符号数已超过3，则不再选择子算式
                    returnData = getPartProblem(max);
                    break;
                }


                HashSet<Character> subSigns;    //子算式的运算符
                Problems pro = new Problems();

                int createTimes = 0;    //用于记录子算式的运算符生成的次数
                while (true) {
                    createTimes++;
                    if (createTimes > 5) {
                        //若连续五次生成的符号都不合法，直接退出，重新选择数据的类型(即自然数，真分数还是子算式)
                        break;
                    }
                    subSigns = getSign();
                    int subSignNum = subSigns.size();

                    if (totalSignNum + subSignNum <= 3) {
                        //则表示加入子算式后，整体算式的符号数不超过3，合法
                        totalSignNum += subSignNum;
                        pro.setNeedSigns(subSigns);
                        break;
                    }
                }
                if (createTimes > 5) {
                    //则重新选择数据类型
                    returnData = getPartProblem(max);
                    break;
                }
                returnData = getTotalProblem(max,pro);
                break;
        }
        return returnData;
    }

    /**
     *
     * @param max   生成数据的最大范围
     * @param problems  题目对象
     * @return  返回不带括号的整个算式
     */
    public  String getTotalProblem(int max,Problems problems){
        StringBuilder totalProblem = new StringBuilder();
        /*HashSet<Character> needSigns = problems.getNeedSigns();
        for (char sign : problems.getNeedSigns()) {
            totalProblem.append(getPartProblem(max,totalSigns)).append(" ").append(sign).append(" ");
        }*/
        Object[] list = problems.getNeedSigns().toArray();
        for (Object o : list) {
            totalProblem.append(getPartProblem(max)).append(" ").append(o).append(" ");
        }
        totalProblem.append(getPartProblem(max));
        return totalProblem.toString();
    }

    /**
     *  为算式随机添加括号
     * @param problem     未添加括号的算式
     * @return  返回添加括号的算式
     */
    public static String addBrackets(String problem) {
        //整个算式的符号数可能为1,2,3，当只有1个符号的时候不需要加括号，当有2个时随机生成最多1个括号，当有3个符号时随机生成最多2个括号
        StringBuilder problemWithBrackets = new StringBuilder();     //带括号的算式
        if (totalSignNum == 1) {
            problemWithBrackets = new StringBuilder(problem);
        }else {
            Random random = new Random();
            int bracketNum = random.nextInt(totalSignNum);  //如果有n个符号，最多生成n-1个括号
            String[] pro = problem.split(" ");
            int[] lIndex = new int[bracketNum];  //用于保存左括号的位置
            int[] rIndex = new int[bracketNum]; //用于保存右括号的位置
            int lastLeft = 0;   //最后一个左括号
            for (int i = 0; i < bracketNum; i++) {
                //假设有算式 a+b+c+d
                //则左括号只能随机在a和b和c前面生成,即下标为0和2和4的位置前
                //左括号不能出现在算式最后
                lIndex[i] = random.nextInt(totalSignNum) * 2;
                if (lastLeft < lIndex[i]) {
                    lastLeft = lIndex[i];
                }

            }
            boolean rLegal;
            for (int i = 0; i < bracketNum; i++) {
                //假设有算式 a+b+c+d,且最后一个左括号在b的前面
                //则右括号只能随机在c和d后面生成,即下标为4和6的位置前
                //右括号不能出现在算式最前，且右括号前一定要至少有一个左括号
                rLegal = false;  //用于判断右括号是否合法
                while (!rLegal) {
                    rIndex[i] = random.nextInt(totalSignNum) * 2 + 2;
                    for (int k = 0; k < bracketNum; k++) {
                        //当右括号前有左括号且后有左括号，即如(a+b)+(c..时，合法
                        //当右括号在最后一个左括号后面，即如a+b+(c+d)...时，合法
                        rLegal = (lIndex[k] < rIndex[i]) && (rIndex[i] != lastLeft);
                    }
                }

            }

            for (int i = 0; i < bracketNum; i++) {
                pro[lIndex[i]] = "(" + pro[lIndex[i]];
                pro[rIndex[i]] = pro[rIndex[i]] + ")";
            }
            for (String s : pro) {
                problemWithBrackets.append(s).append(" ");
            }
            problemWithBrackets.deleteCharAt(problemWithBrackets.length()-1);
        }

        String pwb = problemWithBrackets.toString();
        if (pwb.contains("((") && pwb.contains("))")) {
            //因为一个算式最多两个括号，因此这种情况下表示两个括号的范围是一样的，变为一个
            pwb = pwb.replace("((","(");
            pwb = pwb.replace("))",")");
        }

        return pwb;
    }

}
