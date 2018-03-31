package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-21
 * Time: 下午9:48
 * To change this template use File | Settings | File Templates.
 */
public class Trackinput implements Serializable {
    private Integer uid;
    private Date time;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+08:00")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
