package com.project.wab.config;

import com.project.wab.filter.AnonymousUserFilter;
import com.project.wab.service.CartService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author "Vladyslav Paun"
 */
@Configuration
public class FilterConfig {

    private final CartService cartService;

    public FilterConfig(CartService cartService) {
        this.cartService = cartService;
    }

    @Bean
    public FilterRegistrationBean<AnonymousUserFilter> anonymousUserFilter() {
        FilterRegistrationBean<AnonymousUserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AnonymousUserFilter(cartService));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("AnonymousUserFilter");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}