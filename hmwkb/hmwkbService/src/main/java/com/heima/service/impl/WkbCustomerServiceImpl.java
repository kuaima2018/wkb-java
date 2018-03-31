package com.heima.service.impl;

import com.heima.dao.WkbCustomerDao;
import com.heima.model.WkbCustomer;
import com.heima.service.WkbCustomerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
@Service
public class WkbCustomerServiceImpl implements WkbCustomerService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbCustomerServiceImpl.class);
    @Autowired
    private WkbCustomerDao wkbCustomerDao;

    @Override
    public void addCustomer(WkbCustomer wkbCustomer) {
        if(wkbCustomer.getCreateDate()==null)
            wkbCustomer.setCreateDate(new Date());
        if(wkbCustomer.getCreateBy()==null)
            wkbCustomer.setCreateBy(String.valueOf(wkbCustomer.getUserId()));
        wkbCustomerDao.insertData(wkbCustomer);
    }

    @Override
    public void deleteCustomer(Integer customerId, Integer userId) {
        wkbCustomerDao.deleteCustomer(customerId,userId);
    }

    @Override
    public void updateCustomer(WkbCustomer wkbCustomer) {
        if(wkbCustomer.getCustomerId()==null)
            return;
        WkbCustomer wkbCustomerOld=wkbCustomerDao.getCustomer(wkbCustomer.getCustomerId());
        if(wkbCustomerOld==null){
            logger.error("can not find customer:"+wkbCustomer.getCustomerId());
            return;
        }
        if(StringUtils.isNotBlank(wkbCustomer.getCustomerName()))
            wkbCustomerOld.setCustomerName(wkbCustomer.getCustomerName());
        if(StringUtils.isNotBlank(wkbCustomer.getPostion()))
            wkbCustomerOld.setPostion(wkbCustomer.getPostion());
        if(StringUtils.isNotBlank(wkbCustomer.getCompany()))
            wkbCustomerOld.setCompany(wkbCustomer.getCompany());

        if(StringUtils.isNotBlank(wkbCustomer.getTelephone()))
            wkbCustomerOld.setTelephone(wkbCustomer.getTelephone());
        if(StringUtils.isNotBlank(wkbCustomer.getMobile()))
            wkbCustomerOld.setMobile(wkbCustomer.getMobile());
        if(StringUtils.isNotBlank(wkbCustomer.getFax()))
            wkbCustomerOld.setFax(wkbCustomer.getFax());
        if(StringUtils.isNotBlank(wkbCustomer.getEmail()))
            wkbCustomerOld.setEmail(wkbCustomer.getEmail());

        if(StringUtils.isNotBlank(wkbCustomer.getAddress()))
            wkbCustomerOld.setAddress(wkbCustomer.getAddress());
        if(wkbCustomer.getGroupId()!=null)
            wkbCustomerOld.setGroupId(wkbCustomer.getGroupId());
        wkbCustomerDao.updateCustomer(wkbCustomerOld);
    }

    @Override
    public List<WkbCustomer> queryCustomer(Integer groupId, Integer userId, Integer index, Integer pageSize) {
        int pos=(index-1)*pageSize;
        return wkbCustomerDao.queryCustomersByPage(userId,groupId,"customerName",pos, pageSize);
    }

    @Override
    public void changeCustomersGroup(Integer userId, Integer orgGroupId, Integer newGroupId) {
        wkbCustomerDao.updateCustomersGroup(orgGroupId,newGroupId,userId);
    }
}
