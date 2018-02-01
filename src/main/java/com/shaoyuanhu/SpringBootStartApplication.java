package com.shaoyuanhu;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @Author: ShaoYuanHu
 * @Description:
 * @Date: Create in 2018-02-01 14:46
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的StartApplication启动类
        return builder.sources(StartApplication.class);
    }

}
