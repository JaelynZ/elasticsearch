package com.search.common.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/7/9 9:12
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {

/*    @Autowired
    private InterceptorConfig config;*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
//        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("api/path/**").excludePathPatterns("api/path/login");
//        registry.addInterceptor(config).addPathPatterns("/**");
        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
