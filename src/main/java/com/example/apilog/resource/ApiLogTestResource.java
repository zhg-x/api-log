package com.example.apilog.resource;


import com.example.apilog.dto.DTO;
import com.example.apilog.annotation.ApiDescription;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测试接口日志功能
 */
@CrossOrigin // 使用此注解解决方法级别的跨域问题
public interface ApiLogTestResource {

    @ApiDescription(value = "apiLogTest get 接口测试", notes = "注解测试")
    @GetMapping("/apiLogTest")
    DTO apiLogTest(@RequestParam(value = "param1", required = false) String param1, @RequestParam(value = "param2", required = false) String param2);

    @ApiDescription(value = "apiLogTest post 接口注解测试")
    @PostMapping("/apiLogTest")
    DTO apiLogPostTest(@RequestBody Map<String, Object> map);

}
