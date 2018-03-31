package com.heima.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
@Component
public class SystemConfigure {
    @Value("${com.heima.image.server}")
    private String imageServer;

    @Value("${com.heima.image.path}")
    private String imagePath;

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
