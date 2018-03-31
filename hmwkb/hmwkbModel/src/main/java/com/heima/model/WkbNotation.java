package com.heima.model;

import java.io.Serializable;
import java.util.Date;

public class WkbNotation implements Serializable {
    /**
     * 自动编号
     */
    private Integer id;

    /**
     * 计划日报编号
     */
    private Integer schId;

    /**
     * 类型（1-计划、2-日报）
     */
    private Integer type;

    /**
     * 批注用户
     */
    private Integer userId;

    /**
     * 批注内容
     */
    private String notation;

    /**
     * 创建日期
     */
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}