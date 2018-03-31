package com.heima.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/16
 */
public class WkbSchedule implements Serializable {
    /**
     * 自动编号
     */
    private Integer id;

    /**
     * 用户标识
     */
    private Integer userId;

    /**
     * 计划日报日期
     */
    private Date scheduleDate;

    /**
     * 计划内容
     */
    private String schedule;

    /**
     * 计划批注
     */
    private String scheduleNotation;

    /**
     * 计划批注人
     */
    private Integer scheduleNotationId;

    /**
     * 计划批注时间
     */
    private Date scheduleNotationTime;

    /**
     * 日报内容
     */
    private String daily;

    /**
     * 日报批注
     */
    private String dailyNotation;

    /**
     * 日报批注人
     */
    private Integer dailyNotationId;

    /**
     * 日报批注时间
     */
    private Date dailyNotationTime;

    /**
     * 是否提醒（1-提醒、其他都不提醒）
     */
    private Integer remind;

    /**
     * 提醒时间
     */
    private Date remindTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getDailyNotation() {
        return dailyNotation;
    }

    public void setDailyNotation(String dailyNotation) {
        this.dailyNotation = dailyNotation;
    }

    public Integer getDailyNotationId() {
        return dailyNotationId;
    }

    public void setDailyNotationId(Integer dailyNotationId) {
        this.dailyNotationId = dailyNotationId;
    }

    public Date getDailyNotationTime() {
        return dailyNotationTime;
    }

    public void setDailyNotationTime(Date dailyNotationTime) {
        this.dailyNotationTime = dailyNotationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemind() {
        return remind;
    }

    public void setRemind(Integer remind) {
        this.remind = remind;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleNotation() {
        return scheduleNotation;
    }

    public void setScheduleNotation(String scheduleNotation) {
        this.scheduleNotation = scheduleNotation;
    }

    public Integer getScheduleNotationId() {
        return scheduleNotationId;
    }

    public void setScheduleNotationId(Integer scheduleNotationId) {
        this.scheduleNotationId = scheduleNotationId;
    }

    public Date getScheduleNotationTime() {
        return scheduleNotationTime;
    }

    public void setScheduleNotationTime(Date scheduleNotationTime) {
        this.scheduleNotationTime = scheduleNotationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
