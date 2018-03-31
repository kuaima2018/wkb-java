package com.heima.controller;

import com.heima.model.RetInfo;
import com.heima.model.TempUserinfo;
import com.heima.model.UserinfoModel;
import com.heima.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping({"/userinfo"})
public class UserinfoController {

    @Autowired
    private UserinfoService userinfoservicec;
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody

    public RetInfo adduserinfo(@RequestBody TempUserinfo tempuserinfo)  {
        UserinfoModel um=new UserinfoModel();
        um.setUid(tempuserinfo.getUid());
        um.setUname(tempuserinfo.getUname());
        um.setUpwd(tempuserinfo.getUpwd());
        um.setUsex(tempuserinfo.getUsex());
        um.setUtitle(tempuserinfo.getUtitle());
        um.setUbrithday(tempuserinfo.getUbrithday());
        um.setUmobile(tempuserinfo.getUmobile());
        um.setUtel(tempuserinfo.getUtel());
        um.setUfax(tempuserinfo.getUfax());
        um.setUzipcode(tempuserinfo.getUzipcode());
        um.setCreator(tempuserinfo.getCreator());
        um.setCrtdatetime(tempuserinfo.getCrtdatetime());

        userinfoservicec.saveUserinfo(um);

        RetInfo retInfo=new RetInfo();
        retInfo.setSuccess(true);
        retInfo.setMessage("succ");
        return retInfo;

    }


}
