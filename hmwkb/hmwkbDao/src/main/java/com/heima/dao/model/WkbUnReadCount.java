package com.heima.dao.model;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-11
 */
public class WkbUnReadCount {
    private Long taskId;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WkbUnReadCount that = (WkbUnReadCount) o;

        if (!taskId.equals(that.taskId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}
