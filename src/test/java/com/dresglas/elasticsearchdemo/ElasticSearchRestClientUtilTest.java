package com.dresglas.elasticsearchdemo;

import com.alibaba.fastjson.JSON;
import com.dresglas.test.BaseTest;
import com.dresglas.elasticsearchdemo.utils.ElasticSearchRestClientUtil;
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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class ElasticSearchRestClientUtilTest extends BaseTest {

    @Autowired
    private ElasticSearchRestClientUtil elasticSearchRestClientUtil;

    @Test
    void index() throws Exception {
        HashMap<String, String> objectHashMap = new HashMap<>();
        objectHashMap.put("apram","3");
        IndexRequest request=new IndexRequest("attach_local4445").source(objectHashMap);
        IndexResponse index = elasticSearchRestClientUtil.index(request, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(index));
    }

    @Test
    void get() throws Exception{
        GetRequest getrequest=new GetRequest("attach_local2","doc2", "1032");
        GetResponse documentFields = elasticSearchRestClientUtil.get(getrequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(documentFields));
    }

    @Test
    void update() throws Exception{
        UpdateRequest updaterequest=new UpdateRequest("attach_local2","doc2", "1032").doc("4545","78889");
        UpdateResponse update = elasticSearchRestClientUtil.update(updaterequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(update));
    }

    @Test
    void delete() throws Exception{
        DeleteRequest deleteeequest=new DeleteRequest("attach_local3","1033");
        DeleteResponse delete = elasticSearchRestClientUtil.delete(deleteeequest, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(delete));
    }

    @Test
    void search() throws Exception{
        SearchRequest search=new SearchRequest("attach_local2");
        SearchResponse search1 = elasticSearchRestClientUtil.search(search, RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(search1));
    }

    @Test
    void highlightField() {
        List<String> highlight= Arrays.asList("4");
        String result = elasticSearchRestClientUtil.highlightField(highlight, "4445");
        logger.info("获取的结果为:{}", JSON.toJSON(result));
    }

    @Test
    void highlightContent() {
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        String searchField="66";
        elasticSearchRestClientUtil.highlightContent(highlightBuilder,searchField);
    }

    @Test
    void getAnalyze() {

        List<String> ik_max_word = elasticSearchRestClientUtil.getAnalyze("这是一个传说", "ik_max_word");
        logger.info("ik_max_word获取的结果为:{}", JSON.toJSON(ik_max_word));
        List<String> ik_smart = elasticSearchRestClientUtil.getAnalyze("这是一个传说", "ik_smart");
        logger.info("ik_smart获取的结果为:{}", JSON.toJSON(ik_smart));
    }

    @Test
    void queryIndexExisted() {
        boolean attach_local2 = elasticSearchRestClientUtil.queryIndexExisted("attach_local4445");
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local2));
    }

    @Test
    void deleteIndex() {
        boolean attach_local3 = elasticSearchRestClientUtil.deleteIndex("attach_local3");
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local3));
    }

    @Test
    void createIndex() throws Exception{
        CreateIndexResponse attach_local3 = elasticSearchRestClientUtil.createIndex(new CreateIndexRequest("attach_local3"), RequestOptions.DEFAULT);
        logger.info("获取的结果为:{}", JSON.toJSON(attach_local3));
    }
}