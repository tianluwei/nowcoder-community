package com.nowcoder.community.service;

import com.nowcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSearchService {

    @Autowired
    DiscussPostRepository discussPostRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public void save(DiscussPost discussPost){
        discussPostRepository.save(discussPost);
    }

    public void delete(int id){
        discussPostRepository.deleteById(id);
    }

    public void searchDiscussPost(String keyword,int current,int limit){
//    public Page<DiscussPost> searchDiscussPost(String keyword,int current,int limit){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current, limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>")
                        , new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();

//        Page<DiscussPost> page=elasticsearchTemplate.queryForPage(searchQuery,DiscussPost.class,new SearchResultMapper(){

//        })
        List list=new ArrayList<>();
//        return new AggregatedPageImpl<DiscussPost>();

    }

}
