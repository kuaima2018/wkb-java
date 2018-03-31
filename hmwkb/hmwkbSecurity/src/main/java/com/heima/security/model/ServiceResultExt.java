package com.heima.security.model;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-6
 */
public class ServiceResultExt extends ServiceResult {
    public Object getAttatchData() {
        return attatchData;
    }

    public void setAttatchData(Object attatchData) {
        this.attatchData = attatchData;
    }

    protected Object attatchData;
}
