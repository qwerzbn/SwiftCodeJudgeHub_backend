package com.zbn.zbnojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zbn.zbnojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.zbn")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zbn.zbnojbackendserviceclient.service"})
public class ZbnojBackendUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZbnojBackendUserServiceApplication.class, args);
    }

}
