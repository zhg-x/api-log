package com.example.apilog.resource.impl;

import com.example.apilog.resource.ApiLogTestResource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ApiLogTestResourceImpl implements ApiLogTestResource {


    @Override
    public String apiLogTest(String param1, String param2) {
        return "正在调用的方法" + this.getClass().getPackage().getName() + " " + Thread.currentThread() .getStackTrace()[1].getMethodName();
    }

    @Override
    public String apiLogPostTest(Map<String, Object> map) {
        return "正在调用的方法" + this.getClass().getPackage().getName() + " " + Thread.currentThread() .getStackTrace()[1].getMethodName();
    }

}


