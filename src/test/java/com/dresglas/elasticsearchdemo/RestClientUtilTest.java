package com.dresglas.elasticsearchdemo;

import com.alibaba.fastjson.JSON;
import com.dresglas.test.BaseTest;
import com.dresglas.elasticsearchdemo.tool.RestClientUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RestClientUtilTest extends BaseTest {

    @Autowired
    private RestClientUtil restClientUtil;

    private RestHighLevelClient client;
    @Test
    void testCreateIndex() throws Exception {
        HttpHost host = HttpHost.create("http://120.79.226.248:9200");
        RestClientBuilder builder = RestClient.builder(host);
        client = new RestHighLevelClient(builder);

        CreateIndexRequest request = new CreateIndexRequest("字节跳动");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(createIndexResponse));
        client.close();
    }


    @Test
    void index() throws Exception {
       /* IndexRequest request=new IndexRequest("attach_local", "doc", "103");
        IndexResponse index = restClientUtil.index(request, RequestOptions.DEFAULT);*/
        HttpHost host = HttpHost.create("http://120.79.226.248:9200");
        RestClientBuilder builder = RestClient.builder(host);
        client = new RestHighLevelClient(builder);




        IndexRequest request=new IndexRequest("attach_local", "doc", "103");
        IndexResponse index = client.index(request, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(index));
    }

    @Test
    void get() throws Exception{
        GetRequest getrequest=new GetRequest("字节跳动");
        GetResponse documentFields = restClientUtil.get(getrequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(documentFields));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }

    @Test
    void highlightField() {
    }

    @Test
    void highlightContent() {
    }
}