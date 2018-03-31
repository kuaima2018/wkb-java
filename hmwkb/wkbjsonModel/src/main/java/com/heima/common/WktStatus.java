package com.heima.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public class WktStatus implements Serializable {
    private Integer errorCode;
    private String errorMessage;

    public WktStatus()
    {
        errorCode=0;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
