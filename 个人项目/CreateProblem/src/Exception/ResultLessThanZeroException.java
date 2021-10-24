package Exception;

public class ResultLessThanZeroException extends Exception{
    //当遇到减法，且结果小于零时出现异常
    public ResultLessThanZeroException(){

    }
    public ResultLessThanZeroException(String s){
        super(s);
    }
}
