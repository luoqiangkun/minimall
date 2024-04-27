package org.linlinjava.litemall.admin.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyUserFilter extends UserFilter {

    private final Log logger = LogFactory.getLog(MyUserFilter.class);

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        logger.info("redirectToLoginredirectToLogin");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpStatus.OK.value());
        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.getWriter().write( objectMapper.writeValueAsString(ResponseUtil.unlogin()) );
    }
}
