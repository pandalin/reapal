package com.jvv.reapal.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 16:19
 * @version 1.0
 */
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.jvv.reapal.*")
@ComponentScan("com.jvv.reapal.*")
@EntityScan("com.jvv.reapal.*")
@EnableTransactionManagement
class ReaPalApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ReaPalApplication.class)
    }

    static void main(String[] args) {
        SpringApplication.run(ReaPalApplication.class,args)
    }
}
