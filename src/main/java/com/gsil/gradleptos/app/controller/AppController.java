package com.gsil.gradleptos.app.controller;

import com.gsil.gradleptos.EventResult;
import com.gsil.gradleptos.app.service.AppServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name ="00. 테스트 ")
@RestController
@RequestMapping("/test")
public class AppController {
    @Autowired
    AppServiceImpl service;

    @Operation(summary = "테스트 test", description = "db 및 스웨거 연결 테스트")
    @GetMapping("/get")
    public EventResult getTest(@Parameter(name = "user_id", required = true, example = "10") @RequestParam(name = "user_id") long userId) {
        EventResult eventResult = service.getTest(userId);
        System.out.println(new Date());
        return eventResult;
    }
    @Operation(summary = "문자열 테스트", description = "그래들 연결 확인을 위한 문자열 테스트")
    @GetMapping("/gradle")
    public String gradle() {
        return "gradle!@";
    }

    @Operation(summary = "홍비매니저님", description = "인텔리제이에서 ")
    @GetMapping("/haha")
    public String gradle() {
        return "깃허브 테스트 두번째";
    }

}
