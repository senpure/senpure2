package com.senpure.base.configuration;

import com.senpure.base.configuration.BaseConfiguration;
import com.senpure.base.cache.LocalRemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * @author senpure-generator
 * @version 2018-1-16 16:02:36
 */
@Configuration
@ConditionalOnClass({RedisTemplate.class})
@ConditionalOnBean(CacheManager.class)
public class LocalCacheConfiguration extends BaseConfiguration {

    private String[] localNames = {"rolePermission", "menu", "roleValue", "permissionMenu", "accountRole", "role", "permission", "systemValue", "accountValue", "containerPermission"};
    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        if (cacheManager instanceof LocalRemoteCacheManager) {
            LocalRemoteCacheManager localRemoteCacheManager = (LocalRemoteCacheManager) cacheManager;
            for (String name : localNames) {
                localRemoteCacheManager.addLocalName(name);
            }
        }
    }
}