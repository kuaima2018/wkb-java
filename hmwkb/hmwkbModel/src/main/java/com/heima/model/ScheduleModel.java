package com.heima.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleModel implements Serializable {
    private int id;
    private int uid;
    private Date sschdate;
    private String sschtext;
    private String sdailytext;
    private int    sremindrule;
    private Date   sremindtime;
    private String creator;
    private Date  crtdatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+08:00")
    public Date getSschdate() {
        return sschdate;
    }

    public void setSschdate(Date sschdate) {
        this.sschdate = sschdate;
    }

    public String getSschtext() {
        return sschtext;
    }

    public void setSschtext(String sschtext) {
        this.sschtext = sschtext;
    }

    public String getSdailytext() {
        return sdailytext;
    }

    public void setSdailytext(String sdailytext) {
        this.sdailytext = sdailytext;
    }

    public int getSremindrule() {
        return sremindrule;
    }

    public void setSremindrule(int sremindrule) {
        this.sremindrule = sremindrule;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+08:00")
    public Date getSremindtime() {
        return sremindtime;
    }

    public void setSremindtime(Date sremindtime) {
        this.sremindtime = sremindtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+08:00")
    public Date getCrtdatetime() {
        return crtdatetime;
    }

    public void setCrtdatetime(Date crtdatetime) {
        this.crtdatetime = crtdatetime;
    }
}
