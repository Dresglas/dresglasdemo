package com.sininenuni.elasticsearchdemo.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RestClientUtil {

    private static final String PRE_TAGS = "<span style=\"color:red\">";

    private static final String POST_TAGS = "</span>";

    // es 连接超时，尝试重新连接的次数
    private static final Integer RECONNECT_NUM = 3;

    @Autowired
    private RestHighLevelClient client;

    public ActionResponse reconnect(ActionRequest request, RequestOptions requestOptions) throws IOException {
        // 当es连接异常时，尝试重新连接（3次），如果还失败，则抛出异常
        log.error(" ****  es 连接异常 **** ");
        log.info(" ****  开始重新连接 **** ");
        ActionResponse response = null;
        IOException ioException = null;
        for (int i = 1; i <= RECONNECT_NUM; i++) {
            log.info(" ****  尝试第{}次连接 **** ", i);
            try {
                if (request instanceof SearchRequest) {
                    response = client.search((SearchRequest) request, requestOptions);
                } else if (request instanceof IndexRequest) {
                    response = client.index((IndexRequest) request, requestOptions);
                } else if (request instanceof UpdateRequest) {
                    response = client.update((UpdateRequest) request, requestOptions);
                } else if (request instanceof GetRequest) {
                    response = client.get((GetRequest) request, requestOptions);
                } else if (request instanceof DeleteRequest) {
                    response = client.delete((DeleteRequest) request, requestOptions);
                } else {
                    log.error(" ****  当前reconnect无法处理该[{}]请求类型 **** ", request != null ? request.getClass() : "null");
                    break;
                }
                log.info(" ****  连接成功 **** ");
                break;
            } catch (IOException e) {e.printStackTrace();
                log.error(" ****  第{}次连接失败 **** ", i);
                ioException = e;
            }
        }
        log.info(" ****  结束重新连接 **** ");
        if (ioException != null) {
            // 如果 RECONNECT_NUM 次都失败，则抛出异常
            throw ioException;
        }
        return response;
    }

    /**
     * 新增索引
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public IndexResponse index(IndexRequest request, RequestOptions requestOptions) throws IOException {
        IndexResponse response = null;
        try {
            response = client.index(request, requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            response = (IndexResponse) reconnect(request, requestOptions);
        }
        return response;
    }
	/**
     * 查询索引（比如通过索引id查询）
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public GetResponse get(GetRequest request, RequestOptions requestOptions) throws IOException {
        GetResponse response = null;
        try {
            response = client.get(request, requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            response = (GetResponse) reconnect(request, requestOptions);
        }
        return response;
    }

    /**
     * 更新索引
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public UpdateResponse update(UpdateRequest request, RequestOptions requestOptions) throws IOException {
        UpdateResponse response = null;
        try {
            response = client.update(request, requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            response = (UpdateResponse) reconnect(request, requestOptions);
        }
        return response;
    }

    /**
     * 删除索引
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public DeleteResponse delete(DeleteRequest request, RequestOptions requestOptions) throws IOException {
        DeleteResponse response = null;
        try {
            response = client.delete(request, requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            response = (DeleteResponse) reconnect(request, requestOptions);
        }
        return response;
    } 
    /**
     * 搜索
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public SearchResponse search(SearchRequest request, RequestOptions requestOptions) throws IOException {
        SearchResponse response = null;
        try {
            response = client.search(request, requestOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            response = (SearchResponse) reconnect(request, requestOptions);
        }
        return response;
    }


    public String highlightField(List<String> strList, String field) {
        for (String str : strList) {
            field = field.replace(str, PRE_TAGS + str + POST_TAGS);
        }
        return field;
    }

    public void highlightContent(HighlightBuilder highlightBuilder, String... searchField) {
        for (String field : searchField) {
            highlightBuilder.field(field);
        }
        highlightBuilder.fragmentSize(150);
        highlightBuilder.numOfFragments(5);
        //高亮设置
        highlightBuilder.preTags(PRE_TAGS);
        highlightBuilder.postTags(POST_TAGS);
    }


    /**
     * @param text     需要分词的文本内容
     * @param analyzer 需要选择的分词器
     * @return List<String> list 分词完的集合
     */
    public List<String> getAnalyze(String text, String analyzer) {
        List<String> list = new ArrayList<String>();
        try {
            Request request = new Request("GET", "_analyze");
            JSONObject entity = new JSONObject();
            entity.put("analyzer", analyzer);
            //entity.put("analyzer", "ik_smart");
            entity.put("text", text);
            request.setJsonEntity(entity.toJSONString());
            Response response = this.client.getLowLevelClient().performRequest(request);
            JSONObject tokens = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            JSONArray arrays = tokens.getJSONArray("tokens");
            for (int i = 0; i < arrays.size(); i++) {
                JSONObject obj = JSON.parseObject(arrays.getString(i));
                list.add(obj.getString("token").toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 检查索引是否存在
     *
     * @param indexName 索引库名
     * @return
     */
    public boolean queryIndexExisted(String indexName) {boolean existed = false;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest();
            getIndexRequest.indices(indexName);
            existed = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existed;
    }

    /**
     * 删除索引库
     *
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        boolean acknowledged = false;try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    /**
     * 创建索引库
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createIndex(CreateIndexRequest request, RequestOptions requestOptions) throws IOException {
        return client.indices().create(request, requestOptions);
    }
    
}

