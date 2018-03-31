package com.heima.json;

import com.heima.common.WktStatus;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-12
 */
public class WktStatusResult implements Serializable {
    public WktStatusResult(WktStatus wktStatus)
    {
        this.status=wktStatus;
    }
    public WktStatus getStatus() {
        return status;
    }

    public void setStatus(WktStatus status) {
        this.status = status;
    }

    private WktStatus status;
}
