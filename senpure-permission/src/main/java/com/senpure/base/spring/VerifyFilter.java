package com.senpure.base.spring;

import com.senpure.base.PermissionConstant;
import com.senpure.base.controller.LoginController;
import com.senpure.base.model.Account;
import com.senpure.base.model.Permission;
import com.senpure.base.model.URIPermission;
import com.senpure.base.result.Result;
import com.senpure.base.result.ResultHelper;
import com.senpure.base.result.ResultMap;
import com.senpure.base.service.AuthorizeService;
import com.senpure.base.service.PermissionService;
import com.senpure.base.service.ResourcesVerifyService;
import com.senpure.base.service.URIPermissionService;
import com.senpure.base.struct.LoginedAccount;
import com.senpure.base.struct.MergePermission;
import com.senpure.base.util.Http;
import com.senpure.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by 罗中正 on 2018/1/23 0023.
 */
@WebFilter(urlPatterns = "/*", filterName = "verifyFilter(")
public class VerifyFilter extends SpringContextRefreshEvent implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String loginURI = "/authorize/loginView";
    @Autowired
    private URIPermissionService uriPermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    private ResourcesVerifyService resourcesVerifyService;

    @Autowired
    private LoginController loginController;
    @Autowired
    @Qualifier("localeResolver")
    protected LocaleResolver localeResolver;
    private List<PatternsRequestCondition> patternsRequestConditions = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        initPatterns();
    }

    public void initPatterns()
    {
        List<URIPermission> uriPermissions = uriPermissionService.findAll();
        Map<Long, Set<String>> map = new HashMap<>(128);
        for (URIPermission uriPermission : uriPermissions) {
           Set<String> patterns = map.get(uriPermission.getPermissionId());
            if (patterns == null) {
                patterns = new HashSet<>();
                map.put(uriPermission.getPermissionId(), patterns);
            }
            String uri=uriPermission.getUriAndMethod();
           int index= StringUtil.indexOf(uriPermission.getUriAndMethod(), "[", 1, true);
            if (index > 0) {
                uri = uri.substring(0, index);
            }
            patterns.add(uri);
        }
        map.values().forEach(strings -> {
            String[] patterns = new String[strings.size()];
            strings.toArray(patterns);
            PatternsRequestCondition patternsRequestCondition = new PatternsRequestCondition(patterns);
            patternsRequestConditions.add(patternsRequestCondition);
        });
        for (PatternsRequestCondition patternsRequestCondition : patternsRequestConditions) {
            logger.info("verify patterns {}",patternsRequestCondition);
        }

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LoginedAccount account = Http.getSubject(request, LoginedAccount.class);
        if (account != null) {
            Account lastAccount = authorizeService.findAccount(account.getId());
            boolean login = account.getLoginTime() < lastAccount.getLoginTime();
            //  && !account.getLoginIP().equals(accountVo.getIp());
            if (login) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(loginURI);
                ResultMap result = ResultMap.result(Result.ACCOUNT_OTHER_LOGIN);
                ResultHelper.wrapMessage(result, localeResolver.resolveLocale(request), lastAccount.getIp() == null ? "UNKNOWN" : lastAccount.getIp());
                logger.info("由于在其他地方登陆，该次请求中断,跳转登陆界面");
                logger.debug(result.toString());
                afterLogin(request, result, false);
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                dispatcher.forward(request, response);
                return;
            }
        }
        PatternsRequestCondition match = null;
        for (PatternsRequestCondition patterns : patternsRequestConditions) {
            match = patterns.getMatchingCondition(request);
            if (match != null) {
                break;
            }
        }
        if (match != null) {
            String bestMatch = match.getPatterns().iterator().next();
            List<URIPermission> uriPermissions = uriPermissionService.findByUriAndMethodOnlyCache(bestMatch + "[" + request.getMethod() + "]");
            if (uriPermissions.size() == 0) {
                logger.debug("{} > {}", request.getRequestURI(), "不需要任何权限检查");
            } else {
                HttpServletResponse response = (HttpServletResponse) servletResponse;

                if (account == null) {
                    logger.debug("{} > {}", request.getRequestURI(), "没有登陆或者登陆超时");
                    account = loginController.autoLogin(request);
                    if (account == null) {
                        ResultMap result = ResultMap.result(Result.ACCOUNT_NOT_LOGIN_OR_SESSION_TIMEOUT);
                        RequestDispatcher dispatcher = request.getRequestDispatcher(loginURI);
                         ResultHelper.wrapMessage(result, localeResolver.resolveLocale(request));
                        afterLogin(request, result, false);
                        dispatcher.forward(request, response);
                        return;
                    } else {
                        logger.debug("auto 登陆成功");
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append(account.getAccount())
                        .append("[").append(account.getName()).append("] ")
                        .append(request.getMethod())
                        .append("  ")
                        .append(request.getRequestURI());

                boolean pass = false;
                List<Permission> needPermissions = new ArrayList<>();
                for (URIPermission uriPermission : uriPermissions) {
                    needPermissions.add(permissionService.find(uriPermission.getPermissionId()));
                }
                for (Permission permission : needPermissions) {
                    logger.debug("{} 需要 [ {} ] 权限[{},{}] ", sb.toString(), permission.getName(), permission.getReadableName(), permission.getType());
                    if (permission.getType().equals(PermissionConstant.PERMISSION_TYPE_NORMAL)) {
                        pass = hasPermission(account, permission.getName());
                    } else if (permission.getType().equals(PermissionConstant.PERMISSION_TYPE_OWNER)) {
                        pass = hasPermission(account, permission.getName());
                        if (pass&&permission.getOffset()!=null) {
                            String[] offsets = permission.getOffset().split(",");
                            String[] verifyNames = permission.getVerifyName().split(",");
                            for (int i = 0; i < offsets.length; i++) {
                                String resourceId = null;
                                String uri = request.getRequestURI();
                                int offset = Integer.valueOf(offsets[i]);
                                int first = StringUtil.indexOf(bestMatch, "{", offset);
                                if (first < 0) {
                                    continue;
                                }
                                int formIndex = -1;
                                int count = 0;
                                while (true) {
                                    formIndex = bestMatch.indexOf("/", formIndex + 1);
                                    if (formIndex > first || formIndex < 0) {
                                        break;
                                    } else {
                                        count++;
                                    }
                                }
                                first = StringUtil.indexOf(uri, "/", count);
                                int second = uri.indexOf("/", first + 1);
                                if (second > 0) {
                                    resourceId = uri.substring(first + 1, second);
                                } else {
                                    resourceId = uri.substring(first + 1);
                                }
                                logger.debug("resourceId = {}", resourceId);
                                if (resourceId.equals(PermissionConstant.ALL_OPOTION_STRING)) {
                                    pass = true;
                                } else {
                                    pass = resourcesVerifyService.verify(verifyNames[i], account.getId(), resourceId);
                                }
                                if (!pass) {
                                    break;
                                }
                            }
                        }
                    }
                    if (pass) {
                        break;
                    }
                }
                if (!pass) {
                    logger.warn("{}[{}] 没有权限 {}:{}", account.getAccount(), account.getName(), request.getMethod(), request.getRequestURI());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/authorize/forbidden");
                    dispatcher.forward(request, response);
                    return ;
                }
            }
        }
        else {
            logger.trace("{} > {}", request.getRequestURI(), "不需要任何权限检查");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean hasPermission(LoginedAccount account, String permissionName) {
        int size = account.getPermissions().size();
        for (int i = 0; i < size; i++) {
            MergePermission mergePermission = account.getPermissions().get(i);
            if (mergePermission.getName().equals(permissionName)) {
                if (mergePermission.getExpiry() > 0 && mergePermission.getExpiry() > System.currentTimeMillis()) {
                    account.getPermissions().remove(i);
                    return false;
                }
                return true;
            }
        }
        if (account.getAccount().equalsIgnoreCase(PermissionConstant.NAME)) {
            return true;
        }
        return false;
    }

    private void afterLogin(HttpServletRequest request, ResultMap result, boolean checkLogin) {
        request.setAttribute("checkLogin", checkLogin);
        request.setAttribute("args", result);
        if ("get".equalsIgnoreCase(request.getMethod())) {
            String uri = request.getRequestURI();
            logger.debug("get 请求，登陆后直接重定向....,uri=" + uri);
            Http.setToSession(request, "loginToURI", uri);
        } else {
            String referer = request.getHeader("referer");
            if (referer != null) {
                logger.debug("从" + referer + "进入，登陆后，调用浏览器，返回该页面");
                //Http.set(request, "loginReferer", true);
                request.setAttribute("loginReferer", true);
            }
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
