package com.crw.springmvc.springmvcdemo.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class MyHttpMessageConverter extends AbstractHttpMessageConverter<MyMsgObj> {


    public MyHttpMessageConverter() {
        // 自定义媒体类型 application/x-crw
        super(new MediaType("application", "x-crw", Charset.forName("UTF-8")));
    }

    /**
     * 处理请求数据
     */
    @Override
    protected MyMsgObj readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("utf-8"));
        String[] split = temp.split("-");
        return new MyMsgObj(split[0], split[1]);
    }

    /**
     * 表面本HttpMessageConverter只处理MyMsgObj这个类
     */
    @Override
    protected boolean supports(Class aClass) {
        return MyMsgObj.class.isAssignableFrom(aClass);
    }

    /**
     * 处理输出到response
     */
    @Override
    protected void writeInternal(MyMsgObj myMsgObj, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        myMsgObj.setCode("hello:" + myMsgObj.getCode());
        myMsgObj.setMsg("hello:" + myMsgObj.getMsg());
        httpOutputMessage.getBody().write(myMsgObj.toString().getBytes());
    }

}
