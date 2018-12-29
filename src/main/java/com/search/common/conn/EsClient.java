package com.search.common.conn;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.InetAddress;

/**
 * @author zhangjingling
 * @Title:
 * @Description:
 * @date 2018/6/26 14:30
 */
@Component
public class EsClient {

    public static final String HOST = "127.0.0.1"; // 服务器地址
    public static final int PORT = 9300; // 端口

    /*@Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private String esPort;*/

    private TransportClient client;
    public  EsClient(){
        try{
            Settings esSettings = Settings.builder()
                    .put("cluster.name", "elasticsearch") //设置ES实例的名称
                    .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                    .build();

            client = new PreBuiltTransportClient(esSettings);
            //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST), PORT));

        }catch (Exception ex){
            client.close();
        }
    }
    public  TransportClient getConnection(){

        if (client==null){
            synchronized (EsClient.class){
                if (client==null){
                    new EsClient();
                }
            }
        }
        return  client;

    }

}