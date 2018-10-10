package com.auth.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AuthorisationFilter implements Filter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();

        String jwtToken = fetchToken(cookies, "jwtToken");
        String refToken = fetchToken(cookies, "refToken");
        System.out.println("jwt                    " + jwtToken);
        System.out.println("ref                    " + refToken);
        try {
            if (jwtTokenService.verifyJWT(jwtToken) == null) {

                try {
                    if (jwtTokenService.regenerateJwt(refToken) == null) {
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid or expired.");
                    } else {
                        updateJwtToken(req, refToken);
                        chain.doFilter(request, response);
                    }

                } catch (Exception ex) {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid or expired.");
                }

            }

        } catch (Exception e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid or expired.");
        }

        chain.doFilter(request, response);


    }

    private String fetchToken(Cookie[] cookies, String tokenType) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenType)) {
                    return cookie.getValue();

                }
            }
        }
        return null;
    }

    private void updateJwtToken(HttpServletRequest req, String refToken) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    cookie.setValue(jwtTokenService.regenerateJwt(refToken));
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Bean
    public FilterRegistrationBean<AuthorisationFilter> loggingFilter() {
        FilterRegistrationBean<AuthorisationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorisationFilter());
        registrationBean.addUrlPatterns("/resource/*");
        registrationBean.addUrlPatterns("/resource1/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return registrationBean;
    }


}
