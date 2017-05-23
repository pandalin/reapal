package com.jvv.reapal.core.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor

/**
 * Created by IntelliJ IDEA
 * <p>〈类详细描述〉 </p>
 * 〈功能详细描述〉
 *
 * @author linxm
 * @date 2017/5/9
 * @time 16:14
 * @version 1.0
 */
@Configuration
@EnableAsync
@EnableScheduling
class ReaPalTaskExecutor implements AsyncConfigurer,SchedulingConfigurer{

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler()
        scheduler.poolSize = 20
        scheduler.threadNamePrefix = "task-"
        scheduler.awaitTerminationSeconds = 60
        scheduler.waitForTasksToCompleteOnShutdown = Boolean.TRUE
        scheduler.rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy()
        scheduler.initialize()
        return scheduler
    }

    @Override
    Executor getAsyncExecutor() {
        return this.taskScheduler()
    }

    @Override
    AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler()
    }

    @Override
    void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.taskScheduler = this.taskScheduler()
    }
}
