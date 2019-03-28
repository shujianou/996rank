package com.rank.admin.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by Vim 2019/3/28 0028 下午 9:13
 *
 * @author Vim
 */
public class AppRedisSerializer implements RedisSerializer<Object> {


    @Override
    public byte[] serialize(Object value) throws SerializationException {
        if (value instanceof Integer) {
            return String.valueOf(value).getBytes(charset);
        }
        return ((String)value).getBytes(charset);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }

    public AppRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public AppRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    private final Charset charset;

}
