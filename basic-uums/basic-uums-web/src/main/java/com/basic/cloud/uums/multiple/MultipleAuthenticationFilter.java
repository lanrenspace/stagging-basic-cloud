package com.basic.cloud.uums.multiple;

import com.basic.cloud.uums.multiple.authenticator.MultipleAuthenticator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
@Component
public class MultipleAuthenticationFilter extends GenericFilterBean implements ApplicationContextAware {

    private static final String AUTH_TYPE_PARAM_NAME = "auth_type";

    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private Collection<MultipleAuthenticator> authenticators;

    private ApplicationContext applicationContext;

    private RequestMatcher requestMatcher;

    public MultipleAuthenticationFilter() {
        this.requestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"),
                new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST")
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String grantType = request.getParameter("grant_type");
        if (requestMatcher.matches(request) && "password".equals(grantType)) {
            MultipleAuthentication multipleAuthentication = new MultipleAuthentication();
            multipleAuthentication.setAuthType(request.getParameter(AUTH_TYPE_PARAM_NAME));
            multipleAuthentication.setAuthParameters(request.getParameterMap());
            MultipleAuthenticationContext.set(multipleAuthentication);
            try {
                this.pre(multipleAuthentication, response);
                filterChain.doFilter(request, response);
                this.complete(multipleAuthentication);
            } finally {
                MultipleAuthenticationContext.clear();
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 预处理
     *
     * @param multipleAuthentication
     * @param response
     */
    private void pre(MultipleAuthentication multipleAuthentication, HttpServletResponse response) {
        if (this.authenticators == null) {
            synchronized (this) {
                Map<String, MultipleAuthenticator> integrationAuthenticatorMap = applicationContext.getBeansOfType(MultipleAuthenticator.class);
                this.authenticators = integrationAuthenticatorMap.values();
            }
        }
        for (MultipleAuthenticator authenticator : authenticators) {
            if (authenticator.support(multipleAuthentication)) {
                authenticator.pre(multipleAuthentication, response);
            }
        }
    }


    /**
     * 后置处理
     *
     * @param multipleAuthentication
     */
    private void complete(MultipleAuthentication multipleAuthentication) {
        for (MultipleAuthenticator authenticator : authenticators) {
            if (authenticator.support(multipleAuthentication)) {
                authenticator.complete(multipleAuthentication);
            }
        }
    }
}
