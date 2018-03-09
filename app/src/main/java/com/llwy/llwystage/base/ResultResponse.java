package com.llwy.llwystage.base;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ResultResponse<T> {

    public String Result;
    public String Message;
    public T Data;

    public ResultResponse(String more, String _message, T result) {
        Result = more;
        Message = _message;
        Data = result;
    }
}
