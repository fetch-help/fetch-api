package com.fetch.persist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fetch.persist.service.SearchService;
import com.fetch.persist.service.TypeLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("exact")
    @ResponseBody
    String search(@RequestParam String type, @RequestParam String attribute, @RequestParam String value) throws JsonProcessingException {
        return TypeLookup.writeJsonList(searchService.search(type, attribute, value));
    }

    @GetMapping("wildCard")
    @ResponseBody
    String searchWildCard(@RequestParam String type, @RequestParam String attribute, @RequestParam String value) throws JsonProcessingException {
        return TypeLookup.writeJsonList(searchService.searchWildcard(type, attribute, value));
    }

    @PostMapping("buildSearchIndex")
    void buildIndex() throws InterruptedException {
        searchService.buildIndex();
    }
}