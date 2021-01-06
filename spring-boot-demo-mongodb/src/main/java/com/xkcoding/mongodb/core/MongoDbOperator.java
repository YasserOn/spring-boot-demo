package com.xkcoding.mongodb.core;

import com.google.common.collect.ImmutableMap;
import com.mongodb.client.result.UpdateResult;
import com.xkcoding.mongodb.annotation.SeesSoftlyDeletedRecords;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 操作类.
 */
public class MongoDbOperator<T> {
    private MongoTemplate mongoTemplate;

    private Class<T> tClass;

    public MongoDbOperator(MongoTemplate mongoTemplate, Class<T> classz) {
        this.mongoTemplate = mongoTemplate;
        this.tClass = classz;
    }


    public T getOne(Long id) {
        return mongoTemplate.findById(id, tClass);
    }

    @SeesSoftlyDeletedRecords
    public T getOne(Map<String, Object> filter) {
        Assert.notNull(filter, "过滤条件不能为空");
        Query queryFilter = getQueryFilter(filter);

        return mongoTemplate.findOne(queryFilter, tClass);
    }

    /**
     * 获取单个model
     *
     * @param filter 过滤条件
     * @param sort   Sort theSort = new Sort(Sort.Direction.ASC, "_id");
     */
    public T getOne(Map<String, Object> filter, Sort sort) {
        Query queryFilter = getQueryFilter(filter);
        queryFilter = queryFilter.with(sort);

        return mongoTemplate.findOne(queryFilter, tClass);
    }

    /**
     * 按访问时间倒序排列
     *
     * @param filter 过滤条件
     * @param sort   排序条件
     * @param limit  前几个(0:不限制)
     */
    public List<T> list(Map<String, Object> filter, Sort sort, int limit) {
        Query queryFilter = getQueryFilter(filter);
        if (sort != null) {
            queryFilter = queryFilter.with(sort);
        }
        if (limit > 0) {
            queryFilter = queryFilter.limit(limit);
        }

        return mongoTemplate.find(queryFilter, tClass);
    }

    /**
     * 按访问时间倒序排列
     *
     * @param filter 过滤条件
     */
    public long count(Map<String, Object> filter) {
        Query queryFilter = getQueryFilter(filter);

        return mongoTemplate.count(queryFilter, tClass);
    }

    /**
     * 分页查询
     *
     * @param filter    过滤条件
     * @param sort      排序条件
     * @param pageIndex 第几页
     * @param pageSize  每页大小
     */
    public PageResult<T> pagedList(Map<String, Object> filter, Sort sort, int pageIndex, int pageSize) {
        Query queryFilter = getQueryFilter(filter);
        long count = count(queryFilter);
        if (sort != null) {
            queryFilter = queryFilter.with(sort);
        }
        queryFilter = queryFilter.skip((pageIndex - 1) * pageSize).limit(pageSize);
        List<T> subItems = mongoTemplate.find(queryFilter, tClass);

        return new PageResult<T>()
            .setCount(count)
            .setList(subItems);
    }


    /**
     * 按访问时间倒序排列
     *
     * @param filter 过滤条件
     */
    @SeesSoftlyDeletedRecords
    public List<T> list(Map<String, Object> filter) {
        return list(filter, null, 0);
    }


    /**
     * 插入新数据
     * 返回的id在obj中赋值
     */
    public void insert(T obj) {
        mongoTemplate.insert(obj);
    }

    public long updateOne(Long id, Map<String, Object> filedValues) {
        Query queryFilter = getQueryFilter(id);

        return updateImpl(queryFilter, filedValues);
    }

    public long update(Map<String, Object> filter, Map<String, Object> filedValues) {
        Query queryFilter = getQueryFilter(filter);

        return updateImpl(queryFilter, filedValues);
    }

    public boolean exists(Map<String, Object> filter) {
        return getOne(filter) != null;
    }

    /**
     * 删除
     *
     * @param id _id
     */
    public long delete(Long id) {
        Query queryFilter = getQueryFilter(id);

        return delete(queryFilter);
    }

    /**
     * 删除
     *
     * @param filter 用户Id
     */
    public long delete(Map<String, Object> filter) {
        Query queryFilter = getQueryFilter(filter);

        return delete(queryFilter);
    }

    /**
     * 软删除
     *
     * @param filter 用户Id
     */
    public long softDelete(Map<String, Object> filter) {
        Query queryFilter = getQueryFilter(filter);
        UpdateResult result = mongoTemplate.updateMulti(queryFilter, Update.update("isDel", 1), tClass);

        return result.getModifiedCount();
    }


    private long count(Query queryFilter) {
        return mongoTemplate.count(queryFilter, tClass);
    }

    /**
     * 删除
     *
     * @param queryFilter 用户Id
     */
    private long delete(Query queryFilter) {
        return mongoTemplate.remove(queryFilter, tClass).getDeletedCount();
    }

    private long updateImpl(Query queryFilter, Map<String, Object> filedValues) {
        Update updateOperations = new Update();
        for (Map.Entry<String, Object> entry : filedValues.entrySet()) {
            updateOperations.set(entry.getKey(), entry.getValue());
        }
        UpdateResult writeResult = mongoTemplate.updateMulti(queryFilter, updateOperations, tClass);
        return writeResult.getModifiedCount();
    }

    private Query getQueryFilter(Long id) {
        return getQueryFilter(ImmutableMap.of("_id", id));
    }

    private Query getQueryFilter(Map<String, Object> filter) {
        Query query = new Query();
        if (CollectionUtils.isEmpty(filter)) {
            return query;
        }
        Criteria criteria = new Criteria();
        boolean w = false;
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            if (!w) {
                criteria = (Criteria.where(entry.getKey()).is(entry.getValue()));
                w = true;
            } else {
                criteria = criteria.and(entry.getKey()).is(entry.getValue());
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    private Update getUpdateOperations(Map<String, Object> filedValues) {
        Update updateOperations = new Update();
        filedValues.put("deleted", true);
        for (Map.Entry<String, Object> entry : filedValues.entrySet()) {
            updateOperations.set(entry.getKey(), entry.getValue());
        }

        return updateOperations;
    }

    private Update getSoftDeleteOperations(Map<String, Object> filedValues) {
        return getUpdateOperations(filedValues);
    }

}
