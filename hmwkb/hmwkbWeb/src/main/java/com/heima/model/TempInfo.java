package com.heima.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午11:05
 * To change this template use File | Settings | File Templates.
 */
public class TempInfo implements Serializable {
    private Integer aaa;
    private String user;
    private Integer uid;
    private Date mtracedate;
    private String mlalo;
    private String creator;
    private Timestamp crtdatetime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempInfo tempInfo = (TempInfo) o;

        if (aaa != null ? !aaa.equals(tempInfo.aaa) : tempInfo.aaa != null) return false;
        if (creator != null ? !creator.equals(tempInfo.creator) : tempInfo.creator != null) return false;
        if (crtdatetime != null ? !crtdatetime.equals(tempInfo.crtdatetime) : tempInfo.crtdatetime != null)
            return false;
        if (mlalo != null ? !mlalo.equals(tempInfo.mlalo) : tempInfo.mlalo != null) return false;
        if (mtracedate != null ? !mtracedate.equals(tempInfo.mtracedate) : tempInfo.mtracedate != null) return false;
        if (uid != null ? !uid.equals(tempInfo.uid) : tempInfo.uid != null) return false;
        if (user != null ? !user.equals(tempInfo.user) : tempInfo.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aaa != null ? aaa.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (mtracedate != null ? mtracedate.hashCode() : 0);
        result = 31 * result + (mlalo != null ? mlalo.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (crtdatetime != null ? crtdatetime.hashCode() : 0);
        return result;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getMtracedate() {
        return mtracedate;
    }

    public void setMtracedate(Date mtracedate) {
        this.mtracedate = mtracedate;
    }

    public String getMlalo() {
        return mlalo;
    }

    public void setMlalo(String mlalo) {
        this.mlalo = mlalo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCrtdatetime() {
        return crtdatetime;
    }

    public void setCrtdatetime(Timestamp crtdatetime) {
        this.crtdatetime = crtdatetime;
    }


}



//    public Integer getAaa() {
//        return aaa;
//    }
//
//    public void setAaa(Integer aaa) {
//        this.aaa = aaa;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }