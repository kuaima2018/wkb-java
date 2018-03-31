package com.heima.model;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-21
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationModel {
    private Integer o_ID;
    private String o_Name;
    private Integer c_ID;
    private Integer o_fther_ID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationModel that = (OrganizationModel) o;

        if (c_ID != null ? !c_ID.equals(that.c_ID) : that.c_ID != null) return false;
        if (o_ID != null ? !o_ID.equals(that.o_ID) : that.o_ID != null) return false;
        if (o_Name != null ? !o_Name.equals(that.o_Name) : that.o_Name != null) return false;
        if (o_fther_ID != null ? !o_fther_ID.equals(that.o_fther_ID) : that.o_fther_ID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = o_ID != null ? o_ID.hashCode() : 0;
        result = 31 * result + (o_Name != null ? o_Name.hashCode() : 0);
        result = 31 * result + (c_ID != null ? c_ID.hashCode() : 0);
        result = 31 * result + (o_fther_ID != null ? o_fther_ID.hashCode() : 0);
        return result;
    }

    public Integer getO_ID() {
        return o_ID;
    }

    public void setO_ID(Integer o_ID) {
        this.o_ID = o_ID;
    }

    public String getO_Name() {
        return o_Name;
    }

    public void setO_Name(String o_Name) {
        this.o_Name = o_Name;
    }

    public Integer getC_ID() {
        return c_ID;
    }

    public void setC_ID(Integer c_ID) {
        this.c_ID = c_ID;
    }

    public Integer getO_fther_ID() {
        return o_fther_ID;
    }

    public void setO_fther_ID(Integer o_fther_ID) {
        this.o_fther_ID = o_fther_ID;
    }
}
