package com.heima.model;

import java.io.Serializable;

/**
 * Created by xuzhikai on 2015/7/20.
 */
public class SocialitySearch implements Serializable {
    private String searchType;//1-公司 2-职位
    private String searchValue;//

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

}
