package com.search.demo.controller;

import com.search.demo.repository.AutoCompleteSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/autocomplete")
public class AutoCompleteSearchController {
    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/prefixsearch/{text}", method = RequestMethod.GET)
    public List<String> searchByPrefixMethod(@PathVariable String text) {
        AutoCompleteSearchRepository autoCompleteSearchRepository = context
                .getBean(AutoCompleteSearchRepository.class,
                        new String[] { "movies" }, new String[] { "marvel" });
        return autoCompleteSearchRepository
                .getPrefixSearchResults("name.keywordstring", text);

    }

    @RequestMapping(value = "/ngramsearch/{text}", method = RequestMethod.GET)
    public List<String> searchByNgramMethod(@PathVariable String text) {
        AutoCompleteSearchRepository autoCompleteSearchRepository = context
                .getBean(AutoCompleteSearchRepository.class,
                        new String[] { "movies" }, new String[] { "marvel" });
        return autoCompleteSearchRepository
                .getNgramSearch("name.edgengram", text);

    }

    @RequestMapping(value = "/suggestion/{text}", method = RequestMethod.GET)
    public List<String> searchBySuggestionMethod(@PathVariable String text) {
        AutoCompleteSearchRepository autoCompleteSearchRepository = context
                .getBean(AutoCompleteSearchRepository.class,
                        new String[] { "movies" }, new String[] { "marvel" });
        return autoCompleteSearchRepository
                .getSuggestionSearch("name.completion", text);

    }

}
