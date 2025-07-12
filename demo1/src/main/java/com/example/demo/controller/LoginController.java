package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/api/userinfo/Login")
    public Object Login(String user_no, String user_pwd){
        Map<String, Object> Results = new HashMap<>();

        List<Map<String, Object>> Result = jdbcTemplate.queryForList("SELECT * FROM userinfo WHERE user_no = ? AND user_pwd = ?", user_no, user_pwd);

        if (Result != null && Result.stream().count() > 0){ //找到数据行
            Results.put("success", true); //成功执行
            Results.put("code", 200);
            Results.put("message", "登录成功！"); //登录成功！
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Results);
        }
        else{
            Results.put("success", false); //执行失败
            Results.put("message", "用户名或密码错误！"); //用户名或密码错误！
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Results);
        }
    }
}
