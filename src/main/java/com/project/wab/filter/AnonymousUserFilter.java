//package com.project.wab.filter;
//
//import com.project.wab.service.CartService;
//import jakarta.servlet.*;
//
//import java.io.IOException;
///**
// * @author "Vladyslav Paun"
// */
//public class AnonymousUserFilter implements Filter {
//
//    private final CartService cartService;
//
//    public AnonymousUserFilter(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
////        String token = null;
////        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
////
////            token = WebUtil.checkToken(httpRequest);
////
////            if (token == null) {
////                token = UUID.randomUUID().toString();
////
////                cartService.createCart(token);
////
////                Cookie cookie = WebUtil.populateCookie(token);
////                httpResponse.addCookie(cookie);
////            }
////            Cookie cookie = WebUtil.populateCookie(token);
////            httpResponse.addCookie(cookie);
////        }
//
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
