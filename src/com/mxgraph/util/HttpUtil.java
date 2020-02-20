package com.mxgraph.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class HttpUtil {

    private static final Logger log = Logger.getLogger(HttpUtil.class.getName());

    public static void  response(HttpServletResponse response , Object content){
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
//        ServletOutputStream out = null;
        PrintWriter writer=null;
        try {
            // JSON.toJSONString(result)要获取完整的json字符串，每一个字段都要有set和get方法，不然会缺少某个字段
//            response.getWriter().write(JSON.toJSONString(result));
            writer=response.getWriter();
//            writer.write(result.toString());
            writer.write(JSON.toJSONString(content));
            writer.flush();
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }finally {
            if(writer!=null) {
                writer.close();
            }
        }
    }

}
