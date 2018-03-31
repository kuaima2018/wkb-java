package com.heima.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WkbOrgNode implements Serializable {
    public WkbOrgNode()
    {
        //this.children=new ArrayList<WkbOrgNode>();
    }

    public WkbOrgNode(Integer id, String text)
    {
        this.id=id;
        this.text=text;
        //this.children=new ArrayList<WkbOrgNode>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<WkbOrgNode> getChildren() {
        return children;
    }

    public void setChildren(List<WkbOrgNode> children) {
        this.children = children;
    }

    private Integer id;
    private String text;
    private String state;
    private Map<String,String> attributes;
    private List<WkbOrgNode> children;

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    private String iconCls;
}
