package com.dresglas.elasticsearchdemo;

import com.alibaba.fastjson.JSON;
import com.dresglas.test.BaseTest;
import com.dresglas.elasticsearchdemo.tool.RestClientUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class RestClientUtilTest extends BaseTest {

    @Autowired
    private RestClientUtil restClientUtil;

    @Test
    void index() throws Exception {
        HashMap<String, String> objectHashMap = new HashMap<>();
        objectHashMap.put("apram","3");
        IndexRequest request=new IndexRequest("attach_local3", "doc3", "1033").source(objectHashMap);
        IndexResponse index = restClientUtil.index(request, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(index));
    }

    @Test
    void get() throws Exception{
        GetRequest getrequest=new GetRequest("attach_local2","doc2", "1032");
        GetResponse documentFields = restClientUtil.get(getrequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(documentFields));
    }

    @Test
    void update() throws Exception{
        UpdateRequest updaterequest=new UpdateRequest("attach_local2","doc2", "1032").doc("4545","78889");
        UpdateResponse update = restClientUtil.update(updaterequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(update));
    }

    @Test
    void delete() throws Exception{
        DeleteRequest deleteeequest=new DeleteRequest("attach_local3","1033");
        DeleteResponse delete = restClientUtil.delete(deleteeequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(delete));
    }

    @Test
    void search() throws Exception{
        SearchRequest search=new SearchRequest("attach_local2");
        SearchResponse search1 = restClientUtil.search(search, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(search1));
    }

    @Test
    void highlightField() {
        List<String> highlight= Arrays.asList("4");
        String result = restClientUtil.highlightField(highlight, "4445");
        logger.info("获取的结果为:{}", JSON.toJSON(result));
    }

    @Test
    void highlightContent() {
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        String searchField="66";
        restClientUtil.highlightContent(highlightBuilder,searchField);
    }

    @Test
    void getAnalyze() {

        List<String> ik_max_word = restClientUtil.getAnalyze("这是一个传说", "ik_max_word");
        logger.info("ik_max_word获取的结果为:{}", JSON.toJSON(ik_max_word));
        List<String> ik_smart = restClientUtil.getAnalyze("这是一个传说", "ik_smart");
        logger.info("ik_smart获取的结果为:{}", JSON.toJSON(ik_smart));
    }

    @Test
    void queryIndexExisted() {
        boolean attach_local2 = restClientUtil.queryIndexExisted("attach_local2");
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local2));
    }

    @Test
    void deleteIndex() {
        boolean attach_local3 = restClientUtil.deleteIndex("attach_local3");
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local3));
    }

    @Test
    void createIndex() throws Exception{
        CreateIndexResponse attach_local3 = restClientUtil.createIndex(new CreateIndexRequest("attach_local3"), RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local3));
    }
}