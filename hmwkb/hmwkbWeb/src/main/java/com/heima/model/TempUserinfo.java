package com.heima.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
public class TempUserinfo implements Serializable {
    private Integer uid;
    private String uname;
    private String upwd;
    private Integer usex;
    private Date ubrithday;
    private String utitle;
    private String umobile;
    private String utel;
    private String ufax;
    private String uemail;
    private String uaddr;
    private String uzipcode;
    private String creator;
    private Timestamp crtdatetime;

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

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public Integer getUsex() {
        return usex;
    }

    public void setUsex(Integer usex) {
        this.usex = usex;
    }

    public Date getUbrithday() {
        return ubrithday;
    }

    public void setUbrithday(Date ubrithday) {
        this.ubrithday = ubrithday;
    }

    public String getUtitle() {
        return utitle;
    }

    public void setUtitle(String utitle) {
        this.utitle = utitle;
    }

    public String getUmobile() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile = umobile;
    }

    public String getUtel() {
        return utel;
    }

    public void setUtel(String utel) {
        this.utel = utel;
    }

    public String getUfax() {
        return ufax;
    }

    public void setUfax(String ufax) {
        this.ufax = ufax;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUaddr() {
        return uaddr;
    }

    public void setUaddr(String uaddr) {
        this.uaddr = uaddr;
    }

    public String getUzipcode() {
        return uzipcode;
    }

    public void setUzipcode(String uzipcode) {
        this.uzipcode = uzipcode;
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
