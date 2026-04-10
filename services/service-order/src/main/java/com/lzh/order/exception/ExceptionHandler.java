package com.lzh.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lzh.common.Error;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class ExceptionHandler implements BlockExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       String s, BlockException e) throws Exception {
        httpServletResponse.setStatus(429);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        Error error = Error.error(500,s+"服务已被sentinel异常处理了:"+e.getClass());
        writer.write(objectMapper.writeValueAsString(error));
        writer.flush();
        writer.close();
    }
}
