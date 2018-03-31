package com.heima.service.impl;

import com.heima.dao.WkbFeedbackDao;
import com.heima.model.WkbFeedback;
import com.heima.service.WkbFeebackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/8
 */
@Service
public class WkbFeebackServiceImpl implements WkbFeebackService {

    @Autowired
    private WkbFeedbackDao wkbFeedbackDao;

    @Override
    public void addFeedback(WkbFeedback wkbFeedback) {
        if(wkbFeedback.getCreateDate()==null)
            wkbFeedback.setCreateDate(new Date());

        wkbFeedbackDao.insertData(wkbFeedback);
    }
}
