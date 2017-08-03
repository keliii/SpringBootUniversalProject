package com.keliii.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by keliii on 2017/6/26.
 */
@Configuration
@MapperScan({"com.keliii.news.mapper",
		"com.keliii.user.mapper"})
@EnableTransactionManagement
public class MybatisConfig {
}
