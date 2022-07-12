package com.basic.cloud.uaa.boot;

import com.alibaba.fastjson.JSON;
import com.basic.cloud.common.contstant.ExceptionEnum;
import com.basic.cloud.common.vo.ResultData;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class UaaAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        ResultData resultData = ResultData.error(ExceptionEnum.SERVICE_ERROR.getErrorMsg(), e.getMessage());
        response.getWriter().print(JSON.toJSONString(resultData));
        response.getWriter().flush();
    }
}
