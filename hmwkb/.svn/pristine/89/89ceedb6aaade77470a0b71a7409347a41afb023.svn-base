package com.heima.controller;

import com.heima.common.WkbMessageEnum;
import com.heima.common.WkbResult;
import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.*;
import com.heima.model.WkbCompany;
import com.heima.model.WkbCompanyapply;
import com.heima.service.WkbCompanyService;
import com.heima.service.WkbCompanyapplyService;
import com.heima.service.WkbUserService;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbBizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping({"/company"})
public class CompanyController extends BaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyController.class);

    public CompanyController()
    {
        logger.warn("company controller is created!!!");
    }
    @Autowired
    private WkbCompanyapplyService wkbCompanyapplyService;

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @Autowired
    private WkbUserService wkbUserService;


    @RequestMapping(value = "/search")
    @ResponseBody
    public WktResult search(@RequestBody JsonCompanyQuery jsonCompanyQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonCompanyQuery==null||jsonCompanyQuery.getCompanyId()==null)
            return wktResult;
        try{
            WkbCompany wkbCompany=wkbCompanyService.findCompany(jsonCompanyQuery.getCompanyId());
            List<JsonCompany> jsonCompanyList=new ArrayList<JsonCompany>();
            if(wkbCompany!=null)
            {
                JsonCompany jsonCompany=new JsonCompany();
                jsonCompany.setCompanyId(wkbCompany.getcId());
                jsonCompany.setCompanyName(wkbCompany.getcName());
                jsonCompany.setContact(wkbCompany.getcContact());
                jsonCompany.setMobile(wkbCompany.getcMobile());
                jsonCompany.setTelephone(wkbCompany.getcTel());
                jsonCompany.setEmail(wkbCompany.getcEmail());
                jsonCompany.setAddress(wkbCompany.getcAddr());
                jsonCompany.setFax(wkbCompany.getcFax());
                jsonCompany.setPostcode(wkbCompany.getcZipcode());
                jsonCompany.setIndustry(wkbCompany.getcIndustry());
                jsonCompany.setRemark(wkbCompany.getcRemark());

                jsonCompanyList.add(jsonCompany);
            }
            wktResult.setResult(jsonCompanyList);
        }catch (Exception exp)
        {
            logger.error("search company error:",exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }


    @RequestMapping(value = "/applyMembership", method = {RequestMethod.POST})
    @ResponseBody
    public WktStatusResult ApplyMembership(@RequestBody JsonUserCompanyQuery jsonUserCompanyQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonUserCompanyQuery==null||jsonUserCompanyQuery.getCompanyId()==null||jsonUserCompanyQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数");
            return new WktStatusResult(wktStatus);
        }

        WkbCompany wkbCompany=wkbCompanyService.findCompany(jsonUserCompanyQuery.getCompanyId());
        if(wkbCompany==null)
        {
            wktStatus.setErrorCode(2);
            wktStatus.setErrorMessage("公司不存在");
            return new WktStatusResult(wktStatus);
        }


        //公司直接放入表中
        //是否给公司联系人推送消息提醒
        WkbBizResult wkbBizResult=null;
        try{
            boolean isConfirm=false;
            if(jsonUserCompanyQuery.getConfirm()!=null&&jsonUserCompanyQuery.getConfirm().intValue()==1)
                isConfirm=true;
            wkbBizResult=wkbUserService.applyJoinCompany(jsonUserCompanyQuery.getUserId(),jsonUserCompanyQuery.getCompanyId(), isConfirm);
            if(!wkbBizResult.isSucc()) {
                if (wkbBizResult.getCode().equals(WkbMessageEnum.USER_JOINT_OTHER.getCode())) {
                    wktStatus.setErrorCode(10);
                    wktStatus.setErrorMessage(this.getBizError(wkbBizResult.getCode()));
                } else {
                    wktStatus.setErrorCode(-100);
                    wktStatus.setErrorMessage(this.getBizError(wkbBizResult.getCode()));
                }
            }
        }catch (WkbBizException wkbExp){
            logger.error("apply joint error:"+wkbExp.getCode(), wkbExp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("apply joint unkown error:",exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(this.getBizError(exp.getMessage()));
        }
        return new WktStatusResult(wktStatus);
    }




    @RequestMapping(value = "/apply")
    @ResponseBody
    public WkbResult companyApply(
             @RequestParam Integer userid,
             @RequestBody CompanyInfo companyInfo,
             @RequestParam(required = false, defaultValue = "") String sessionid
    )
    {
        WkbResult wkbResult=new WkbResult();
        if(companyInfo==null)
        {
            wkbResult.setSuccess(false);
            wkbResult.setMessage("未提供参数");
            return wkbResult;
        }
        logger.debug("begin company apply user:"+userid);
        logger.debug("begin company apply user sessionId:"+sessionid+"-end");
        wkbResult=this.checkSession(sessionid, userid);
        if(wkbResult.getSuccess().booleanValue()==false)
            return wkbResult;

        try
        {
            WkbCompanyapply wkbCompanyapply=new WkbCompanyapply();
            wkbCompanyapply.setStatus("0");
            wkbCompanyapply.setcId(companyInfo.getCompanycode());
            wkbCompanyapply.setcName(companyInfo.getCompanyname());
            wkbCompanyapply.setcContact(companyInfo.getContractman());
            wkbCompanyapply.setcMobile(companyInfo.getMobile());
            wkbCompanyapply.setcTel(companyInfo.getTelephone());
            wkbCompanyapply.setcFax(companyInfo.getFax());
            wkbCompanyapply.setcEmail(companyInfo.getEmail());
            wkbCompanyapply.setcAddr(companyInfo.getAddress());
            wkbCompanyapply.setcZipcode(companyInfo.getPostcode());
            wkbCompanyapply.setCreator(userid);
            wkbCompanyapply.setCrtdatetime(new Date());
            wkbCompanyapplyService.saveCompanyApply(wkbCompanyapply);
        }catch (WkbBizException wkbExp)
        {
            logger.error("company apply error:"+wkbExp.getCode(), wkbExp);
            this.formatResult(wkbExp.getCode(),"", wkbResult);
        }
        catch (Exception exp)
        {
            logger.error("company apply unkown error:", exp);
            wkbResult.setSuccess(false);
            wkbResult.setMessage(exp.getMessage());
        }
        return wkbResult;
    }
}
