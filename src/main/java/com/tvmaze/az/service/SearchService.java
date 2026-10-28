package com.tvmaze.az.service;

import com.tvmaze.az.dbdata.CacheRepository;
import com.tvmaze.az.dbdata.ShowCache;
import com.tvmaze.az.dto.SearchResponse;
import com.tvmaze.az.dto.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SearchService {

    private static final String API_URL =
            "https://api.tvmaze.com";

    private final RestClient restClient;
    private final CacheRepository cacheRepository;
    private final ObjectMapper objectMapper;
    private final CommentsService commentsService;

    public SearchService(RestClient.Builder builder,
                         CacheRepository cacheRepository,
                         ObjectMapper objectMapper,
                         CommentsService commentsService) {
        this.restClient = builder
                .baseUrl(API_URL)
                .build();

        this.cacheRepository = cacheRepository;
        this.objectMapper = objectMapper;
        this.commentsService = commentsService;
    }

    public List<SearchResponse> searchShows(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del show es obligatorio"
            );
        }

        SearchResult[] results = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/shows")
                        .queryParam("q", query.trim())
                        .build())
                .retrieve()
                .body(SearchResult[].class);

        if (results == null) {
            return List.of();
        }

        return Arrays.stream(results)
                .map(SearchResult::show)
                .filter(Objects::nonNull)
                .map(show -> new SearchResponse(
                        show.id(),
                        show.name(),
                        resultChannel(show),
                        show.summary(),
                        show.genres() == null
                                ? List.of()
                                : show.genres(),
                        commentsService.getComments(
                                Math.toIntExact(show.id())
                        )
                ))
                .toList();
    }

    private String resultChannel(
            SearchResult.ShowResult show
    ) {
        if (show.network() != null && show.network().name() != null) {
            return show.network().name();
        }

        if (show.webChannel() != null) {
            return show.webChannel().name();
        }

        return null;
    }

    public JsonNode getShowId(Long id) {

        if (id == null || id <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La estructura del Id no es el correcto"
            );
        }

        return cacheRepository.findById(id)
                .map(showCache -> (JsonNode)
                        objectMapper.valueToTree(
                                showCache.getShowDato()
                        )
                )
                .orElseGet(() -> fetchAndCacheShow(id));

    }

    private JsonNode fetchAndCacheShow(Long id) {

        try {
            JsonNode show = restClient.get()
                    .uri("/shows/{id}", id)
                    .retrieve()
                    .body(JsonNode.class);

            if (show == null || show.isNull()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_GATEWAY,
                        "Dato vacio"
                );
            }

            Map<String, Object> showDato = objectMapper.convertValue(
                    show,
                    new TypeReference<Map<String, Object>>() {
                    }
            );

            ShowCache showCache = new ShowCache(id, showDato);
            cacheRepository.save(showCache);

            return show;

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El Id no se encuentra disponible" + id
            );
        }
    }
}
