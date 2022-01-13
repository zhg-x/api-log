package com.example.apilog.resource;


import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin // 使用此注解解决方法级别的跨域问题
public interface ApiLogTestResource {

    @GetMapping("/apiLogTest")
    String apiLogTest(@RequestParam(value = "param1", required = false) String param1, @RequestParam(value = "param2", required = false) String param2);

    @PostMapping("/apiLogTest")
    String apiLogPostTest(@RequestBody Map<String, Object> map);

}
