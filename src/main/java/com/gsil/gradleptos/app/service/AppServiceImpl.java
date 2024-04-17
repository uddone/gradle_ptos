package com.gsil.gradleptos.app.service;

import com.gsil.gradleptos.DbObject;
import com.gsil.gradleptos.EventResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppServiceImpl {

    private final DbObject db;

    public EventResult getTest(long userId) {
        EventResult eventResult = new EventResult();

        String sql = " select * from user_info where	1=1 and user_id  = :user_id ";

        Map<String, Object> param = new HashMap<>();
        param.put("user_id",userId);
        eventResult = db.SelectByMap("getTest", sql, param);

        return eventResult;
    }
}
