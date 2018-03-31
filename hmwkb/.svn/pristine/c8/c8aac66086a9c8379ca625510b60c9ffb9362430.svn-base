package com.heima.controller;

import com.heima.common.WktStatus;
import com.heima.json.JsonFeedbackQuery;
import com.heima.json.WktStatusResult;
import com.heima.service.WkbFeebackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/8
 */
@Controller
@RequestMapping({"/feedback"})
public class FeedbackController extends BaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private WkbFeebackService wkbFeebackService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public WktStatusResult add(@RequestBody JsonFeedbackQuery jsonFeedbackQuery)
    {
        WktStatus wktStatus=new WktStatus();

        if(jsonFeedbackQuery==null|| StringUtils.isBlank(jsonFeedbackQuery.getContent())
                ||jsonFeedbackQuery.getUserId()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供完整的信息");
            return new WktStatusResult(wktStatus);
        }
        try{
            jsonFeedbackQuery.setCreateBy(String.valueOf(jsonFeedbackQuery.getUserId()));
            wkbFeebackService.addFeedback(jsonFeedbackQuery);
        }catch (Exception exp)
        {
            wktStatus.setErrorCode(-1);
            wktStatus.setErrorMessage(exp.getMessage());
            logger.error("add feedback error:", exp);
        }
        return new WktStatusResult(wktStatus);
    }
}
