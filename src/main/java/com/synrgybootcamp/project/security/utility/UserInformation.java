package com.synrgybootcamp.project.security.utility;

import com.synrgybootcamp.project.security.middleware.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserInformation {
    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    public String getUserID() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return jwtTokenUtility.getUserIDFromJwtToken(jwtTokenFilter.getTokenFromRequest(request));
    }
}
