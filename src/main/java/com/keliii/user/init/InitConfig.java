package com.keliii.user.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by keliii on 2017/6/21.
 */
@Configuration
public class InitConfig {

    @Bean
    public PermissionInit getPermissionInit() {
        PermissionInit init = new PermissionInit();
        return init;
    }

}
