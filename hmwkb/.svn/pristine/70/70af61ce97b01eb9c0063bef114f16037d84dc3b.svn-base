package com.chinadrtv.common.dal;

import java.text.MessageFormat;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.common.pagination.ReflectUtil;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-9
 * Time: 下午9:52
 */
public abstract class BaseDaoImpl<T> extends SqlSessionDaoSupport implements BaseDao<T> {

    protected String queryAll;          // 获取所有对象

    protected String queryByPK;         // 根据主键获取对象

    protected String queryByCompositePK; // 根据联合主键获取对象

    protected String query;             // 根据查询条件获取对象

    protected String queryOne;          // 根据查询条件获取对象

    protected String count;             // 根据查询条件获取记录总数

    protected String queryNativePage;   // 根据查询条件获取分页对象

    protected String queryUnique;       // 根据查询条件获取唯一对象

    protected String updateByPK;        // 根据主键更新对象

    protected String update;            // 更新对象

    protected String delete;            // 根据条件删除对象

    protected String deleteByPK;        // 根据主键删除对象

    protected String add;               // 新增对象

    protected String entityClassName;   // 实体类名称,如：User

    private Class<T> entityClass;       // 实体class实例

    public void initialize() throws Exception {
        initSqlName();
    }

    private void initSqlName() {
        // 实体类型别信息
        this.entityClass = ReflectUtil.getSuperClassGenericType(getClass());
        this.entityClassName = entityClass.getSimpleName();

        // 根据实体类名称定义出通用方法的名称(注意：只要mybatis的sqlmap文件的namespace保持和entityClassName一致就可以自动匹配上了)
        this.queryAll = MessageFormat.format("{0}.queryAll", entityClassName);
        this.queryByPK = MessageFormat.format("{0}.queryByPK", entityClassName);
        this.queryByCompositePK = MessageFormat.format("{0}.queryByCompositePK", entityClassName);
        this.query = MessageFormat.format("{0}.query", entityClassName);
        this.queryOne = MessageFormat.format("{0}.queryOne", entityClassName);
        this.count = MessageFormat.format("{0}.count", entityClassName);
        this.queryNativePage = MessageFormat.format("{0}.queryNativePage", entityClassName);
        this.queryUnique = MessageFormat.format("{0}.queryUnique", entityClassName);
        this.updateByPK = MessageFormat.format("{0}.updateByPK", entityClassName);
        this.update = MessageFormat.format("{0}.update", entityClassName);
        this.delete = MessageFormat.format("{0}.delete", entityClassName);
        this.deleteByPK = MessageFormat.format("{0}.deleteByPK", entityClassName);
        this.add = MessageFormat.format("{0}.add", entityClassName);
    }

    @Override
    public int insertData(T o) {
        return this.getSqlSession().insert(add, o);
    }

    @Override
    public int deleteDataByPK(Object o) {
        return this.getSqlSession().delete(deleteByPK, o);
    }

    @Override
    public int deleteData(T o) {
        return this.getSqlSession().delete(delete, o);
    }

    @Override
    public int updateData(T o) {
        return this.getSqlSession().update(update, o);
    }

    @Override
    public int updateDataByPK(T o) {
        return this.getSqlSession().update(updateByPK, o);
    }

    @Override
    public Object queryObjectByPK(Object o) {
        return this.getSqlSession().selectOne(queryByPK, o);
    }

    @Override
    public int queryForInt(T o) {
        return (Integer) this.getSqlSession().selectOne(count, o);
    }

    @Override
    public List<T> queryForListAll() {
        return this.getSqlSession().selectList(queryAll);
    }

    @Override
    public List<T> queryForList(T o, int offSet, int maxRow) {
        return this.getSqlSession().selectList(query, o, new RowBounds(offSet, maxRow));
    }

    @Override
    public PaginationBean<T> queryForListByPagination(T o, Page page) {
        int totalRecords = (Integer) this.getSqlSession().selectOne(count, o);
        PaginationBean<T> pageHolder = new PaginationBean<T>(page, totalRecords);
        List<T> resultList = queryForList(o,
                (pageHolder.getCurrentPage() - 1) * pageHolder.getPageSize(), pageHolder.getPageSize());
        pageHolder.setPageList(resultList);
        return pageHolder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T queryObject(T o) {
        return (T) this.getSqlSession().selectOne(queryOne, o);
    }

    @Override
    public List<T> queryForList(T o) {
        return this.getSqlSession().selectList(query, o);
    }

    @Override
    public List<T> queryFrends(List<Integer> ts) {
        return this.getSqlSession().selectList(query,ts);  //To change body of implemented methods use File | Settings | File Templates.
    }
}