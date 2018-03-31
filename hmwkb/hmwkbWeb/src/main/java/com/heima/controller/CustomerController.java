package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.*;
import com.heima.model.WkbContactLog;
import com.heima.model.WkbCustomer;
import com.heima.model.WkbCustomerGroup;
import com.heima.service.WkbContactLogService;
import com.heima.service.WkbCustomerGroupService;
import com.heima.service.WkbCustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@Controller
@RequestMapping({"/customer"})
public class CustomerController extends BaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private WkbCustomerService wkbCustomerService;
    @Autowired
    private WkbCustomerGroupService wkbCustomerGroupService;
    @Autowired
    private WkbContactLogService wkbContactLogService;

    @RequestMapping(value = "/addCustomer")
    @ResponseBody
    public WktStatusResult addCustomer(@RequestBody JsonCustomerQuery jsonCustomerQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerQuery==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbCustomer wkbCustomer=new WkbCustomer();
            BeanUtils.copyProperties(jsonCustomerQuery, wkbCustomer);
            wkbCustomerService.addCustomer(wkbCustomer);
        }catch (Exception exp)
        {
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("add customer error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/modifyCustomer")
    @ResponseBody
    public WktStatusResult modifyCustomer(@RequestBody JsonCustomerQuery jsonCustomerQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerQuery==null||jsonCustomerQuery.getCustomerId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbCustomer wkbCustomer=new WkbCustomer();
            BeanUtils.copyProperties(jsonCustomerQuery, wkbCustomer);
            wkbCustomerService.updateCustomer(wkbCustomer);
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("modify customer error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/deleteCustomer")
    @ResponseBody
    public WktStatusResult deleteCustomer(@RequestBody JsonCustomerQuery jsonCustomerQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerQuery==null||jsonCustomerQuery.getCustomerId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            wkbCustomerService.deleteCustomer(jsonCustomerQuery.getCustomerId(), jsonCustomerQuery.getUserId());
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("delete customer error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/listGroup")
    @ResponseBody
    public WktResult listGroup(@RequestBody JsonCustomerQuery jsonCustomerQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonCustomerQuery==null||jsonCustomerQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供完整的信息");
            return wktResult;
        }

        try{
            List<WkbCustomerGroup> wkbCustomerGroupList= wkbCustomerGroupService.queryCustomerGroups(jsonCustomerQuery.getUserId());
            List<JsonCustomerGroup> jsonCustomerGroupList=new ArrayList<JsonCustomerGroup>();
            if(wkbCustomerGroupList!=null)
            {
                for(WkbCustomerGroup wkbCustomerGroup:wkbCustomerGroupList)
                {
                    JsonCustomerGroup jsonCustomerGroup=new JsonCustomerGroup();
                    jsonCustomerGroup.setGroupId(wkbCustomerGroup.getGroupId());
                    jsonCustomerGroup.setGroupName(wkbCustomerGroup.getGroupName());

                    jsonCustomerGroupList.add(jsonCustomerGroup);
                }
            }
            wktResult.setResult(jsonCustomerGroupList);
        }catch (Exception exp){
            wktResult.getStatus().setErrorCode(-1);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
            logger.error("query customer group error:", exp);
        }
        return wktResult;
    }

    @RequestMapping(value = "/listCustomer")
    @ResponseBody
    public WktResult listCustomer(@RequestBody JsonCustomerQuery jsonCustomerQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonCustomerQuery==null||jsonCustomerQuery.getUserId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供完整的信息");
            return wktResult;
        }

        try{
            if(jsonCustomerQuery.getIndex()==null)
                jsonCustomerQuery.setIndex(1);
            if(jsonCustomerQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonCustomerQuery.getPageSize()==null||jsonCustomerQuery.getPageSize().intValue()<=0)
                return wktResult;

            List<WkbCustomer> wkbCustomerList=wkbCustomerService.queryCustomer(jsonCustomerQuery.getGroupId(), jsonCustomerQuery.getUserId(), jsonCustomerQuery.getIndex(), jsonCustomerQuery.getPageSize());

            List<JsonCustomerBase> jsonCustomerBaseList=new ArrayList<JsonCustomerBase>();
            if(wkbCustomerList!=null)
            {
                for(WkbCustomer wkbCustomer:wkbCustomerList)
                {
                    JsonCustomerBase jsonCustomerBase=new JsonCustomerBase();
                    BeanUtils.copyProperties(wkbCustomer,jsonCustomerBase);
                    jsonCustomerBaseList.add(jsonCustomerBase);
                }
            }

            wktResult.setResult(jsonCustomerBaseList);
        }catch (Exception exp){
            wktResult.getStatus().setErrorCode(-1);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
            logger.error("query customer group error:", exp);
        }
        return wktResult;
    }

    @RequestMapping(value = "/addCustomerGroup")
    @ResponseBody
    public WktStatusResult addCustomerGroup(@RequestBody JsonCustomerGroupQuery jsonCustomerGroupQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerGroupQuery==null||jsonCustomerGroupQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbCustomerGroup wkbCustomerGroup=new WkbCustomerGroup();
            wkbCustomerGroup.setGroupName(jsonCustomerGroupQuery.getGroupName());
            wkbCustomerGroup.setUserId(jsonCustomerGroupQuery.getUserId());
            wkbCustomerGroupService.addCustomerGroup(wkbCustomerGroup);
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("add customer group error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/modifyCustomerGroup")
    @ResponseBody
    public WktStatusResult modifyCustomerGroup(@RequestBody JsonCustomerGroupQuery jsonCustomerGroupQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerGroupQuery==null||jsonCustomerGroupQuery.getUserId()==null||jsonCustomerGroupQuery.getGroupId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbCustomerGroup wkbCustomerGroup=new WkbCustomerGroup();
            wkbCustomerGroup.setGroupName(jsonCustomerGroupQuery.getGroupName());
            wkbCustomerGroup.setUserId(jsonCustomerGroupQuery.getUserId());
            wkbCustomerGroup.setGroupId(jsonCustomerGroupQuery.getGroupId());
            wkbCustomerGroupService.updateCustomerGroup(wkbCustomerGroup);
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("modify customer group error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/deleteCustomerGroup")
    @ResponseBody
    public WktStatusResult deleteCustomerGroup(@RequestBody JsonCustomerGroupQuery jsonCustomerGroupQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonCustomerGroupQuery==null||jsonCustomerGroupQuery.getUserId()==null||jsonCustomerGroupQuery.getGroupId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            wkbCustomerGroupService.deleteCustomerGroup(jsonCustomerGroupQuery.getGroupId(), jsonCustomerGroupQuery.getUserId());
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("delete customer group error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/addContactLog")
    @ResponseBody
    public WktStatusResult addContactLog(@RequestBody JsonContactLogQuery jsonContactLogQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonContactLogQuery==null||jsonContactLogQuery.getUserId()==null||jsonContactLogQuery.getCustomerId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbContactLog wkbContactLog=new WkbContactLog();
            BeanUtils.copyProperties(jsonContactLogQuery,wkbContactLog);
            wkbContactLogService.addContactLog(wkbContactLog);
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("add contact log error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/deleteContactLog")
    @ResponseBody
    public WktStatusResult deleteContactLog(@RequestBody JsonContactLogQuery jsonContactLogQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonContactLogQuery==null||jsonContactLogQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }

        try{
            WkbContactLog wkbContactLog=new WkbContactLog();
            BeanUtils.copyProperties(jsonContactLogQuery,wkbContactLog);
            wkbContactLogService.deleteContactLog(wkbContactLog);
        }catch (Exception exp){
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("delete contact log error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/listContactLog")
    @ResponseBody
    public WktResult listContactLog(@RequestBody JsonContactLogQuery jsonContactLogQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonContactLogQuery==null||jsonContactLogQuery.getUserId()==null||jsonContactLogQuery.getCustomerId()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供完整的信息");
            return wktResult;
        }

        try{
            if(jsonContactLogQuery.getIndex()==null)
                jsonContactLogQuery.setIndex(1);
            if(jsonContactLogQuery.getIndex().intValue()<=0)
                return wktResult;
            if(jsonContactLogQuery.getPageSize()==null||jsonContactLogQuery.getPageSize().intValue()<=0)
                return wktResult;

            List<WkbContactLog> wkbContactLogList=wkbContactLogService.queryContactLogs(jsonContactLogQuery.getCustomerId(), jsonContactLogQuery.getUserId(), jsonContactLogQuery.getIndex(), jsonContactLogQuery.getPageSize());

            List<JsonContactLog> jsonContactLogList=new ArrayList<JsonContactLog>();
            if(wkbContactLogList!=null)
            {
                for(WkbContactLog wkbContactLog:wkbContactLogList)
                {
                    JsonContactLog jsonContactLog=new JsonContactLog();
                    BeanUtils.copyProperties(wkbContactLog,jsonContactLog);
                    jsonContactLogList.add(jsonContactLog);
                }
            }

            wktResult.setResult(jsonContactLogList);
        }catch (Exception exp){
            wktResult.getStatus().setErrorCode(-1);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
            logger.error("query contact log error:", exp);
        }
        return wktResult;
    }
}
