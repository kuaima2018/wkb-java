package com.heima.json;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-12
 */
public class JsonUserNameId extends JsonUserName {
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;
}
