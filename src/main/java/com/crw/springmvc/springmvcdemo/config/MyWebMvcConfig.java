package com.crw.springmvc.springmvcdemo.config;

import com.crw.springmvc.springmvcdemo.converter.MyHttpMessageConverter;
import com.crw.springmvc.springmvcdemo.intercepter.MyIntercepter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("com.crw.springmvcdemo")
public class MyWebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MyIntercepter myIntercepter() {
        return new MyIntercepter();
    }

    /**
     * 文件上传下载解析
     *
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMaper = new ObjectMapper();
        //设置null值不参与序列化(字段不被显示)
        objectMaper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 禁用空对象转换json校验
        objectMaper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMaper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //驼峰命名法转换为小写加下划线
       /* objectMaper.setPropertyNamingStrategy( PropertyNamingStrategy.SNAKE_CASE );
        converter.setObjectMapper(objectMaper);*/
        return converter;
    }

    @Bean
    public MyHttpMessageConverter myHttpMessageConverter() {
        return new MyHttpMessageConverter();
    }

    /**
     * 注册消息解析器
     * 此重载会覆盖掉 spring mvc默认注册的多个 HttpMessageConverter
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

    /**
     * 注册消息解析器
     * 仅添加一个自定义的HttpMessageConverter，不会覆盖掉默认注册的
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(myHttpMessageConverter());
    }

    /**
     * 静态资源映射
     * addResourceHandler 添加对外暴露访问路径
     * addResourceLocations 文件放置位置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myIntercepter()).addPathPatterns("/hello*");
    }

    /**
     * 处理无业务逻辑，仅页面跳转
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/index");
    }

    /**
     * 路径匹配参数设置
     *
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);// 路径 /aa/xx.yy 可接受.yy了
    }
}
