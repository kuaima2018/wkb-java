package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbFree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-13
 */
public interface WkbFreeDao extends BaseDao<WkbFree> {
    List<WkbFree> queryFreeByUser(@Param(value="userId")Integer userId, @Param(value="index")Integer index,@Param(value="pageSize")Integer pageSize);
    int deleteFreeByUser(@Param(value="id")Integer id,@Param(value="userId")Integer userId);
    int deleteFrees(@Param(value="ids")List<Integer> ids,@Param(value="userId")Integer userId);
}
