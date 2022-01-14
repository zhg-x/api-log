package com.example.apilog.resource.impl;

import com.example.apilog.dto.DTO;
import com.example.apilog.resource.ApiLogTestResource;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class ApiLogTestResourceImpl implements ApiLogTestResource {


    @Override
    public DTO apiLogTest(String param1, String param2) {
        DTO dto = new DTO();
        dto.setRetCode("000000");
        dto.setRetMsg("成功");
        return dto;
    }

    @Override
    public DTO apiLogPostTest(Map<String, Object> map) {
        DTO dto = new DTO();
        dto.setRetCode("999999");
        dto.setRetMsg("操作失败");
        return dto;
    }

}


