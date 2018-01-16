package com.senpure.base.init;

import com.senpure.base.cache.LocalRemoteCacheManager;
import com.senpure.base.spring.Spring;
import com.senpure.base.spring.SpringContextRefreshEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by 罗中正 on 2017/7/27.
 */
@Component
@Order(99999)
public class SystemInfoPrinter extends SpringContextRefreshEvent {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

       CacheManager cacheManager= Spring.getBean(CacheManager.class);
        if (cacheManager instanceof LocalRemoteCacheManager) {
            LocalRemoteCacheManager localRemoteCacheManager = (LocalRemoteCacheManager) cacheManager;
            logger.info("采用spring cache 本机缓存 {}",localRemoteCacheManager.getLocalNames());
        }

    }
}
