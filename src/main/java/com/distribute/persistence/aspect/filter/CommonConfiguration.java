package com.distribute.persistence.aspect.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author swzxsyh
 */
@Configuration
public class CommonConfiguration {

    @Bean
    public FilterRegistrationBean<CustomFilter> registerCustomFilter(){
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.setName("CustomFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.setEnabled(Boolean.TRUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
