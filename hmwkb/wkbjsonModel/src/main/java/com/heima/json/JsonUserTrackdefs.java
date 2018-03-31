package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
public class JsonUserTrackdefs extends JsonUserNameId {
    private String mlalo;
    private Date date;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMlalo() {
        return mlalo;
    }

    public void setMlalo(String mlalo) {
        this.mlalo = mlalo;
    }

}
