package com.toy.pbzuul;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class FilterZuul extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse servletResponse = context.getResponse();

        FirebaseToken decodedToken = null;
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7, authorization.length());
        }

        try {
            if (bearerToken != null && !bearerToken.equalsIgnoreCase("undefined")) {
                decodedToken = firebaseAuth.verifyIdToken(bearerToken);
                String uid = decodedToken.getUid();
                context.addZuulRequestHeader("uid", uid);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        return null;
    }
}
