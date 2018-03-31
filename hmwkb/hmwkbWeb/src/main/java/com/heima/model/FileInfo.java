package com.heima.model;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-9
 */
public class FileInfo {
    private String name;
    private String text;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
