package com.search.entity;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:44
 */
public class AutoSuggestionReq {
    private String hintKey;
    private Integer count;

    public String getHintKey() {
        return hintKey;
    }

    public void setHintKey(String hintKey) {
        this.hintKey = hintKey;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
