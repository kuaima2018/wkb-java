package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.service.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class MessageSourceImpl implements MessageSource {
    @Override
    public String getMessage(String code) {
        WkbMessageEnum  wkbMessageEnum=WkbMessageEnum.getEntry(code);
        if(wkbMessageEnum==null)
        {
            return null;
        }
        else
        {
            //暂时只使用说明文字，后期可以根据方言来显示不同信息
            return wkbMessageEnum.getDesc();
        }
    }
}
