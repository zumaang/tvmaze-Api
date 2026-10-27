package com.tvmaze.az.controller;

import com.tvmaze.az.dto.searchResponse;
import com.tvmaze.az.service.searchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class searchController {

    private final searchService searchService;

    public searchController(searchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<searchResponse> searchShows(
            @RequestParam("query") String query
    ){
        return searchService.searchShows(query);
    }
}
