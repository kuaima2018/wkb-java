package com.heima.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
public class DefShareModel implements Serializable {
    private int id;

    private int userId;

    private String userName;

    private int friendId;

    private String friendName;

    private String creator;

    private Timestamp crtDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCrtDateTime() {
        return crtDateTime;
    }

    public void setCrtDateTime(Timestamp crtDateTime) {
        this.crtDateTime = crtDateTime;
    }
}
