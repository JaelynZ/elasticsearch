package com.search.control;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.search.common.Result;
import com.search.common.commonConstant;
import com.search.common.utils.ToolUtil;
import com.search.entity.*;
import com.search.iface.IGood;
import com.search.iface.IHello;
import com.search.service.IndexService;
import com.search.service.SearchService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 9:58
 */
@RestController
public class SearchController implements IGood {

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public AutoSuggestionResp autoSuggestion(@RequestBody AutoSuggestionReq req){
        long startTime = System.currentTimeMillis();
        AutoSuggestionResp resp = searchService.autoSuggestion(req.getHintKey(), req.getCount());
        Result result = new Result();
        result.setCode(commonConstant.NORMAL_CODE);
        resp.setResult(result);
        long endTime = System.currentTimeMillis();
        logger.info("search日志: cost={}, params={}, result={}", endTime-startTime, JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public SearchResp search(@RequestBody SearchReq req) {
        long startTime = System.currentTimeMillis();
        SearchResp resp = searchService.search(req.getHintKey(), req.getPageNo(), req.getPageSize());
        Result result = new Result();
        result.setCode(commonConstant.NORMAL_CODE);
        resp.setResult(result);
        long endTime = System.currentTimeMillis();
        logger.info("search日志: cost={}, params={}, result={}", endTime-startTime, JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public UpdateIndexResp updateIndex(@RequestBody UpdateIndexReq req){
        long startTime = System.currentTimeMillis();
        UpdateIndexResp resp = new UpdateIndexResp();
        Result result = new Result();
        if(!ToolUtil.validIsJson(JSON.toJSONString(req))){
            result.setCode(commonConstant.ERROR_CODE);
            result.setMsg("入参格式不正确！");
            resp.setResult(result);
            return resp;
        }
        kafkaTemplate.send("good.update", JSON.toJSONString(req));
        result.setCode(commonConstant.NORMAL_CODE);
        result.setMsg("更新成功！");
        resp.setResult(result);
        long endTime = System.currentTimeMillis();
        logger.info("updateIndex日志: cost={}, params={}, result={}", endTime-startTime, JSON.toJSONString(req), null);
        return resp;
    }

    @Override
    public UpdateIndexResp delIndex(@RequestBody UpdateIndexReq req){
        long startTime = System.currentTimeMillis();
        UpdateIndexResp resp = new UpdateIndexResp();
        Result result = new Result();
        if(!ToolUtil.validIsJson(JSON.toJSONString(req))){
            result.setCode(commonConstant.ERROR_CODE);
            result.setMsg("入参格式不正确！");
            resp.setResult(result);
            return resp;
        }
        kafkaTemplate.send("good.del", JSON.toJSONString(req));
        result.setCode(commonConstant.NORMAL_CODE);
        result.setMsg("更新成功！");
        resp.setResult(result);
        long endTime = System.currentTimeMillis();
        logger.info("delIndex日志: cost={}, params={}, result={}", endTime-startTime, JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public void fileSync(@RequestBody FileSyncReq req){
        long startTime = System.currentTimeMillis();
        String fileName = req.getFileName();
        List<UpdateIndexReq> txtResult = readTxtFile(fileName);
        //循环发送kafka消息
        txtResult.stream().forEach(result -> kafkaTemplate.send("good.update", JSON.toJSONString(result)));
        long endTime = System.currentTimeMillis();
        logger.info("fileSync日志: cost={}, params={}, result={}", endTime-startTime, JSON.toJSONString(req), JSON.toJSONString(txtResult));

    }

    public List<UpdateIndexReq> readTxtFile(String filePath){
        List<UpdateIndexReq> txtResult = Lists.newArrayList();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    UpdateIndexReq req = new UpdateIndexReq();
                    String[] param = lineTxt.split("_\\|_");
                    if(param.length != 11){
                        logger.info("不符格式的字符串：{}", lineTxt);
                        continue;
                    }
                    req.setId(param[0].toString());
                    //去除特殊符号
                    String title = param[1].toString().replaceAll("\\pP", "");
                    title.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+","");
                    req.setTitle(title);
                    req.setOldId(param[2].toString());
                    req.setTaobaoId(param[3].toString());
                    req.setPlat(param[4].toString());
                    req.setShopName(param[5].toString());
                    req.setKeyWord(param[6].toString());
                    req.setCreateTime(param[7].toString());
                    req.setIsRecommend(param[8].toString());
                    req.setAttention(param[9].toString());
                    req.setDiscount(param[10].toString());
                    txtResult.add(req);
                }
                read.close();
            } else {
                logger.info("找不到指定的文件");
            }
        }catch (IOException e){
            logger.error(e.getMessage());
        }

        return txtResult;
    }
}
