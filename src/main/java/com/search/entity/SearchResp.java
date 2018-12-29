package com.search.entity;

import com.search.common.BaseResp;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:44
 */
public class SearchResp extends BaseResp {
    private List<Map<String, Object>> data;
    private long total;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
