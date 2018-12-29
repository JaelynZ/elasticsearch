package com.search.common.kafka.consumer;

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
public class GoodUpdateKafkaConsumer {
    private Logger logger = LoggerFactory.getLogger(GoodUpdateKafkaConsumer.class);

    public static final String TOPIC = "good.update";

    @Autowired
    private IndexService indexService;

    @KafkaListener(topics = {TOPIC})
    public void processMessage(String message) {
        logger.info("消费主题：{},内容：{}", TOPIC, message);
        if(!ToolUtil.validIsJson(message)){
            return;
        }
        indexService.updateIndex(message);
    }
}

