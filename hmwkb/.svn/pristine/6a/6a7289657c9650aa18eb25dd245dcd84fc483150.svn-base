package com.heima.security.service.impl;

import org.springframework.security.web.util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-16
 */
public class ServiceSecurityConfig {
    protected String loginPattern;
    protected List<String> filterPatternList;
    /**
     * 例外地址
     * 不需要安全检查（主要用于安全检查模式中的例外）
     */
    protected List<String> exceptionPatternList;
    protected String matcherMethod;
    protected String appId;
    protected String tokenName="token";
    protected String logoutPattern;

    public ServiceSecurityConfig()
    {
        this.loginPattern="/login*";
        this.logoutPattern="/logout*";
        filterPatternList=new ArrayList<String>();
        filterPatternList.add("/**");
        matcherMethod="ANT";
        appId="Test";
    }
    public String getLoginPattern() {
        return loginPattern;
    }

    public void setLoginPattern(String loginPattern) {
        this.loginPattern = loginPattern;
    }

    public List<String> getFilterPatternList() {
        return filterPatternList;
    }

    public void setFilterPatternList(List<String> filterPatternList) {
        this.filterPatternList = filterPatternList;
    }

    public List<String> getExceptionPatternList() {
        return exceptionPatternList;
    }

    public void setExceptionPatternList(List<String> exceptionPatternList) {
        this.exceptionPatternList = exceptionPatternList;
    }

    public String getMatcherMethod() {
        return matcherMethod;
    }

    public void setMatcherMethod(String matcherMethod) {
        this.matcherMethod = matcherMethod;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getLogoutPattern() {
        return logoutPattern;
    }

    public void setLogoutPattern(String logoutPattern) {
        this.logoutPattern = logoutPattern;
    }

    public RequestMatcher requestMatcherFactory(String pattern)
    {
        if("ANT".equalsIgnoreCase(matcherMethod))
        {
            return  new AntPathRequestMatcher(pattern);
        }
        else if("REGEX".equalsIgnoreCase(matcherMethod))
        {
            return new RegexRequestMatcher(pattern,null);
        }
        else if("EL".equalsIgnoreCase(matcherMethod))
        {
            return new ELRequestMatcher(pattern);
        }
        else
        {
            return new AnyRequestMatcher();
        }
    }
}
