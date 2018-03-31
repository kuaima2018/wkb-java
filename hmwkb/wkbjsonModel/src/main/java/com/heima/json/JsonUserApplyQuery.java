package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonUserApplyQuery implements Serializable {
    private Integer userId;
    private Integer targetId;
    private String remark;
    private String token;
    private Integer requestId;
    private Integer type;
    private List<Integer> requestIds;
    private String strRequestIds;

    public List<Integer> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<Integer> requestIds) {
        this.requestIds = requestIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStrRequestIds() {
        return strRequestIds;
    }

    public void setStrRequestIds(String strRequestIds) {
        this.strRequestIds = strRequestIds;
    }

}
