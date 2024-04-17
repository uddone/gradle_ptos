package com.gsil.gradleptos;

import java.util.List;

public interface EventResultI<T> {
    // 수행 메서드 명
    String getRqMethod();
    void setRqMethod(String rqMethod);
    // 결과 코드
    int getRsCode();
    void setRsCode(int rsCode);
    // 결과 메시지
    String getRsMsg();
    void setRsMsg(String rsMsg);
    // 결과 값 List
    List<T> getRsMap();
    void setRsMap(List<T> rsMap);
    // 결과 값 Map
    T getRsObj();
    void setRsObj(T rsObj);
    // Exception Err 메시지
    String getExMsg();
    void setExMsg(String exMsg);
}
