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
public class RegUserController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/api/userinfo/RegUser")
    public Object Login(String user_no, String user_pwd){
        Map<String, Object> Results = new HashMap<>();

        List<Map<String, Object>> Result = jdbcTemplate.queryForList("SELECT * FROM userinfo WHERE user_no = ? AND user_pwd = ?", user_no, user_pwd);

        if (Result != null && Result.stream().count() > 0){ //找到数据行
            Results.put("success", false);
            Results.put("message", "用户名已存在！"); //用户名已存在！
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Results);
        }
        else{
            int rowsAffected = jdbcTemplate.update("INSERT INTO userinfo(user_no, user_pwd) VALUES (?, ?)", user_no, user_pwd);

            if (rowsAffected > 0){
                Results.put("success", true);
                Results.put("code", 200);
                Results.put("message", "注册成功！");
            }
            else{
                Results.put("success", false);
                Results.put("message", "注册失败！请重试");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Results);
    }
}
