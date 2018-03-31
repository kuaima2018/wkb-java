package com.heima.controller;

import com.heima.common.WkbResult;
import com.heima.json.CompanyBaseInfo;
import com.heima.json.SearchResultInfo;
import com.heima.json.UserBaseInfo;
import com.heima.model.WkbCompany;
import com.heima.model.WkbMessage;
import com.heima.model.WkbUser;
import com.heima.service.WkbCompanyService;
import com.heima.service.WkbMessageService;
import com.heima.service.WkbUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/index"})
public class IndexController extends BaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private WkbUserService wkbUserService;

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @RequestMapping(value = "/findEntry")
    @ResponseBody
    public SearchResultInfo SeachEntry(
            @RequestParam(required = false, defaultValue = "") String targetid)
    {
        if(StringUtils.isBlank(targetid))
            return new SearchResultInfo();
        else
        {
            try
            {
                SearchResultInfo searchResultInfo=new SearchResultInfo();
                //检查是否数字
                if(StringUtils.isNumeric(targetid))
                {
                    Integer id=Integer.parseInt(targetid);
                    WkbUser wkbUser = wkbUserService.getUser(id);
                    if(wkbUser!=null)
                    {
                        UserBaseInfo userBaseInfo=new UserBaseInfo();
                        userBaseInfo.setAddress(wkbUser.getuAddr());
                        userBaseInfo.setBirthday(wkbUser.getuBrithday());
                        userBaseInfo.setEmail(wkbUser.getuEmail());
                        userBaseInfo.setFax(wkbUser.getuFax());
                        userBaseInfo.setMobile(wkbUser.getuMobile());
                        userBaseInfo.setName(wkbUser.getuName());
                        userBaseInfo.setPostcode(wkbUser.getuZipcode());
                        userBaseInfo.setSex(wkbUser.getuSex()!=null?wkbUser.getuSex().intValue():null);
                        userBaseInfo.setTelephone(wkbUser.getuTel());
                        userBaseInfo.setUserId(wkbUser.getuId());

                        searchResultInfo.setUser(userBaseInfo);
                    }
                }

                WkbCompany wkbCompany = wkbCompanyService.findCompany(targetid);
                if(wkbCompany!=null)
                {
                    CompanyBaseInfo companyBaseInfo=new CompanyBaseInfo();
                    companyBaseInfo.setCompanyid(wkbCompany.getcId());
                    companyBaseInfo.setDescription(null);
                    companyBaseInfo.setName(wkbCompany.getcName());

                    searchResultInfo.setCompany(companyBaseInfo);
                }
                return searchResultInfo;
            }catch (Exception exp)
            {
                logger.error("find entry error:", exp);
                SearchResultInfo searchResultInfo=new SearchResultInfo();
                searchResultInfo.setSuccess(false);
                searchResultInfo.setMessage(exp.getMessage());
                return  searchResultInfo;
            }
        }
    }

    @RequestMapping(value = "/testFile",method = {RequestMethod.POST})
    @ResponseBody
    public String test(@RequestBody String str)
    {

        try{
            FileWriter writer = new FileWriter("/wkb/static/test.txt", false);
            writer.write("xxx");
            writer.close();
        }catch (Exception exp)
        {
            logger.error("test file error:", exp);
            return exp.getMessage();
        }
        //File tempFile=new File("/static/test.txt");
        return "succ";
    }

    @Autowired
    private WkbMessageService wkbMessageService;

    @RequestMapping(value = "/addMessage",method = {RequestMethod.POST})
    @ResponseBody
    public WkbResult addMessage(@RequestBody WkbMessage wkbMessage)
    {
        WkbResult wkbResult=new WkbResult();
        try{
           wkbMessageService.addMessage(wkbMessage);
        }catch (Exception exp)
        {
            logger.error("add message error:", exp);
            wkbResult.setSuccess(false);
            wkbResult.setMessage(exp.getMessage());
        }
        return wkbResult;
    }

    @RequestMapping(value = "/queryMessage")
    @ResponseBody
    public List<WkbMessage> queryMessage(
            @RequestParam(value = "startDate",required = false) String sDate,
            @RequestParam(value = "endDate",required = false) String eDate,
            @RequestParam(required = false) Integer index,
            @RequestParam(required = false) Integer pagesize
    )
    {
        if(index==null)
            index=1;
        if(pagesize==null)
            pagesize=1000;
        int startPos=(index-1)*pagesize+1;
        int endPos=startPos+pagesize-1;

        Date startDate=null;
        Date endDate=null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isBlank(sDate))
        {
            sDate=simpleDateFormat.format(new Date());
        }
        sDate+=" 00:00:00";
        if(StringUtils.isBlank(eDate))
        {
            eDate=simpleDateFormat.format(new Date());
        }
        eDate+=" 23:59:59";
        try
        {
            startDate=simpleDateFormatFull.parse(sDate);
            endDate=simpleDateFormatFull.parse(eDate);
        }
        catch (Exception exp)
        {
            logger.error("parse date error:", exp);
            return null;
        }

        return wkbMessageService.queryMessages(startDate,endDate,startPos,endPos);
    }
}
