package net.qqxh.sunflow.server.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Copyright (C), 2019/5/29, sunflow开发团队
 * 〈自定义shrio过滤器〉<br>
 * 〈自定义roles过滤器，覆盖shiro自带roles过滤器〉
 *
 * @author jason
 * @fileName: CustomRolesAuthorizationFilter.java
 * @date: 2019/5/29 20:26
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }

        return false;
    }

}
