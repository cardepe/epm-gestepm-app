package com.epm.gestepm.lib.http.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static void addCookie(final String name, final String value, final HttpServletResponse response) {

        final Cookie cookie = new Cookie(name, value);

        response.addCookie(cookie);
    }

    public static void addCookie(final String name, final String value, final Integer maxAge, final HttpServletResponse response) {

        final Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void removeCookie(final String name, final HttpServletResponse response) {

        final Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
