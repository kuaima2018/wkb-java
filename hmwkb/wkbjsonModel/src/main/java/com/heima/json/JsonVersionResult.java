package com.heima.json;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/10/7.
 */
public class JsonVersionResult implements Serializable {
    private String version;
    private String desc;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
