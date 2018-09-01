package com.search.demo.repository;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Scope("prototype")
public class AutoCompleteSearchRepository extends BaseRepository {
    @Autowired
    private Client client;

    protected static final Logger logger = LoggerFactory
            .getLogger(AutoCompleteSearchRepository.class);

    public AutoCompleteSearchRepository(String[] indices, String[] types) {
        super(indices, types);
    }

    public List<String> getPrefixSearchResults(String field, String text) {
        logger.info("inside prefix search result");
        QueryBuilder query = QueryBuilders.prefixQuery(field, text);
        SearchResponse searchResponse = client.prepareSearch(indices)
                .setTypes(types).setQuery(query).get();
        logger.info("response{}", searchResponse);
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(SearchHit::getSourceAsMap).map(
                        Map::values).flatMap(Collection::stream)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> getNgramSearch(String field, String text) {
        logger.info("inside ngram search");
        QueryBuilder query = QueryBuilders.matchQuery(field, text);
        SearchResponse searchResponse = client.prepareSearch(indices)
                .setTypes(types)
                .setQuery(query)
                .get();
        logger.info("result{}", searchResponse);
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(SearchHit::getSourceAsMap).map(
                        Map::values).flatMap(Collection::stream)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> getSuggestionSearch(String field, String text) {
        logger.info("inside suggestion search");
        String suggestionName = "completion-suggestion";
        SuggestionBuilder completionSuggestionFuzzyBuilder = SuggestBuilders
                .completionSuggestion(field).prefix(text, Fuzziness.ONE);

        SearchResponse searchResponse = client.prepareSearch(indices)
                .setTypes(types)
                .suggest(new SuggestBuilder().addSuggestion(suggestionName,
                        completionSuggestionFuzzyBuilder))
                .execute().actionGet();
        logger.info("result {}", searchResponse);
        return searchResponse.getSuggest().getSuggestion(suggestionName)
                .getEntries()
                .stream()
                .map(Suggest.Suggestion.Entry::getOptions)
                .flatMap(Collection::stream)
                .map(val -> val.getText().string()).collect(
                        Collectors.toList());

    }
}
