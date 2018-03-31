package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-11
 */
public class JsonUserTaskBase implements Serializable {
    private Integer taskId;
    private Integer sendUserId;
    private String name;
    private String text;
    private Integer voiceId;
    private List<FileResult> fileInfos;
    private Integer duration;
    private Date date;
    private Integer important;
    private Integer read;

    /**
     * 发送者头像url
     */
    private String imageUrl;


    public JsonUserTaskBase()
    {
        this.voiceId =0;
    }
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
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

    public Integer getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(Integer voiceId) {
        this.voiceId = voiceId;
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

    public List<FileResult> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<FileResult> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}