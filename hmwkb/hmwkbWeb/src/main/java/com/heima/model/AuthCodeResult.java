package com.heima.model;

import com.heima.common.WkbResult;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
public class AuthCodeResult extends WkbResult implements Serializable {
    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    private String authcode;

}
