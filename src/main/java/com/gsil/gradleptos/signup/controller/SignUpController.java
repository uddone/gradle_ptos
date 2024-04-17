package com.gsil.gradleptos.signup.controller;

import com.gsil.gradleptos.EventResult;
import com.gsil.gradleptos.signup.service.SignUpServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="01. 로그인 ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final SignUpServiceImpl service;

    @Tag(name ="01. 로그인 ")
    @Operation(summary = "유저 로그인1", description = "db 및 스웨거 연결 테스트1")
    @GetMapping("/check")
    public EventResult CheckUserLogin(@Parameter(description = "플랫폼 타입", required = true) @RequestParam(name = "platform_type") Integer platformType,
                                      @Parameter(description = "고유 플랫폼키", required = true) @RequestParam(name = "platform_key") String platformKey,
                                      @Parameter(description = "회원 ID", required = true) @RequestParam(name = "auth_id") String authId,
                                      @Parameter(description = "회원 Password", required = true) @RequestParam(name = "auth_token") String authToken)
    {

        return service.CheckUserLogin(platformType, platformKey, authId, authToken);
    }
}
