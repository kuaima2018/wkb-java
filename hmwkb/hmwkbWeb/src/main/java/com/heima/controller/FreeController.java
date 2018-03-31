package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.JsonFree;
import com.heima.json.JsonFreeQuery;
import com.heima.json.WktStatusResult;
import com.heima.model.WkbFree;
import com.heima.service.WkbFreeService;
import com.heima.web.util.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-13
 */
@Controller
@RequestMapping({"/free"})
public class FreeController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FreeController.class);

    @Autowired
    private WkbFreeService wkbFreeService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public WktStatusResult saveFree(@RequestBody JsonFreeQuery jsonFreeQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonFreeQuery==null||jsonFreeQuery.getAmount()==null||jsonFreeQuery.getDate()==null
                ||jsonFreeQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数");
            return new WktStatusResult(wktStatus);
        }
        if(jsonFreeQuery.getAmount().compareTo(BigDecimal.ZERO)<=0)
        {
            wktStatus.setErrorCode(2);
            wktStatus.setErrorMessage("费用必须为正数");
            return new WktStatusResult(wktStatus);
        }
        try{
            WkbFree wkbFree=new WkbFree();
            wkbFree.setAmount(jsonFreeQuery.getAmount());
            wkbFree.setUserId(jsonFreeQuery.getUserId());
            wkbFree.setDate(jsonFreeQuery.getDate());
            wkbFree.setCreateDate(new Date());
            wkbFree.setRemark(jsonFreeQuery.getRemark());
            wkbFree.setName(jsonFreeQuery.getName());

            wkbFreeService.saveFree(wkbFree);
        }
        catch (Exception exp)
        {
            logger.error("add free error:", exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public WktStatusResult deleteFree(@RequestBody JsonFreeQuery jsonFreeQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonFreeQuery==null||jsonFreeQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数");
            return new WktStatusResult(wktStatus);
        }

        try{
            List<Integer> freeIdList= ListHelper.parseStr(jsonFreeQuery.getStrFreeIds());
            if(freeIdList==null||freeIdList.size()==0) {
                freeIdList=new ArrayList<Integer>();
                if (jsonFreeQuery.getFreeIds() != null && jsonFreeQuery.getFreeIds().size() > 0) {
                    freeIdList.addAll(jsonFreeQuery.getFreeIds());
                }
                if (jsonFreeQuery.getFreeId() != null && jsonFreeQuery.getFreeId().intValue() > 0) {
                    if (!freeIdList.contains(jsonFreeQuery.getFreeId()))
                        freeIdList.add(jsonFreeQuery.getFreeId());
                }
            }
            if(freeIdList.size()==0)
            {
                wktStatus.setErrorCode(2);
                wktStatus.setErrorMessage("未提供删除的费用记录");
                return new WktStatusResult(wktStatus);
            }
            int count=wkbFreeService.deleteFrees(freeIdList,jsonFreeQuery.getUserId());
            if(count<=0)
            {
                wktStatus.setErrorCode(3);
                wktStatus.setErrorMessage("未找到对应费用");
            }
        }
        catch (Exception exp)
        {
            logger.error("add free error:", exp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(exp.getMessage());
        }
        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public WktResult getFree(@RequestBody JsonFreeQuery jsonFreeQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonFreeQuery==null||jsonFreeQuery.getUserId()==null||jsonFreeQuery.getIndex()==null
                ||jsonFreeQuery.getPageSize()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数");
            return wktResult;
        }

        if(jsonFreeQuery.getIndex().intValue()<=0
                ||jsonFreeQuery.getPageSize().intValue()<=0)
        {
            wktResult.getStatus().setErrorCode(2);
            wktResult.getStatus().setErrorMessage("分页参数不正确");
            return wktResult;
        }

        try{
            List<WkbFree> wkbFreeList=wkbFreeService.queryFreeByUser(jsonFreeQuery.getUserId(),jsonFreeQuery.getIndex(),jsonFreeQuery.getPageSize());
            if(wkbFreeList!=null)
            {
                List<JsonFree> jsonFreeList=new ArrayList<JsonFree>();
                for(WkbFree wkbFree:wkbFreeList)
                {
                    JsonFree jsonFree=new JsonFree();
                    jsonFree.setFreeId(wkbFree.getId());
                    jsonFree.setDate(wkbFree.getDate());
                    jsonFree.setName(wkbFree.getName());
                    jsonFree.setCreateDate(wkbFree.getCreateDate());
                    jsonFree.setAmount(wkbFree.getAmount());
                    jsonFree.setRemark(wkbFree.getRemark());

                    jsonFreeList.add(jsonFree);
                }

                wktResult.setResult(jsonFreeList);
            }
        }
        catch (Exception exp)
        {
            logger.error("add free error:", exp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }
}
