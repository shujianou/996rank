package com.rank.config;

import com.rank.admin.serializer.AppRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Vim 2019/3/28 0028 下午 8:48
 *
 * @author Vim
 */
@Configuration
public class AppConfig {
    /**
     * 设置 redisTemplate 的序列化设置
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 1.创建 redisTemplate 模版
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        AppRedisSerializer appRedisSerializer = new AppRedisSerializer();
        // 2.关联 redisConnectionFactory
        template.setConnectionFactory(redisConnectionFactory);
        // 6.序列化类，对象映射设置
        // 7.设置 value 的转化格式和 key 的转化格式
        template.setValueSerializer(appRedisSerializer);
        template.setKeySerializer(appRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
