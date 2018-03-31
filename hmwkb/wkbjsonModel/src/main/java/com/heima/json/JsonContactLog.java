package com.heima.json;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public class JsonContactLog implements Serializable {
    private Integer contactLogId;
    private String contactLogContent;
    private Date contactLogDate;

    public Integer getContactLogId() {
        return contactLogId;
    }

    public void setContactLogId(Integer contactLogId) {
        this.contactLogId = contactLogId;
    }

    public String getContactLogContent() {
        return contactLogContent;
    }

    public void setContactLogContent(String contactLogContent) {
        this.contactLogContent = contactLogContent;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getContactLogDate() {
        return contactLogDate;
    }

    public void setContactLogDate(Date contactLogDate) {
        this.contactLogDate = contactLogDate;
    }
}
