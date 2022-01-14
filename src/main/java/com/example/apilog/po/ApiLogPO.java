package com.example.apilog.po;

import lombok.Data;

@Data
public class ApiLogPO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 进程ID
     */
    private String tid;
    /**
     * 请求URL
     */
    private String requestUrl;
    /**
     * 请求方法
     */
    private String method;
    /**
     * URL描述
     */
    private String urlDesc;
    /**
     * 请求返回结果
     */
    private String result;
    /**
     * 请求开始时间
     */
    private String startTime;
    /**
     * 请求耗时
     */
    private Integer costTime;
    /**
     * 客户机IP
     */
    private String clientIp;
    /**
     * 服务端IP
     */
    private String serverIp;

    public ApiLogPO() {
    }

    @Override
    public String toString() {
        return "ApiLogPO { " +
                "userId=" + getUserId() + ", " +
                "tid=" + getTid() + ", " +
                "requestUrl=" + getRequestUrl() + ", " +
                "method=" + getMethod() + ", " +
                "urlDesc=" + getUrlDesc() + ", " +
                "result=" + getResult() + ", " +
                "startTime=" + getStartTime() + ", " +
                "costTime=" + getCostTime() +
                "clientIp=" + getClientIp() + ", " +
                "serverIp=" + getServerIp() + " " +
                '}';
    }
}

