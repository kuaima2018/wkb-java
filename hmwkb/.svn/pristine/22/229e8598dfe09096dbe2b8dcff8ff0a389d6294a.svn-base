package com.heima.web.service.impl;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;
import com.heima.service.WkbSeqService;
import com.heima.web.service.JPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class JPushServiceImpl implements JPushService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JPushServiceImpl.class);

    @Autowired
    private WkbSeqService wkbSeqService;

    @Value("${com.heima.jpush.masterSecret}")
    private String masterSecret;
    @Value("${com.heima.jpush.appKey}")
    private String appKey;

    @Override
    public boolean pushMessage(Integer uId, String title,String msgContent, Map<String, Object> extra) {
        try{
            if(logger.isDebugEnabled())
            {
                logger.debug("push uid:"+uId);
                logger.debug("push title:"+title);
                logger.debug("push message:"+msgContent);
            }
            JPushClient jpush = new JPushClient(masterSecret,appKey);
            int sendNo=wkbSeqService.getSendNo();


            //String msgContent = wkbFriendapply.getAppName()+ "请求加为好友";
            //Map<String, Object> extra =new HashMap<String, Object>();
            //extra.put("type", 1);
            MessageResult msgResult=jpush.sendNotificationWithAlias(sendNo, uId.toString(), title, msgContent, 001, extra, null);
            if (null != msgResult) {
                if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
                    logger.info("发送成功， sendNo=" + msgResult.getSendno());
                    return true;
                } else {
                    logger.error("发送失败， 错误代码=" + msgResult.getErrcode() + ", " +
                            "错误消息=" + msgResult.getErrmsg());
                }
            } else {
                logger.error("无法获取数据:"+ sendNo);
            }
            return false;

        }catch (Exception exp)
        {
            logger.error("push unknown error:",exp);
            return false;
        }
    }
}
