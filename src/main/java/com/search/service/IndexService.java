package com.search.service;

import com.search.entity.UpdateIndexReq;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 14:32
 */
public interface IndexService {
    void updateIndex(String param);
    void  delIndex(String id);
    void  get();
    void  update(String id) throws  Exception;
    void  multiGet(String ... ids) throws  Exception;
    void  bulk(String ... ids) throws  Exception;
    void  bulkProcesstor(String index,String type,String... ids) throws  Exception;

    void handleKafkaMsg(String message);
}
