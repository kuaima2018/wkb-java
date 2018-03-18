package com.kuaimasoft.service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public interface IAuthCodeService {
    public void genAuthcodeAndSend(String mobile);
    public int authenticate(String mobile, String authCode);
}
