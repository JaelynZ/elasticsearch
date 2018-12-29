package com.search.common.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.search.common.utils.ToolUtil;
import com.search.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/7/6 17:59
 */
@Component
public class GoodDelKafkaConsumer {
    private Logger logger = LoggerFactory.getLogger(GoodDelKafkaConsumer.class);

    public static final String TOPIC = "good.del";

    @Autowired
    private IndexService indexService;

    @KafkaListener(topics = {TOPIC})
    public void processMessage(String message) {
        logger.info("消费主题：{},内容：{}", TOPIC, message);
        if(!ToolUtil.validIsJson(message)){
            return;
        }
        JSONObject jo = JSONObject.parseObject(message);
        String id = jo.getString("id");
        indexService.delIndex(id);
    }
}

