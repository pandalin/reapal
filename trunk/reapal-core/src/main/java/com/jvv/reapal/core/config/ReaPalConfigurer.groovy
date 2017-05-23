package com.jvv.reapal.core.config

import com.jvv.reapal.core.interceptor.HeadInterceptor
import com.jvv.reapal.core.interceptor.SignInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import javax.annotation.Resource
import java.nio.charset.Charset

/**
 * Created by IntelliJ IDEA
 * <p>〈配置信息〉 </p>
 * 〈配置信息〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:08
 * @version 1.0
 */
@Configuration
class ReaPalConfigurer extends WebMvcConfigurerAdapter{

    @Resource
    private SignInterceptor signInterceptor
    @Resource
    private HeadInterceptor headInterceptor

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headInterceptor).addPathPatterns("/**")
        registry.addInterceptor(signInterceptor).addPathPatterns("/reapal/**")
    }

    @Override
    void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters)
        converters.add(responseBodyConverter());
    }

    @Bean
    HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"))
        return converter;
    }
}
