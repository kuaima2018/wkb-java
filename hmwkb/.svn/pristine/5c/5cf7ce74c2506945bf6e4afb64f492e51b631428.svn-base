package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-19
 * Time: 下午10:51
 * To change this template use File | Settings | File Templates.
 */
public class FrdMap implements Serializable {

    private Integer uid;
    private String mlalo;
    private String creator;
    private Date crtdatetime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCrtdatetime() {
        return crtdatetime;
    }

    public void setCrtdatetime(Date crtdatetime) {
        this.crtdatetime = crtdatetime;
    }

}
