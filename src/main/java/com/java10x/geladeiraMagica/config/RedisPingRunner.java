package com.java10x.geladeiraMagica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;

@Component
public class RedisPingRunner implements CommandLineRunner {
    @Autowired(required = false)
    private StringRedisTemplate template;

    @Override
    public void run(String... args) {
        if (template == null) {
            return;
        }
        try {
            template.getRequiredConnectionFactory().getConnection().ping();
        } catch (Exception ignored) {
        }
    }
}
