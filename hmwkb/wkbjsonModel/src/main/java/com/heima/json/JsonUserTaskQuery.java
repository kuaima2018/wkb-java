package com.heima.json;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-11
 */
public class JsonUserTaskQuery extends JsonTaskQuery {
    private String dateFrom;
    private String dateEnd;
    private Integer important;
    private String targetIds;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public String getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(String targetIds) {
        this.targetIds = targetIds;
    }
}
