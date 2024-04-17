package com.gsil.gradleptos;

import java.util.List;

public class TypedEventResult<T> implements EventResultI<T> {
    // 수행 메서드 명
    private String rqMethod;
    // 결과 코드
    private Integer rsCode = -1;
    // 결과 메시지
    private String rsMsg;
    // 결과 값 List
    private List<T> rsMap;
    // 결과 값 Map
    private T rsObj;
    // Exception Err 메시지
    private String exMsg;

    public String getRqMethod()
    {
        return rqMethod;
    }

    public void setRqMethod(String rqMethod)
    {
        this.rqMethod = rqMethod;
    }

    public int getRsCode()
    {
        return rsCode;
    }

    public void setRsCode(int rsCode)
    {
        this.rsCode = rsCode;
    }

    public String getRsMsg()
    {
        return rsMsg;
    }

    public void setRsMsg(String rsMsg)
    {
        this.rsMsg = rsMsg;
    }

    public String getExMsg()
    {
        return exMsg;
    }

    public void setExMsg(String exMsg)
    {
        this.exMsg = exMsg;
    }

    public T getRsObj()
    {
        return rsObj;
    }

    public void setRsObj(T rsObj)
    {
        this.rsObj = rsObj;
    }

    public void putRsObj(T object)
    {
        this.rsObj = object;
    }

    public List<T> getRsMap()
    {
        return rsMap;
    }

    public void setRsMap(List<T> rsMap)
    {
        this.rsMap = rsMap;
    }
}
