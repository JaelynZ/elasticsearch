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
public class AutoSuggestionResp extends BaseResp {
    private List<Suggestion> data;

    public List<Suggestion> getData() {
        return data;
    }

    public void setData(List<Suggestion> data) {
        this.data = data;
    }

}
