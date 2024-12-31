package com.craftly.jwtmodule;

public class JwtUtil {

    private JwtUtil() {}

    public static JwtAuthentication getAuthentication(String subject) {
        final JwtAuthentication jwtAuthInfo = new JwtAuthentication();
        jwtAuthInfo.setEmail(subject);

        return jwtAuthInfo;
    }
}