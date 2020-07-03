package net.onebean.sso.sdk.core;

import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 * @author 0neBean
 */
@WebFilter(filterName = "ssoLoginFilter")
public class SsoLoginFilter implements Filter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    private static final Logger LOGGER = LoggerFactory.getLogger(SsoLoginFilter.class);




    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (pathMatcher.match("/**/*.html", request.getRequestURI())) {
            // 拦截静态资源
            try {
                String resUri = SsoService.getInstance().checkLoginStatus(request);
                if (StringUtils.isNotEmpty(resUri)){
                    LOGGER.info("checkLoginStatus failure , current url is = "+request.getRequestURL());
                    LOGGER.info("checkLoginStatus failure , request will redirect to "+resUri);
                    response.sendRedirect(resUri);
                }
            } catch (Exception e) {
                LOGGER.error("checkLoginStatus got a err is ,gonna resend redirect", e);
                response.sendRedirect(SsoService.getInstance().generateSsoCompleteUrl());
            }
        }
        //执行操作后必须doFilter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}



