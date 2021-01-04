package com.xkcoding.mongodb.core;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:分页结果
 * @Author: xsh
 * @Date: 2020-10-26 15:24:52
 */
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable{

    private static final long serialVersionUID = 9000095574330546233L;

    private Long count;

    private Long totalPage;

    private Long pageSize=1L;

    private Long pageNumber;

    private List<T> list;
    public PageResult(Long count, Long pageSize){
        this.count=count;
        this.pageSize=pageSize;
    }
    public PageResult() {
        list = new ArrayList<>();
        count = 0L;
    }

    public Long getTotalPage() {
        if(null!=totalPage){
            return totalPage;
        }
        if(null!=count&&null!=pageSize){
            totalPage=count%pageSize<=0?count/pageSize:count/pageSize+1;
        }
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public void add(T t) {
        list.add(t);
    }
}
