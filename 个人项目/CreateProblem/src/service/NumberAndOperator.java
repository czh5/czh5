package service;

import java.util.List;

public class NumberAndOperator {
    //用于保存操作数和操作符的类
    List<String> number;
    List<String> operator;

    public NumberAndOperator() {
    }

    public NumberAndOperator(List<String> number, List<String> operator) {
        this.number = number;
        this.operator = operator;
    }

    public List<String> getNumber() {
        return number;
    }

    public List<String> getOperator() {
        return operator;
    }
}
