package com.heima.controller;

import com.heima.common.WktStatus;
import com.heima.json.AddUser;
import com.heima.json.WktStatusResult;
import com.heima.model.AuthCodeResult;
import com.heima.service.AuthCodeService;
import com.heima.web.service.AuthcodeManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-1
 */
@Controller
@RequestMapping({"/authcode"})
public class AuthCodeController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthCodeController.class);

//    @Autowired
//    private AuthcodeManagerService authcodeManagerService;

    @RequestMapping(value = "/genAuthCode")
    @ResponseBody
    public WktStatusResult genAuthCode(@RequestBody AddUser addUser)
    {
        WktStatus wktStatus=new WktStatus();
        if(addUser==null||StringUtils.isBlank(addUser.getMobile()))
        {
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage("未提供手机号");
            return new WktStatusResult(wktStatus);
        }
        try
        {
            //wkt-调用短信接口，发送短信
//            authcodeManagerService.genAuthcodeAndSend(addUser.getMobile());
        }catch (Exception exp)
        {
            logger.error("gen auth code error:",exp);
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }
}
