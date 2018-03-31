package com.heima.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class FriendRequest implements Serializable {
    private int fid;
    private String fname;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
