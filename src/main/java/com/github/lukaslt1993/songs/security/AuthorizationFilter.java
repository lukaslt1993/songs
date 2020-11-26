package com.github.lukaslt1993.songs.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private String tokenSecret;

    private UserDetailsService userDetailsService;
    
    public AuthorizationFilter(AuthenticationManager authManager,
                               String tokenSecret,
                               UserDetailsService userDetailsService) {
        super(authManager);
        this.tokenSecret = tokenSecret;
        this.userDetailsService = userDetailsService;
     }
    
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        
        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }   
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        
        if (token != null) {
            
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            
            String userName = Jwts.parser()
                    .setSigningKey( tokenSecret )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();

            if (userName != null) {
                UserDetails user = userDetailsService.loadUserByUsername(userName);
                return new UsernamePasswordAuthenticationToken(user,null, new ArrayList<>());
            }

        }
        
        return null;
    }
    
}
 