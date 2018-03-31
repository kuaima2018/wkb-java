package com.heima.json;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class FileResult implements Serializable {

    private Integer fileId;
    private String fileName;
    private String fileType;


    public FileResult()
    {
        this.fileName="";
        this.fileType="";
    }

    public FileResult(Integer fileId,String fileName,String fileType)
    {
        this.fileId = fileId;
        this.fileName = (fileName!=null)?fileName:"";
        this.fileType = (fileType!=null)?fileType:"";
    }
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
