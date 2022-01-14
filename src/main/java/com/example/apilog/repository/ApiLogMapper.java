package com.example.apilog.repository;

import com.example.apilog.po.ApiLogPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiLogMapper {

    @Insert({
            "insert into SYS_WEB_LOG (" +
                    "USER_ID, TID, REQUEST_URL, METHOD, URL_DESC, RESULT, " +
                    "START_TIME, COST_TIME, CLIENT_IP, SERVER_IP) values (" +
                    "#{userId,jdbcType=VARCHAR}, #{tid,jdbcType=VARCHAR}, #{requestUrl,jdbcType=VARCHAR}, " +
                    "#{method,jdbcType=VARCHAR}, #{urlDesc,jdbcType=VARCHAR},  #{result,jdbcType=VARCHAR}, " +
                    "#{startTime,jdbcType=VARCHAR}, #{costTime,jdbcType=VARCHAR}, #{clientIp,jdbcType=VARCHAR}, " +
                    "#{serverIp,jdbcType=VARCHAR} " +
                    ")"})
    int insert(ApiLogPO apiLogPO);


}
