package com.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.search.common.conn.EsClient;
import com.search.entity.UpdateIndexReq;
import com.search.service.IndexService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 14:33
 */
@Service
public class IndexServiceImpl implements IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private EsClient client;

    @Value("${es.type}")
    private String esType;

    @Value("${es.index}")
    private String esIndex;

    @Override
    public void updateIndex(String param){
        Map<String, Object> jsonMap = JSON.parseObject(param);
        IndexResponse indexResponse = client.getConnection().prepareIndex(esIndex, esType, jsonMap.get("id").toString())
                .setSource(jsonMap)
                .get();
        String _index = indexResponse.getIndex();
        String _type = indexResponse.getType();
        String _id = indexResponse.getId();
        long _version = indexResponse.getVersion();
        RestStatus _status = indexResponse.status();

        logger.info("更新情况：index:{},type:{},id:{},_version:{},status:{}", _index, _type, _id, _version, _status);
    }

    @Override
    public void delIndex(String id) {
        DeleteResponse deleteResponse = client.getConnection().prepareDelete(esIndex, esType, id).get();
        String _index = deleteResponse.getIndex();
        String _type = deleteResponse.getType();
        String _id = deleteResponse.getId();
        long _version = deleteResponse.getVersion();
        RestStatus _status = deleteResponse.status();

        logger.info("删除情况：index:{},type:{},id:{},_version:{},status:{}", _index, _type, _id, _version, _status);
    }


    @Override
    public void get() {
        /*GetResponse response = client.getConnection().prepareGet("twitter", "tweet", "94pKEWABJOgzR6sJVCCV").get();
        Map<String, DocumentField> fields = response.getFields();
        System.out.println("map:"+fields);
        String index = response.getIndex();
        Map<String, Object> source = response.getSource();
        String id = response.getId();
        System.out.println(source);*/

    }


    @Override
    public void update(String id)  throws  Exception{
        client.getConnection().prepareUpdate("twitter","tweet",id)
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("name", "tom")
                        .endObject()).get();
    }

    @Override
    public void multiGet(String... ids) throws Exception {
        MultiGetResponse multiGetResponse = client.getConnection().prepareMultiGet()
                .add("twitter", "tweet", ids[0])
                .add("twitter", "tweet", ids[1], ids[2], ids[3])
                .get();
        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            GetResponse response = multiGetItemResponse.getResponse();
            if (response.isExists()){
                System.out.println(response.getSourceAsString());
            }
        }
    }

    @Override
    public void bulk(String... ids) throws Exception {
        BulkRequestBuilder prepareBulk = client.getConnection().prepareBulk();
        for (String id : ids) {
            prepareBulk.add(client.getConnection().prepareIndex("twitter","tweet",id)
                    .setSource(jsonBuilder().startObject().field("name","肖添"+id).endObject()));

        }
        BulkResponse responses = prepareBulk.get();
        System.out.println(responses.hasFailures());
        for (BulkItemResponse response : responses) {
            System.out.println(response.getResponse().getId() + "," + response.getResponse().getIndex() + "," + response.getResponse().getResult());
        }

    }

    @Override
    public void bulkProcesstor(String index,String type,String... ids) throws Exception {
        /*try {

            //IndexResponse indexResponse = client.getConnection().prepareIndex(index, type).setSource(getMapping()).get();
            IndexResponse indexResponse = client.getConnection().prepareIndex(index, type).setSource().get();

            PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(getMapping());
            PutMappingResponse putMappingResponse = client.getConnection().admin().indices().putMapping(mappingRequest).actionGet();
            //client.getConnection().prepareIndex("temp1","test").
            BulkProcessor bulkProcessor = BulkProcessor.builder(client.getConnection(), new BulkProcessor.Listener() {
                public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                    System.out.println("beforeBulk:" + executionId + "," + bulkRequest.getDescription() + "," + bulkRequest.numberOfActions());
                }

                public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                    System.out.println("afterBulk:" + executionId + "," + bulkRequest.getDescription() + "," + bulkRequest.numberOfActions());
                    System.out.println("afterBulk:" + executionId + "," + bulkResponse.getItems() + "," + bulkResponse.getTook());
                }

                public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable throwable) {
                    System.out.println("afterBulk:" + executionId + "," + bulkRequest.getParentTask() + "," + bulkRequest.getDescription() + "," + throwable);
                }
            })
                    .setBulkActions(10)
                    .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                    .setConcurrentRequests(1)
                    .setFlushInterval(TimeValue.timeValueMillis(1))
                    .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

            for (String id : ids) {
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put("name","中华人民共和国"+id);
                jsonMap.put("age",30+Integer.parseInt(id));
                jsonMap.put("date",new Date());
                jsonMap.put("message","程序设计"+id);
                jsonMap.put("tel","18612855433");
                jsonMap.put("attr_name",new String[]{"品牌_sku_attr"+id,"商品类别_sku_attr"+id,"面料_sku_attr"+id});
                jsonMap.put("create_date",new Date());
                bulkProcessor.add(new IndexRequest(index,type,id).source(jsonMap));

            }
            bulkProcessor.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }finally {

        }*/

    }

    @Override
    public void handleKafkaMsg(String message){
        System.out.println("ddd");
    }
}
