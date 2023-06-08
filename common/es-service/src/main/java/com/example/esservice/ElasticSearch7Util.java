package com.example.esservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by Bing on 2023/5/18.
 */
@Component
public class ElasticSearch7Util {

    @Resource(name = "restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @throws IOException
     */
    public boolean createIndex(String index, String source) throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.source(source, XContentType.JSON);
        //2. 客户端执行请求 IndicesClient,请求后获得响应
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        //输出是否创建成功
        System.out.println(response.isAcknowledged());
        return response.isAcknowledged();
    }

    /**
     * 查询是否存在索引
     *
     * @throws IOException
     */
    public boolean existIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        return exists;
    }

    /**
     * 删除索引
     *
     * @return
     */
    public boolean deleteIndex(String index) throws IOException {
        //创建删除索引请求对象
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        //客户端执行请求
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        //输出是否删除成功
        System.out.println(response.isAcknowledged());
        return response.isAcknowledged();
    }

    /**
     * 添加文档
     */
    public <T> Boolean addDocument(String index, T source) throws IOException {
        //创建请求
        String sourceStr = JSON.toJSONString(source);

        JSONObject jsonObject = JSONObject.parseObject(sourceStr);
        IndexRequest request = new IndexRequest(index).id(jsonObject.get("id").toString());

        request.timeout(TimeValue.timeValueSeconds(1));
        //将我们的数据放入请求
        request.source(JSON.toJSONString(source), XContentType.JSON);
        //客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response);
        //响应状态 CREATED
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
        return shardInfo.getSuccessful() == 1;
    }

    /**
     * 批量新增文档
     */
    public <T> Boolean addDocument(String index, List<T> source) throws Exception {
        BulkRequest request = new BulkRequest();

        String sourceStr = JSON.toJSONString(source);
        JSONArray sourceArr = JSONArray.parseArray(sourceStr);

        sourceArr.forEach(one -> {
            //批量更新或者删除只需修改方法即可
            request.add(new IndexRequest(index)
                    .id(((JSONObject) one).get("id").toString())
                    .source(((JSONObject) one).toJSONString(), XContentType.JSON));
        });
        BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(responses);
        //是否删除失败 返回false代表删除成功 false
        System.out.println(responses.hasFailures());
        return !responses.hasFailures();
    }

    /**
     * 删除文档
     *
     * @throws IOException
     */
    public <T> Boolean deleteDocument(String index, T source) throws IOException {
        String sourceStr = JSON.toJSONString(source);
        JSONObject jsonObject = JSONObject.parseObject(sourceStr);

        DeleteRequest request = new DeleteRequest(index).id(jsonObject.get("id").toString());
        request.timeout("1s");
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
        //OK
        return response.status().getStatus() == 200;
    }

    // 修改文档
    public <T> Boolean updateDocument(String index, T source) throws IOException {
        String sourceStr = JSON.toJSONString(source);
        JSONObject jsonObject = JSONObject.parseObject(sourceStr);

        UpdateRequest request = new UpdateRequest(index, jsonObject.get("id").toString());
        request.timeout("1s");
        request.doc("starName", "二钻333",
                "address", "闵行莘庄镇七莘路299号2222");
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.status());
        return response.status().getStatus() == 200;
    }

    //3.3 查询文档
    public String getDocument(String index, Long id) throws IOException {
        GetRequest request = new GetRequest(index);
        request.id(id + "");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(response);
        return response.getSourceAsString();
    }


}
