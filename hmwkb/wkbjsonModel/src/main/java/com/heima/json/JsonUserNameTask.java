package com.heima.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class JsonUserNameTask extends JsonUserTaskBase {

    private List<JsonUserName> receiverNames;

    public List<JsonUserName> getReceiverNames() {
        return receiverNames;
    }

    public void setReceiverNames(List<JsonUserName> receiverNames) {
        this.receiverNames = receiverNames;
    }

    public JsonUserNameTask()
    {
        this.receiverNames =new ArrayList<JsonUserName>();
    }
}
