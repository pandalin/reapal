package com.jvv.reapal.core.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
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
class ReaPalExecutor implements AsyncConfigurer,SchedulingConfigurer{

    @Bean
    ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler()
        scheduler.poolSize = 20
        scheduler.threadNamePrefix = 'reapal-task-'
        scheduler.awaitTerminationSeconds = 60
        scheduler.waitForTasksToCompleteOnShutdown = Boolean.TRUE
        scheduler.rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy()
        scheduler.initialize()
        return scheduler
    }

    @Bean
    Executor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 20
        executor.queueCapacity = 10
        executor.threadNamePrefix = 'reapal-executor-'
        executor.initialize()
        return executor
    }

    @Override
    Executor getAsyncExecutor() {
        return this.threadPoolTaskExecutor()
    }

    @Override
    AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new ReaPalAsyncUncaughtExceptionHandler()
    }

    @Override
    void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.taskScheduler = this.taskScheduler()
    }
}
