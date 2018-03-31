package com.heima.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public class DefautUserRequest implements Serializable {
    private int uid;
    private String uname;
    private List<FriendRequest> frdsuids;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public List<FriendRequest> getFrdsuids() {
        return frdsuids;
    }

    public void setFrdsuids(List<FriendRequest> frdsuids) {
        this.frdsuids = frdsuids;
    }
}
