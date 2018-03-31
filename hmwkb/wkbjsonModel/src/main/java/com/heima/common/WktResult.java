package com.heima.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public class WktResult implements Serializable {
    private WktStatus status;
    private Object result;

    public WktResult()
    {
        status=new WktStatus();
    }

    public WktStatus getStatus() {
        return status;
    }

    public void setStatus(WktStatus status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
