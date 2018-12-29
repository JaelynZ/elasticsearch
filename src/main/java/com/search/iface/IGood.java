package com.search.iface;

import com.search.entity.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 10:40
 */
@RequestMapping("/good")
public interface IGood {
    /**
     * 商品智能提示
     * @param req
     * @return
     */
    @RequestMapping(value = "/autoSuggestion", method = RequestMethod.POST)
    AutoSuggestionResp autoSuggestion(@RequestBody AutoSuggestionReq req);

    /**
     * 商品搜索
     * @param req
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    SearchResp search(@RequestBody SearchReq req);

    /**
     * 更新商品索引
     * @param req
     */
    @RequestMapping(value = "/updateIndex", method = RequestMethod.POST)
    UpdateIndexResp updateIndex(@RequestBody UpdateIndexReq req);

    /**
     * 删除商品索引
     * @param req
     */
    @RequestMapping(value = "/delIndex", method = RequestMethod.POST)
    UpdateIndexResp delIndex(@RequestBody UpdateIndexReq req);

    /**
     * 文件同步接口
     */
    @RequestMapping(value = "/fileSync", method = RequestMethod.POST)
    void fileSync(@RequestBody FileSyncReq req);
}
