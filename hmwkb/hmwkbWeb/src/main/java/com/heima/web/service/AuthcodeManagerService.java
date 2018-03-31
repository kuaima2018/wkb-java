package com.heima.web.service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public interface AuthcodeManagerService {
    public void genAuthcodeAndSend(String mobile);
    public int authenticate(String mobile, String authCode);
}
