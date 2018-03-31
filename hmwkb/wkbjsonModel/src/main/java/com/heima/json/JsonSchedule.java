package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/16
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSchedule implements Serializable {
    private Date date;
    private String schedule;
    /*private String scheduleNotation;
    private Integer scheduleNotationId;
    private String scheduleNotationName;
    private String scheduleNotationUrl;*/
    private String daily;
   /* private String dailyNotation;
    private Integer dailyNotationId;
    private String dailyNotationName;
    private String dailyNotationUrl;*/
    private Integer remind;
    private Date remindTime;
    private List<JsonNotation> scheduleNotations;
    private List<JsonNotation> dailyNotations;
    private Integer userId;

    public JsonSchedule()
    {
        this.scheduleNotations=new ArrayList<JsonNotation>();
        this.dailyNotations=new ArrayList<JsonNotation>();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRemind() {
        return remind;
    }

    public void setRemind(Integer remind) {
        this.remind = remind;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public List<JsonNotation> getScheduleNotations() {
        return scheduleNotations;
    }

    public void setScheduleNotations(List<JsonNotation> scheduleNotations) {
        this.scheduleNotations = scheduleNotations;
    }

    public List<JsonNotation> getDailyNotations() {
        return dailyNotations;
    }

    public void setDailyNotations(List<JsonNotation> dailyNotations) {
        this.dailyNotations = dailyNotations;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
