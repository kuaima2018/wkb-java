package com.heima.json;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-11
 */
public class JsonTaskUserName extends JsonUserName {
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonTaskUserName that = (JsonTaskUserName) o;

        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
