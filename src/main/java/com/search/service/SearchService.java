package com.search.service;

import com.search.entity.AutoSuggestionReq;
import com.search.entity.AutoSuggestionResp;
import com.search.entity.SearchResp;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 14:33
 */
public interface SearchService {
    AutoSuggestionResp autoSuggestion(String hintKey, Integer count);
    SearchResp search(String hintKey, Integer pageNo, Integer pageSize);
    void  searchByCondition() throws Exception;
    void  multiSearch();
    void aggsearch();
    void metricsAgg();
}
