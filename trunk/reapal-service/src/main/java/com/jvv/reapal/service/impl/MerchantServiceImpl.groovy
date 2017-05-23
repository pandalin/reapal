package com.jvv.reapal.service.impl

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.google.common.cache.RemovalListener
import com.google.common.cache.RemovalNotification
import com.jvv.reapal.dao.MerchantDao
import com.jvv.reapal.model.entity.Merchant
import com.jvv.reapal.service.MerchantService
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

import javax.annotation.Resource
import java.util.concurrent.TimeUnit

/**
 * Created by IntelliJ IDEA
 * <p>〈商户〉 </p>
 * 〈商户〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:41
 * @version 1.0
 */
@Service("merchantService")
@Slf4j
class MerchantServiceImpl implements MerchantService{

    @Resource
    private MerchantDao merchantDao

    //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
    LoadingCache<String, Merchant> cache =
        //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        CacheBuilder.newBuilder()
        //设置并发级别为8，并发级别是指可以同时写缓存的线程数
        .concurrencyLevel(8)
        //设置写缓存后30分钟过期
        .expireAfterWrite(30, TimeUnit.MINUTES)
        //设置缓存容器的初始容量为10
        .initialCapacity(10)
        //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
        .maximumSize(100)
        //设置要统计缓存的命中率
        .recordStats()
        //设置缓存的移除通知
        .removalListener(new RemovalListener<Object, Object> () {
            @Override
            void onRemoval(RemovalNotification<Object, Object> notification) {
                log.info("{} was removed, cause is {}", notification.getKey(), notification.getCause())
            }
        })
        //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
        .build(
        new CacheLoader<String, Merchant> () {
            @Override
            Merchant load(String partnerId) throws Exception {
                return findMerchantByPartnerId(partnerId);
            }
        }
    )

    Merchant findMerchantByPartnerId(String partnerId) {
        return merchantDao.findMerchantByPartnerId(partnerId)
    }

    Merchant loadFromCache(String partnerId) {
        try {
            return cache.get(partnerId);
        } catch (Exception e) {
            if(e instanceof CacheLoader.InvalidCacheLoadException){
                return null;
            }
            return findMerchantByPartnerId(partnerId);
        }
    }
}
