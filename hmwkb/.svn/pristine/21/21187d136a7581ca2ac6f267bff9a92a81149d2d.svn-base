package com.heima.service.impl;

import com.heima.dao.UserinfoDao;
import com.heima.model.UserinfoModel;
import com.heima.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */

@Service
public class UserinfoServiceImpl implements UserinfoService {
    @Autowired
    private UserinfoDao userinfodao;

    @Override
    public void saveUserinfo(UserinfoModel userinfomodel) {
            userinfodao.insertData(userinfomodel);

    }
}
