package com.heima.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
public class FriendsReturnModel implements Serializable {
    private Integer uid;
    private String uname;
    private Integer usex;
    private String oname;
    private Integer gid;
    private String ggroupname;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getUsex() {
        return usex;
    }

    public void setUsex(Integer usex) {
        this.usex = usex;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGgroupname() {
        return ggroupname;
    }

    public void setGgroupname(String ggroupname) {
        this.ggroupname = ggroupname;
    }
}
