package com.project.wab.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

/**
 * @author "Vladyslav Paun"
 */
public class WebUtil {

    public static final int MAX_AGE = 60 * 60 * 24;//24 hours
    public static final String CART_TOKEN = "cartToken";

    private WebUtil() {

    }

    public static String checkToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> CART_TOKEN.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public static Cookie populateCookie(String token) {
        Cookie cookie = new Cookie(CART_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE);
        return cookie;
    }

    public static Cookie removeCookie() {
        Cookie removeCookie = new Cookie(CART_TOKEN, "");
        removeCookie.setPath("/");
        removeCookie.setMaxAge(0);
        return removeCookie;
    }
}
