package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-21
 * Time: 下午9:40
 * To change this template use File | Settings | File Templates.
 */
public class TrackPathTL implements Serializable {
    private Date time;
    private String mlalo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMlalo() {
        return mlalo;
    }

    public void setMlalo(String mlalo) {
        this.mlalo = mlalo;
    }
}
