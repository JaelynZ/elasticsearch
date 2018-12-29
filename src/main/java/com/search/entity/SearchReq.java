package com.search.entity;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:44
 */
public class SearchReq {
    private String hintKey;
    private Integer pageNo;
    private Integer pageSize;

    public String getHintKey() {
        return hintKey;
    }

    public void setHintKey(String hintKey) {
        this.hintKey = hintKey;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
