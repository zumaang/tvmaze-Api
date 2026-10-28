package com.tvmaze.az.controller;

import com.tvmaze.az.dto.SearchResponse;
import com.tvmaze.az.service.SearchService;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<SearchResponse> searchShows(
            @RequestParam("query") String query
    ) {
        return searchService.searchShows(query);
    }

    @GetMapping("/{id}")
    public JsonNode getShowId(@PathVariable Long id) {
        return searchService.getShowId(id);
    }
}
