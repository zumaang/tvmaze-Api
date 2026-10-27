package com.tvmaze.az.service;

import com.tvmaze.az.dto.searchResponse;
import com.tvmaze.az.dto.searchResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class searchService {

    private static final String API_URL =
            "https://api.tvmaze.com";

    public final RestClient restClient;

    public searchService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl(API_URL)
                .build();
    }

    public List<searchResponse> searchShows(String query) {
        if (query == null || query.isBlank()){
            throw new IllegalArgumentException(
                    "El nombre del show es obligatorio"
            );
        }

        searchResult[] results = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/shows")
                        .queryParam("q", query.trim())
                        .build())
                .retrieve()
                .body(searchResult[].class);

        if (results == null){
            return List.of();
        }

        return Arrays.stream(results)
                .map(searchResult::show)
                .filter(Objects::nonNull)
                .map(show -> new searchResponse(
                        show.id(),
                        show.name(),
                        resultChannel(show),
                        show.summary(),
                        show.genres() == null
                                ? List.of()
                                : show.genres()
                ))
                .toList();
    }

    private String resultChannel(
            searchResult.ShowResult show
    ){
        if (show.network() != null && show.network().name() != null){
            return show.network().name();
        }

        if (show.webChannel() != null){
            return show.webChannel().name();
        }

        return null;
    }
}
