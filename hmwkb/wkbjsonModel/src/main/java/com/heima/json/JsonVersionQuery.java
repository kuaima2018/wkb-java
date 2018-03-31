package com.heima.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/10/7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonVersionQuery implements Serializable {
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    private String platform;//ios android
}
