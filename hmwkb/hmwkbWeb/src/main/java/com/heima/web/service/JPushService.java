package com.heima.web.service;

import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface JPushService {
    boolean pushMessage(Integer uId, String title,String msgContent, Map<String, Object> extra);
}
