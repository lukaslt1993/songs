package com.github.lukaslt1993.songs.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstants {

    public enum Role {
        ADMIN("ADMIN"),
        USER("USER");

        private String role;

        Role(String role) {
            this.role = role;
        }

        public String getValue() {
            return role;
        }
    }

    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
}
