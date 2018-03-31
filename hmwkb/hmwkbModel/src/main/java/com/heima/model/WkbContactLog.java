package com.heima.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public class WkbContactLog implements Serializable {
    private Integer contactLogId;
    private Integer customerId;
    private Integer userId;
    private String contactLogContent;
    private Date contactLogDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getContactLogDate() {
        return contactLogDate;
    }

    public void setContactLogDate(Date contactLogDate) {
        this.contactLogDate = contactLogDate;
    }

    public Integer getContactLogId() {
        return contactLogId;
    }

    public void setContactLogId(Integer contactLogId) {
        this.contactLogId = contactLogId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContactLogContent() {
        return contactLogContent;
    }

    public void setContactLogContent(String contactLogContent) {
        this.contactLogContent = contactLogContent;
    }
}
