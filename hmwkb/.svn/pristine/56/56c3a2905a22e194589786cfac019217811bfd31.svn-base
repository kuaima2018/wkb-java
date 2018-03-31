package com.heima.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 下午11:06
 * To change this template use File | Settings | File Templates.
 */
public class Group implements Serializable {
    public Group()
    {

    }

    public Group(String name)
    {
        this.name=name;
        this.frdMapList=new ArrayList<FrdMap>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FrdMap> getFrdMapList() {
        return frdMapList;
    }

    public void setFrdMapList(List<FrdMap> frdMapList) {
        this.frdMapList = frdMapList;
    }

    private String name;
    private List<FrdMap> frdMapList;
}
