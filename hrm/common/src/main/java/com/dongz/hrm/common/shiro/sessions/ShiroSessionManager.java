package com.dongz.hrm.common.shiro.sessions;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author dong
 * @date 2020/2/20 00:27
 * @desc
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 获取请求头中authorization中的信息
        String id = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(id)) {
            // 如果没有携带，生成新的id
            return super.getSessionId(request, response);
        } else {
             // 返回sessionId
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            return id;
        }
    }
}
