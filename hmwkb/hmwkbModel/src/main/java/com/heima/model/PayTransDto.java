package com.heima.model;

/**
 * Created by xuzhikai on 2016/3/6.
 */
public class PayTransDto extends PayTrans {
    public String getPanType() {
        return panType;
    }

    public void setPanType(String panType) {
        this.panType = panType;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    private String panType;
    private String idCardType;

    public String getsDateCreated() {
        return sDateCreated;
    }

    public void setsDateCreated(String sDateCreated) {
        this.sDateCreated = sDateCreated;
    }

    private String sDateCreated;
}
