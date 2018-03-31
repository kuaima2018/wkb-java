package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class UserTask implements Serializable {
    public UserTask()
    {
        this.voiceid=0;
    }
    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(Integer senduserid) {
        this.senduserid = senduserid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVoiceid() {
        return voiceid;
    }

    public void setVoiceid(Integer voiceid) {
        this.voiceid = voiceid;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public List<FileResult> getFileinfos() {
        return fileinfos;
    }

    public void setFileinfos(List<FileResult> fileinfos) {
        this.fileinfos = fileinfos;
    }

    private Integer taskid;
    private Integer senduserid;
    private String name;
    private String text;
    private Integer voiceid;
    private List<FileResult> fileinfos;
    private Integer duration;
    private Date date;
    private Integer important;
    private Integer read;
}
