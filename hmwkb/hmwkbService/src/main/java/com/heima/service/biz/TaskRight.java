package com.heima.service.biz;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public enum TaskRight {
    ALL(1,"全部"),
    UPLEVEL(2,"上级"),
    SELF(3,"本部门"),
    LOWLEVEL(4,"下级")
    ;

    TaskRight(Integer right,String dsc)
    {
        this.right=right;
        this.dsc=dsc;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    private Integer right;
    private String dsc;

    public static TaskRight getTaskRight(Integer right)
    {
        for(TaskRight taskRight:TaskRight.values())
        {
            if(taskRight.getRight().equals(right))
                return taskRight;
        }
        return LOWLEVEL;
    }


}
