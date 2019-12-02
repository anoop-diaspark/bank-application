package com.diaspark.INB.filter;

import com.diaspark.INB.config.JwtTokenProvider;
import com.diaspark.INB.entity.UserPrincipal;
import com.diaspark.INB.exception.UnauthorizedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTAuthFilter extends SecurityContextHolderAwareRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JWTAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

//        try {
//            HttpServletRequest request = (HttpServletRequest) req;
//
//            //extract token on header or cookie
//            String authToken = extractToken(request);
//
//            //extract userPrincipal object from token
//            UserPrincipal userPrincipal = jwtTokenProvider.getUserPrincipal(authToken);
//            if (userPrincipal != null) {
//
//                //create authorities to be set in spring authentication
//                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//                grantedAuthorities.addAll(userPrincipal.getAuthorities());
//
//                //create authentication object and set the spring security context with authentication object
//                JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(userPrincipal.getAuthorities(), userPrincipal, authToken);
//                jwtAuthenticationToken.setAuthenticated(true);
//                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        chain.doFilter(req, res);
    }


    private String extractToken(HttpServletRequest request) {
    	 System.out.println("Get header xAuth" + request.getHeader("AUTH-TOKEN"));
         System.out.println("Get header authorization" + request.getHeader("Authorization"));
        if (request.getHeader("AUTH-TOKEN") != null) {
            return request.getHeader("AUTH-TOKEN");
        } else if (request.getCookies() != null) {
            return this.getValue(request.getCookies(), "AUTH-TOKEN");
        }

        throw new UnauthorizedException("No token found on request");
    }

    private String getValue(Cookie[] cookies, String name) {
        
        for (Cookie cookie : cookies) {
        	System.out.println("cookies key:" + cookie.getName() + "value:" + cookie.getValue());
            if (cookie.getName().equalsIgnoreCase(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
