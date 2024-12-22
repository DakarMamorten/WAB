package com.project.wab.filter;

import com.project.wab.service.CartService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class AnonymousUserFilter implements Filter {

    private final CartService cartService;

    public AnonymousUserFilter(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void init(FilterConfig filterConfig)  {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {

            String token = null;
            if (httpRequest.getCookies() != null) {
                token = Arrays.stream(httpRequest.getCookies())
                        .filter(cookie -> "anonymousUserToken".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst()
                        .orElse(null);
            }

            if (token == null) {
                token = UUID.randomUUID().toString();

                cartService.createCart(token);

                Cookie cookie = new Cookie("anonymousUserToken", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 days
                httpResponse.addCookie(cookie);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
