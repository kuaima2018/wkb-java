package com.chinadrtv.common.dal;

import java.util.List;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-9
 * Time: 下午9:48
 */
public interface BaseDao<T> {

    /**
     * 添加数据
     *
     * @param o
     * @return
     */
    public int insertData(T o);

    /**
     * 根据唯一标示删除数据
     *
     * @param o
     * @return
     */
    public int deleteDataByPK(Object o);

    /**
     * 根据条件删除数据
     *
     * @param o
     * @return
     */
    public int deleteData(T o);

    /**
     * 修改数据
     *
     * @param o
     * @return
     */
    public int updateData(T o);

    /**
     * 根据唯一标示修改数据
     *
     * @param o
     * @return
     */
    public int updateDataByPK(T o);

    /**
     * 根据唯一标示找数据
     *
     * @param o
     * @return
     */
    public Object queryObjectByPK(Object o);

    /**
     * 根据参数操作记录数
     *
     * @param o
     * @return
     */
    public int queryForInt(T o);

    /**
     * 获取全部的数据
     *
     * @return
     */
    public List<T> queryForListAll();

    /**
     * 显示分页查找数据，建议使用queryForListByPagination方法
     *
     * @param o
     * @param offSet 位移数 (当前页-1)*最大行数
     * @param maxRow 显示的最大行数
     * @return
     */
    public List<T> queryForList(T o, int offSet, int maxRow);

    /**
     * 隐式分页查找数据
     *
     * @param o
     * @param page
     * @return
     */
    public PaginationBean<T> queryForListByPagination(T o, Page page);

    /**
     * 根据条件查找条数据
     *
     * @param o
     * @return
     */
    public T queryObject(T o);

    /**
     * 根据条件查找多条数据
     *
     * @param o
     * @return
     */
    public List<T> queryForList(T o);


    public List<T> queryFrends(List<Integer> list);
}

