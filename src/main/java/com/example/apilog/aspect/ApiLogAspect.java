package com.example.apilog.aspect;

import com.alibaba.fastjson.JSON;
import com.example.apilog.annotation.ApiDescription;
import com.example.apilog.po.ApiLogPO;
import com.example.apilog.repository.ApiLogMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ApiLogAspect {

    // 使用指定类初始化日志对象，输出日志时可以打印出日志信息所在的类
    private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);
    private ThreadLocal<Integer> serial = ThreadLocal.withInitial(() -> 0);

    @Resource
    private ApiLogMapper apiLogMapper;

    // 定义切点以及切点表达式，用来匹配指定的方法，更多用法请参考：https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pointcuts
    @Pointcut("execution(* com..resource..*(..))")
    public void logPointcut() {
    }

    @Around("(logPointcut())")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        this.serial.set((Integer) this.serial.get() + 1);
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        this.serial.set((Integer) this.serial.get() - 1);
        // 使用new GsonBuilder().serializeNulls().create()生成的Gson对象会保留被转换对象中的null值
        Gson gson = new GsonBuilder().serializeNulls().create();
        if ((Integer) this.serial.get() == 0) {
            try {
                // 接收到请求，获取请求信息(包含request、response、session等信息)
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes == null) {
                    return result;
                }
                ApiLogPO apiLogPO = new ApiLogPO();
                if (result != null) {
                    apiLogPO.setResult(JSON.parseObject(gson.toJson(result)).getString("retCode"));
                }
                // 获取request，可以在其中获取session、requestUrl等信息
                HttpServletRequest request = attributes.getRequest();
                apiLogPO.setClientIp(this.getRealIp(request));
                apiLogPO.setServerIp(InetAddress.getLocalHost().getHostAddress());
                apiLogPO.setMethod(request.getMethod());
                apiLogPO.setRequestUrl(request.getRequestURL().toString());
                apiLogPO.setUserId(request.getHeader("userId"));
                apiLogPO.setTid(String.valueOf(request.getAttribute("tid")));

                // 从切面连接点处通过反射机制获取连接点处的方法(Method对象中包括方法名称、描述符、参数、返回类型和异常等信息)
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                // method.getAnnotation(ApiDescription.class) ==> 返回null
                ApiDescription apiDescription = AnnotatedElementUtils.findMergedAnnotation(method, ApiDescription.class);
                if (apiDescription != null) {
                    apiLogPO.setUrlDesc(apiDescription.value());
                }
//                log.info("连接点的方法：" + method);
//                log.info("连接点方法的参数：" + gson.toJson(this.getParameter(joinPoint)));
                apiLogPO.setStartTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(startTime));
//                log.info("方法执行的结束时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(endTime));
                apiLogPO.setCostTime((int) (endTime - startTime));
                log.info("{}", apiLogPO);

                apiLogMapper.insert(apiLogPO);
            } catch (Exception e) {
                log.error("api log aspect occurred exception: ", e);
            }
        }
        return result;
    }

    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip) ? ip : request.getRemoteAddr();
        }
    }

    private Object getParameter(ProceedingJoinPoint joinPoint) {
        List<Object> argList = new ArrayList<>();
        // 获取连接点处签名方法的参数
        Parameter[] parameters = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
        // 获取连接点方法的实际参数
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            // 创建一个map对象存储参数名和参数值
            Map<String, Object> map = new HashMap<>();
            map.put(parameters[i].getName(), args[i]);
            argList.add(map);
        }
        return argList;
    }

}
