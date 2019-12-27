package com.example.sharefood.conf;

import com.example.sharefood.conf.intercepors.IndexBlogInterceptor;
import com.example.sharefood.conf.intercepors.IndexFoodInterceptor;
import com.example.sharefood.conf.intercepors.IndexInterceptor;
import com.example.sharefood.conf.intercepors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private IndexInterceptor indexInterceptor;
    @Autowired
    private IndexFoodInterceptor indexFoodInterceptor;
    @Autowired
    private IndexBlogInterceptor indexBlogInterceptor;
    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(indexInterceptor).addPathPatterns("/","/detailfood");
        registry.addInterceptor(indexFoodInterceptor).addPathPatterns("/food");
        registry.addInterceptor(indexBlogInterceptor).addPathPatterns("/foodblog");
        //关闭了拦截器，项目完成后开启
    }

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/images/users/**").addResourceLocations("file:E:/sharefood/src/main/resources/static/images/users/");
        registry.addResourceHandler("/images/tujian/**").addResourceLocations("file:E:/sharefood/src/main/resources/static/images/tujian/");
        registry.addResourceHandler("/images/meishi/**").addResourceLocations("file:E:/sharefood/src/main/resources/static/images/meishi/");
        /*TODO 项目转移到其他文件夹时 这里的路劲需要更改*/
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/food").setViewName("index-1");
        registry.addViewController("/detailfood").setViewName("index-2");
        registry.addViewController("/foodblog").setViewName("index-3");
        registry.addViewController("/login").setViewName("module/login");
   }

}
